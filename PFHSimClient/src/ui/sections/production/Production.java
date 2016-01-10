package ui.sections.production;

import java.util.HashMap;
import java.util.Optional;

import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import sim.Enterprise;
import sim.EnterpriseException;
import sim.bank.BankException;
import sim.production.Machine;
import sim.production.MachineException;
import sim.production.MachineType;
import sim.production.WallType;
import sim.research.dev.UpgradeException;
import ui.abstraction.Container;
import ui.abstraction.Triple;
import ui.abstraction.UISection;
import ui.abstraction.Utils;
import ui.sections.UpgradeDialog;

public class Production extends Container<VBox> implements UISection{

	private @FXML TableView<Machine> tableMachines;

	private @FXML TableColumn<Machine, String> colName;
	private @FXML TableColumn<Machine, String> colState;
	private @FXML TableColumn<Machine, Number> colCosts;
	private @FXML TableColumn<Machine, Number> colUseage;
	private @FXML TableColumn<Machine, Number> colMaxUseage;
	private @FXML TableColumn<Machine, String> colProduction;
	private @FXML TableColumn<Machine, Number> colUpgrades;

	private @FXML Button btnProduction;
	private @FXML Button btnUpgrade;
	private @FXML Button btnSell;
	private @FXML Button btnBuy;

	private Enterprise ent;
	private Runnable sidebarUpdate;

	public Production(Enterprise ent, Runnable sidebarUpdate) {
		this.ent = ent;
		this.sidebarUpdate = sidebarUpdate;
		load("/ui/fxml/Production.fxml");
	}

	public void initialize() {
		btnUpgrade.setDisable(true);
		btnSell.setDisable(true);
		btnProduction.setDisable(true);

		update();
		tableMachines.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			if(newValue != null){
				btnUpgrade.setDisable(!newValue.canDoUpgrade());
				btnProduction.setDisable(newValue.isInUpgrade());
				btnSell.setDisable(newValue.isInUpgrade());
			}else{
				btnSell.setDisable(true);
				btnProduction.setDisable(true);
				btnUpgrade.setDisable(true);
			}
		});
		if(tableMachines.getItems().size() > 0)
			tableMachines.getSelectionModel().select(0);
	}

	@FXML
	private void onBuy(ActionEvent evt){
		BuyDialog dia = new BuyDialog(ent);
		Optional<MachineType> res = dia.showAndWait();
		if(res.isPresent()){
			try {
				ent.buyMachine(res.get());
			} catch (BankException e) {
				Utils.showError(e.getMessage());
				e.printStackTrace();
			}
		}
		
		update();
	}

	@FXML
	private void onChangeProduction(ActionEvent evt){
		Machine m = tableMachines.getSelectionModel().getSelectedItem();
		ChangeProductionDialog dia = new ChangeProductionDialog(m, ent.getWarehouse());
		Optional<Pair<WallType, Integer>> res = dia.showAndWait();
		if(res.isPresent()){
			Pair<WallType, Integer> p = res.get();
			try {
				m.setProductionType(p.getKey());
				m.setMaxOutput(p.getValue());
			} catch (MachineException e) {
				Utils.showError(e.getMessage());
				e.printStackTrace();
			}
		}
		
		update();
	}

	@FXML
	private void onUpgrade(ActionEvent evt){
		Machine m = tableMachines.getSelectionModel().getSelectedItem();
		HashMap<String, Triple<Integer, Integer, Integer>> upgradeFactors = new HashMap<>();

		upgradeFactors.put("Performance", new Triple<>(
				m.getPerformance(),
				m.getType().getUpgradePerfInc(),
				m.getPerformance() + m.getType().getUpgradePerfInc()
				));
		upgradeFactors.put("Quality", new Triple<>(
				m.getQuality(),
				m.getType().getUpgradeQualInc(),
				m.getQuality() + m.getType().getUpgradeQualInc()
				));
		upgradeFactors.put("Running Costs", new Triple<>(
				m.getCosts(),
				m.getType().getUpgradeCostInc(),
				m.getCosts() + m.getType().getUpgradeCostInc()
				));
		upgradeFactors.put("Required Employees", new Triple<>(
				m.getRequiredEmps(),
				m.getType().getUpgradeEmpInc(),
				m.getRequiredEmps() + m.getType().getUpgradeEmpInc()
				));

		UpgradeDialog dia = new UpgradeDialog(m.getId(), m.getType().getUpgradeCosts(), m.getType().getUpgradeDuration(), upgradeFactors);
		Optional<Boolean> res = dia.showAndWait();
		if(res.isPresent() && res.get()){
			try {
				ent.startMachineUpgrade(m);
			} catch (UpgradeException | BankException e) {
				Utils.showError(e.getMessage());
			}
		}

		update();
	}

	@FXML
	private void onSell(ActionEvent evt){
		Machine m = tableMachines.getSelectionModel().getSelectedItem();
		int sell = m.getType().getBaseCosts() + m.getType().getUpgradeCosts() * m.getUpgradeCount();
		sell *= 0.66d;
		Alert a = new Alert(AlertType.CONFIRMATION);
		a.setTitle("Sell Machine");
		a.setHeaderText("Do you want to sell " + m.getId() + " ?");
		a.setContentText("You will get a refund of: " + sell + " €");
		Optional<ButtonType> res = a.showAndWait();
		if(res.isPresent()){
			if(res.get() == ButtonType.OK){
				try {
					ent.sellMachine(m);
				} catch (EnterpriseException e) {
					Utils.showError(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		update();
	}

	@Override
	public void update() {
		colName.setCellValueFactory(df -> {
			return new ReadOnlyStringWrapper(df.getValue().getId());
		});

		colState.setCellValueFactory(df -> {
			Machine m = df.getValue();
			String state = "Running";
			if(m.isInUpgrade())
				state = "In Upgrade";
			else if(!m.isInOperation())
				state = "Stopped";
			return new ReadOnlyStringWrapper(state);
		});

		colCosts.setCellValueFactory(df -> {
			return new ReadOnlyIntegerWrapper(df.getValue().getCosts());
		});

		colUseage.setCellValueFactory(df -> {
			return new ReadOnlyIntegerWrapper(df.getValue().getUtilization());
		});

		colMaxUseage.setCellValueFactory(df -> {
			return new ReadOnlyIntegerWrapper(df.getValue().getMaxOutput());
		});

		colProduction.setCellValueFactory(df -> {
			return new ReadOnlyStringWrapper(df.getValue().getProductionType().toString());
		});

		colUpgrades.setCellValueFactory(df -> {
			return new ReadOnlyIntegerWrapper(df.getValue().getUpgradeCount());
		});

		tableMachines.setItems(FXCollections.observableArrayList(ent.getProductionHouse().getMachines()));
		tableMachines.refresh();
		
		sidebarUpdate.run();
	}

	@Override
	public void changeTab() {
		update();
	}

}

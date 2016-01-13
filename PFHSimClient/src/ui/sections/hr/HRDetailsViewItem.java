package ui.sections.hr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import sim.Enterprise;
import sim.EnterpriseException;
import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.production.Machine;
import sim.research.dev.UpgradeFactors;
import ui.abstraction.Container;
import ui.abstraction.Triple;
import ui.abstraction.Utils;
import ui.sections.UpgradeDialog;

public class HRDetailsViewItem extends Container<HBox> {

	private @FXML Label lblEmpName;
	private @FXML Label lblEmpCosts;
	private @FXML Label lblUpgradeLevel;
	private @FXML Label lblEmpStatus;

	private @FXML Button btnUpgrade;
	private @FXML Button btnFire;

	private @FXML ComboBox<Machine> cbbAssignedWorkplace;

	private Enterprise ent;
	private Employee emp;

	private HRDetails parent;

	public HRDetailsViewItem(Enterprise ent, HRDetails pa) {
		this.ent = ent;
		this.emp = null;
		this.parent = pa;
		load("/ui/fxml/hr/HRDetailsViewItem.fxml");
	}

	private void initialize(){
		if(emp != null){
			lblEmpName.setText(emp.getName());
			lblEmpCosts.setText("" + emp.getCosts() + " €");
			lblUpgradeLevel.setText("" + emp.getVisitedTrainings());

			String status = "";
			if(emp.isOnTraining())
				status = "On Training";
			else if(emp.isAssigned())
				status = "Working";
			else
				status = "Free";
			lblEmpStatus.setText(status);

			btnUpgrade.setDisable(!emp.canDoTraining() || !ent.getBankAccount().canBeCharged(emp.getType().getUpgradeCosts()));
			btnFire.setDisable(!emp.canBeUnassigned());

			if(emp.getType() == EmployeeType.PRODUCTION){
				cbbAssignedWorkplace.setVisible(true);
				List<Machine> machines = new ArrayList<>();
				machines.addAll(ent.getProductionHouse().getMachines());
				for (int i = 0; i < machines.size(); i++) {
					if(machines.get(i).isInUpgrade())
						machines.remove(i--);
				}
				cbbAssignedWorkplace.setItems(FXCollections.observableArrayList(machines));
				Callback<ListView<Machine>, ListCell<Machine>> factory = lv -> {
					return new ListCell<Machine>(){
						@Override
						protected void updateItem(Machine item, boolean empty){
							super.updateItem(item, empty);
							if(item != null){
								setText(item.getId());
							}
						}
					};
				};
				cbbAssignedWorkplace.setButtonCell(factory.call(null));
				cbbAssignedWorkplace.setCellFactory(factory);
				if(emp.isAssigned() && !emp.isOnTraining())
					cbbAssignedWorkplace.getSelectionModel().select((Machine)emp.getWork());
				else
					cbbAssignedWorkplace.getSelectionModel().select(-1);

				cbbAssignedWorkplace.setDisable(emp.isOnTraining());
			}

		}else{
			lblEmpName.setText("null");
		}
	}

	@FXML
	private void onUpgrade(ActionEvent e){
		HashMap<String, Triple<Integer, Integer, Integer>> factors = new HashMap<>();
		
		factors.put("Costs: ", new Triple<>(
				emp.getCosts(),
				emp.getType().getUpgradeCostInc(),
				emp.getCosts() + emp.getType().getUpgradeCostInc()
				));
		
		String text = "";
		int before = 0;
		int delta = 0;
		int after = 0;
		
		switch (emp.getType()) {
		case HR:
			text = "HR Capacity: ";
			before = emp.getSkill() * UpgradeFactors.HR_MANAGE_AMOUNT_FACTOR;
			delta = UpgradeFactors.HR_MANAGE_AMOUNT_FACTOR;
			after = (emp.getSkill()+1) * UpgradeFactors.HR_MANAGE_AMOUNT_FACTOR;
			break;
			
		case ARCHITECT:
			text = "Research time reduction: ";
			before = emp.getSkill() * UpgradeFactors.ARCHITECT_THINK_TIME_REDUCTION_FACTOR;
			delta = UpgradeFactors.ARCHITECT_THINK_TIME_REDUCTION_FACTOR;
			after = (emp.getSkill()+1) * UpgradeFactors.HR_MANAGE_AMOUNT_FACTOR;
			break;

		default:
			break;
		}
		
		factors.put(text, new Triple<>(before, delta, after));
		
		
		UpgradeDialog dia = new UpgradeDialog(emp.getName(), emp.getType().getUpgradeCosts(), 
				emp.getType().getUpgradeDuration(), factors);

		Optional<Boolean> res = dia.showAndWait();

		if(res.isPresent() && res.get()){
			
			try {
				ent.startEmployeeTraining(emp);
			} catch (EnterpriseException e1) {
				//this should not happen
				e1.printStackTrace();
				Utils.showError(e1.getMessage());
			}
		}

		parent.initialize(); //eventually fire buttons of other employees need to be disabled
	}

	@FXML
	private void onFire(ActionEvent e){
		ent.getHR().fire(emp);
		parent.initialize();
	}

	@FXML
	private void onNewMachineSelected(ActionEvent e){
		if(cbbAssignedWorkplace.getValue() != emp.getWork()){
			//a new workplace was selected; try to assign
			emp.assignWorkplace(cbbAssignedWorkplace.getValue());
			initialize();
		}
	}

	void setEmployee(Employee e){
		this.emp = e;
		initialize();
	}
}

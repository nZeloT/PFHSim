package ui.sections;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import sim.Enterprise;
import sim.bank.BankException;
import sim.hr.HRException;
import sim.procurement.ResourceMarket;
import sim.procurement.ResourceMarketException;
import sim.procurement.ResourceType;
import sim.warehouse.WarehouseException;
import ui.abstraction.Container;
import ui.abstraction.UISection;
import ui.abstraction.Utils;

public class Procurement extends Container<VBox> implements UISection{

	private @FXML Button btn_wood;
	private @FXML Button btn_concrete;
	private @FXML Button btn_insulation;
	private @FXML Button btn_roof;
	private @FXML Button btn_brick;
	private @FXML Button btn_window;

	private @FXML Label price_wood;
	private @FXML Label price_concrete;
	private @FXML Label price_insulation;
	private @FXML Label price_roof;
	private @FXML Label price_brick;
	private @FXML Label price_window;

	private @FXML Label sum_wood;
	private @FXML Label sum_concrete;
	private @FXML Label sum_insulation;
	private @FXML Label sum_roof;
	private @FXML Label sum_brick;
	private @FXML Label sum_window;
	
	private @FXML Label war_wood;
	private @FXML Label war_concrete;
	private @FXML Label war_insulation;
	private @FXML Label war_roof;
	private @FXML Label war_brick;
	private @FXML Label war_window;

	private @FXML Label AM_wood;
	private @FXML Label AM_concrete;
	private @FXML Label AM_insulation;
	private @FXML Label AM_roof;
	private @FXML Label AM_brick;
	private @FXML Label AM_window;

	private @FXML TextField input_wood;
	private @FXML TextField input_concrete;
	private @FXML TextField input_insulation;
	private @FXML TextField input_roof;
	private @FXML TextField input_brick;
	private @FXML TextField input_window;
	
	private @FXML Label lblWarehouseMax;
	private @FXML Label lblWarehouseCurrent;

	private Enterprise ent;
	private ResourceMarket market;
	private @FXML VBox root;

	private int int_amount_wood;
	private int int_amount_concrete;
	private int int_amount_insulation;
	private int int_amount_roof;
	private int int_amount_brick;
	private int int_amount_window;

	public Procurement(Enterprise enterprise) {
		this.ent = enterprise;
		this.market = ent.getMarket();
		load("/ui/fxml/Procurement.fxml");
	}

	public void initialize() {
		price_wood.setText(" " + market.getPrice(ResourceType.WOOD));
		price_concrete.setText(" " + market.getPrice(ResourceType.CONCRETE));
		price_insulation.setText(" " + market.getPrice(ResourceType.INSULATION));
		price_roof.setText(" " + market.getPrice(ResourceType.ROOF_TILE));
		price_brick.setText(" " + market.getPrice(ResourceType.BRICK));
		price_window.setText(" " + market.getPrice(ResourceType.WINDOW));

		AM_wood.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.WOOD));
		AM_concrete.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.CONCRETE));
		AM_insulation.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.INSULATION));
		AM_roof.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.ROOF_TILE));
		AM_brick.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.BRICK));
		AM_window.setText("" + ent.getWarehouse().getStoredAmount(ResourceType.WINDOW));
		
		lblWarehouseCurrent.setText("" + ent.getWarehouse().getUtilization());
		lblWarehouseMax.setText("" + ent.getWarehouse().getCapacity());
	}

	public void update() {

		price_wood.setText("" + market.getPrice(ResourceType.WOOD));
		price_concrete.setText("" + market.getPrice(ResourceType.CONCRETE));
		price_insulation.setText("" + market.getPrice(ResourceType.INSULATION));
		price_roof.setText("" + market.getPrice(ResourceType.ROOF_TILE));
		price_brick.setText("" + market.getPrice(ResourceType.BRICK));
		price_window.setText(" " + market.getPrice(ResourceType.WINDOW));
		
		AM_wood.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.WOOD));
		AM_concrete.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.CONCRETE));
		AM_insulation.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.INSULATION));
		AM_roof.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.ROOF_TILE));
		AM_brick.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.BRICK));
		AM_window.setText("" + ent.getWarehouse().getStoredAmount(ResourceType.WINDOW));
		
		lblWarehouseCurrent.setText("" + ent.getWarehouse().getUtilization());
		lblWarehouseMax.setText("" + ent.getWarehouse().getCapacity());
	}

	public boolean checkIfNumberInput(TextField input) {

		if (input.getText().matches("[0-9]{1,}")) {
			return true;  
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("PFH Warning");
			alert.setHeaderText("This is a numeric input field");
			alert.setContentText("Try again with numbers");

			alert.showAndWait();
			return false;
		}

	}

	@FXML
	private void handleBuyButton(ActionEvent event){
		Button btn = (Button) event.getSource();

		if (btn.getId().equals("btn_wood")) {
			buy(AM_wood, int_amount_wood, ResourceType.WOOD);
		}

		else if (btn.getId().equals("btn_insulation")) {
			buy(AM_insulation, int_amount_insulation, ResourceType.INSULATION);
		}

		else if (btn.getId().equals("btn_concrete")) {
			buy(AM_concrete, int_amount_concrete, ResourceType.CONCRETE);
		}
		else if (btn.getId().equals("btn_roof")) {
			buy(AM_roof, int_amount_roof, ResourceType.ROOF_TILE);
		}
		else if (btn.getId().equals("btn_brick")) {
			buy(AM_brick, int_amount_brick, ResourceType.BRICK);
		}

		else if (btn.getId().equals("btn_window")) {
			buy(AM_window, int_amount_window, ResourceType.WINDOW);
		}

	}

	@FXML
	public void handleInput(ActionEvent event) {
		TextField text = (TextField) event.getSource();
		updateTextField(text);
	}
	
	@FXML
	public void handleKeyInput(KeyEvent event){
		TextField text = (TextField) event.getSource();
		updateTextField(text);
	}
	
	private void updateTextField(TextField text){
		if(text.getText().isEmpty())
			return;
		
		if (text.getId().equals("input_wood")) {
			if (checkIfNumberInput(text)) {
				int_amount_wood = Integer.parseInt(text.getText());
				sum_wood.setText(" " + market.getPrice(ResourceType.WOOD) * int_amount_wood + " €");
				war_wood.setText(" " + ResourceType.WOOD.getVolume() * int_amount_wood);
			} else {
				text.setText("");
			}

		}

		if (text.getId().equals("input_insulation")) {
			if (checkIfNumberInput(text)) {
				int_amount_insulation = Integer.parseInt(text.getText());
				sum_insulation.setText(" " + market.getPrice(ResourceType.INSULATION) * int_amount_insulation + " €");
				war_insulation.setText(" " + ResourceType.INSULATION.getVolume() * int_amount_wood);
			} else {
				text.setText("");
			}

		}

		if (text.getId().equals("input_concrete")) {
			if (checkIfNumberInput(text)) {
				int_amount_concrete = Integer.parseInt(text.getText());
				sum_concrete.setText(" " + market.getPrice(ResourceType.CONCRETE) * int_amount_concrete + " €");
				war_concrete.setText(" " + ResourceType.CONCRETE.getVolume() * int_amount_wood);
			} else {
				text.setText("");
			}
		}
		if (text.getId().equals("input_roof")) {
			if (checkIfNumberInput(text)) {
				int_amount_roof = Integer.parseInt(text.getText());
				sum_roof.setText(" " + market.getPrice(ResourceType.ROOF_TILE) * int_amount_roof + " €");
				war_roof.setText(" " + ResourceType.ROOF_TILE.getVolume() * int_amount_wood);
			} else {
				text.setText("");
			}
		}
		if (text.getId().equals("input_brick")) {
			if (checkIfNumberInput(text)) {
				int_amount_brick = Integer.parseInt(text.getText());
				sum_brick.setText(" " + market.getPrice(ResourceType.BRICK) * int_amount_brick + " €");
				war_brick.setText(" " + ResourceType.BRICK.getVolume() * int_amount_wood);
			} else {
				text.setText("");
			}
		}
		if (text.getId().equals("input_window")) {
			if (checkIfNumberInput(text)) {
				int_amount_window = Integer.parseInt(text.getText());
				sum_window.setText(" " + market.getPrice(ResourceType.WINDOW) * int_amount_window + " €");
				war_window.setText(" " + ResourceType.WINDOW.getVolume() * int_amount_wood);
			} else {
				text.setText("");
			}

		}

	}
	
	public void buy(Label fullAmount, int amount, ResourceType type){

		try {
			ent.buyResources(type, amount);
		} catch (BankException | ResourceMarketException | HRException | WarehouseException e) {
			Utils.showError(e.getMessage());
			e.printStackTrace();
		}
		
		lblWarehouseCurrent.setText("" + ent.getWarehouse().getUtilization());
		lblWarehouseMax.setText("" + ent.getWarehouse().getCapacity());

		fullAmount.setText("" + ent.getWarehouse().getStoredAmount(type));
	}

	@Override
	public void changeTab() {
		update();
	}

}

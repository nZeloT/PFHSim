package ui.sections;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import sim.Enterprise;
import sim.EnterpriseException;
import sim.procurement.ResourceType;
import sim.production.WallType;
import sim.research.dev.ExtendWarehouse;
import ui.abstraction.Container;
import ui.abstraction.UISection;
import ui.abstraction.Utils;

public class Warehouse extends Container<VBox> implements UISection{

	// amount
	private @FXML Label AM_panorama;
	private @FXML Label AM_massPlus;
	private @FXML Label AM_massLig;
	private @FXML Label AM_lightWeight;
	private @FXML Label AM_lightPlus;

	// total space
	private @FXML Label TS_panorama;
	private @FXML Label TS_massPlus;
	private @FXML Label TS_massLig;
	private @FXML Label TS_lightWeight;
	private @FXML Label TS_lightPlus;

	// piece unit
	private @FXML Label PU_panorama;
	private @FXML Label PU_massPlus;
	private @FXML Label PU_massLig;
	private @FXML Label PU_lightWeight;
	private @FXML Label PU_lightPlus;

	private @FXML Label AM_insulation;
	private @FXML Label AM_wood;
	private @FXML Label AM_window;
	private @FXML Label AM_roof;
	private @FXML Label AM_brick;
	private @FXML Label AM_concrete;
	
	private @FXML Label Label_ProgressExtension;
	private @FXML ProgressIndicator ProgressBar_extendWarehouse;
	private @FXML Button btn_extendWarehouse;

	private Enterprise ent;

	private int TS_panorama_int;
	private int TS_massPlus_int;
	private int TS_massLig_int;
	private int TS_lightWeight_int;
	private int TS_lightPlus_int;

	private int upgrade_duration = 2;
	private boolean isUpgradeRunning = false;
	public Warehouse(Enterprise enterprise) {
		this.ent = enterprise;
		load("/ui/fxml/Warehouse.fxml");
	}

	public void initialize() {

		AM_insulation.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.INSULATION));
		AM_wood.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.WOOD));
		AM_window.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.WINDOW));
		AM_roof.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.ROOF_TILE));
		AM_brick.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.BRICK));
		AM_concrete.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.CONCRETE));

		AM_panorama.setText(" " + ent.getWarehouse().getStoredAmount(WallType.PANORAMA_WALL));
		AM_massPlus.setText(" " + ent.getWarehouse().getStoredAmount(WallType.MASSIVE_PLUS_CONSTUCTION));
		AM_massLig.setText(" " + ent.getWarehouse().getStoredAmount(WallType.MASSIVE_LIGHT_CONSTRUCTION));
		AM_lightWeight.setText(" " + ent.getWarehouse().getStoredAmount(WallType.LIGHT_WEIGHT_CONSTRUCTION));
		AM_lightPlus.setText(" " + ent.getWarehouse().getStoredAmount(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS));

		calculateTotalSpace();

		TS_panorama.setText("" + TS_panorama_int);
		TS_massPlus.setText("" + TS_massPlus_int);
		TS_massLig.setText("" + TS_massLig_int);
		TS_lightWeight.setText("" + TS_lightWeight_int);
		TS_lightPlus.setText("" + TS_lightPlus_int);

		PU_panorama.setText(" " + WallType.PANORAMA_WALL.getVolume());
		PU_massPlus.setText(" " + WallType.MASSIVE_PLUS_CONSTUCTION.getVolume());
		PU_massLig.setText(" " + WallType.MASSIVE_LIGHT_CONSTRUCTION.getVolume());
		PU_lightWeight.setText(" " + WallType.LIGHT_WEIGHT_CONSTRUCTION.getVolume());
		PU_lightPlus.setText(" " + WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS.getVolume());

	}

	public void update() {

		AM_insulation.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.INSULATION));
		AM_wood.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.WOOD));
		AM_window.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.WINDOW));
		AM_roof.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.ROOF_TILE));
		AM_brick.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.BRICK));
		AM_concrete.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.CONCRETE));

		AM_panorama.setText(" " + ent.getWarehouse().getStoredAmount(WallType.PANORAMA_WALL));
		AM_massPlus.setText(" " + ent.getWarehouse().getStoredAmount(WallType.MASSIVE_PLUS_CONSTUCTION));
		AM_massLig.setText(" " + ent.getWarehouse().getStoredAmount(WallType.MASSIVE_LIGHT_CONSTRUCTION));
		AM_lightWeight.setText(" " + ent.getWarehouse().getStoredAmount(WallType.LIGHT_WEIGHT_CONSTRUCTION));
		AM_lightPlus.setText(" " + ent.getWarehouse().getStoredAmount(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS));

		calculateTotalSpace();

		TS_panorama.setText("" + TS_panorama_int);
		TS_massPlus.setText("" + TS_massPlus_int);
		TS_massLig.setText("" + TS_massLig_int);
		TS_lightWeight.setText("" + TS_lightWeight_int);
		TS_lightPlus.setText("" + TS_lightPlus_int);

		upgrade_duration--;

		if(upgrade_duration == 1){
			ProgressBar_extendWarehouse.setProgress(0.5);
		}

		if (upgrade_duration == 0) {
			btn_extendWarehouse.setDisable(false);
			btn_extendWarehouse.setVisible(true);
			ProgressBar_extendWarehouse.setVisible(false);
			ProgressBar_extendWarehouse.setProgress(0.0);
		}

	}

	@FXML
	private void handleExtendButton(ActionEvent event) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("Would you like to extend Warehouse?");
		alert.setContentText(" Costs:      " + ExtendWarehouse.UPGRADE_COSTS 
				+ "\n Duration: " + ExtendWarehouse.UPGRADE_DURATION
				+ "\n \n Are you ok with this?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			try{
				ProgressBar_extendWarehouse.setVisible(true);
				btn_extendWarehouse.setVisible(false);
				Label_ProgressExtension.setVisible(true);
				
				ent.startWarehouseExtension();
				
				upgrade_duration = ExtendWarehouse.UPGRADE_DURATION;
				ProgressBar_extendWarehouse.setProgress(0.0);
				
			}catch(EnterpriseException e){
				e.printStackTrace();
				Utils.showError(e.getMessage());
			}
		}     

	}

	public void calculateTotalSpace() {

		TS_panorama_int = WallType.PANORAMA_WALL.getVolume()
				* ent.getWarehouse().getStoredAmount(WallType.PANORAMA_WALL);
		TS_massPlus_int = WallType.MASSIVE_PLUS_CONSTUCTION.getVolume()
				* ent.getWarehouse().getStoredAmount(WallType.MASSIVE_PLUS_CONSTUCTION);
		TS_massLig_int = WallType.MASSIVE_LIGHT_CONSTRUCTION.getVolume()
				* ent.getWarehouse().getStoredAmount(WallType.MASSIVE_LIGHT_CONSTRUCTION);
		TS_lightWeight_int = WallType.LIGHT_WEIGHT_CONSTRUCTION.getVolume()
				* ent.getWarehouse().getStoredAmount(WallType.LIGHT_WEIGHT_CONSTRUCTION);
		TS_lightPlus_int = WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS.getVolume()
				* ent.getWarehouse().getStoredAmount(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS);

	}

	@Override
	public void changeTab() {
		if(isUpgradeRunning=true){
			if(upgrade_duration == 1){
				ProgressBar_extendWarehouse.setProgress(0.5);
			}

			if (upgrade_duration == 0) {
				btn_extendWarehouse.setDisable(false);
				btn_extendWarehouse.setVisible(true);
				ProgressBar_extendWarehouse.setVisible(false);
				ProgressBar_extendWarehouse.setProgress(0.0);
			}
		}
		
		AM_insulation.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.INSULATION));
		AM_wood.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.WOOD));
		AM_window.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.WINDOW));
		AM_roof.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.ROOF_TILE));
		AM_brick.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.BRICK));
		AM_concrete.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.CONCRETE));

		AM_panorama.setText(" " + ent.getWarehouse().getStoredAmount(WallType.PANORAMA_WALL));
		AM_massPlus.setText(" " + ent.getWarehouse().getStoredAmount(WallType.MASSIVE_PLUS_CONSTUCTION));
		AM_massLig.setText(" " + ent.getWarehouse().getStoredAmount(WallType.MASSIVE_LIGHT_CONSTRUCTION));
		AM_lightWeight.setText(" " + ent.getWarehouse().getStoredAmount(WallType.LIGHT_WEIGHT_CONSTRUCTION));
		AM_lightPlus.setText(" " + ent.getWarehouse().getStoredAmount(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS));

		calculateTotalSpace();

		TS_panorama.setText("" + TS_panorama_int);
		TS_massPlus.setText("" + TS_massPlus_int);
		TS_massLig.setText("" + TS_massLig_int);
		TS_lightWeight.setText("" + TS_lightWeight_int);
		TS_lightPlus.setText("" + TS_lightPlus_int);
	}
	
	

}
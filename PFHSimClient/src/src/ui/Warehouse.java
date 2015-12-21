package src.ui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import sim.Enterprise;
import sim.procurement.ResourceType;
import sim.production.WallType;

public class Warehouse {
            	
	
	// amount
	private @FXML Label AM_panorama;
	private @FXML Label AM_massPlus;
	private @FXML Label AM_massLig;
	private @FXML Label AM_lightWeight;
	private @FXML Label AM_lightPlus;

	//total space
	private @FXML Label TS_panorama;
	private @FXML Label TS_massPlus;
	private @FXML Label TS_massLig;
	private @FXML Label TS_lightWeight;
	private @FXML Label TS_lightPlus;
	
	//piece unit
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

	private @FXML ProgressIndicator ProgressBar_extendWarehouse;
	private @FXML Button btn_extendWarehouse;
	private @FXML VBox root;
	
	private Enterprise ent;
		
	public Warehouse(Enterprise enterprise){
		
		FXMLLoader fxml = new FXMLLoader(getClass().getResource("/ui/fxml/Warehouse.fxml"));
		fxml.setController(this);
		try {
		this.ent = enterprise;
		fxml.load();
		} catch (IOException e) {
		e.printStackTrace();
		}
		
	

	}
	
	public void initialize(){
		  
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
  
		TS_panorama.setText("0");
		TS_massPlus.setText("0");
		TS_massLig.setText("0");
		TS_lightWeight.setText("0");
		TS_lightPlus.setText("0");
  
		System.out.println(WallType.PANORAMA_WALL.getVolume());
		PU_panorama.setText(" " +WallType.PANORAMA_WALL.getVolume());
		PU_massPlus.setText(" " +WallType.MASSIVE_PLUS_CONSTUCTION.getVolume());
		PU_massLig.setText(" " +WallType.MASSIVE_LIGHT_CONSTRUCTION.getVolume());
		PU_lightWeight.setText(" " +WallType.LIGHT_WEIGHT_CONSTRUCTION.getVolume());
		PU_lightPlus.setText(" " +WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS.getVolume());
	}
	@FXML
	private void handleExtendButton(ActionEvent event) {

		  
		
		
		System.out.println("warehouse extension begin");
	}
	
	public VBox getBox(){
		return root;
	}

}
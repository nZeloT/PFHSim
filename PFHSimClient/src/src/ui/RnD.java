package src.ui;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import sim.Enterprise;
import sim.hr.EmployeeType;
import sim.production.PFHouseType;

public class RnD{
	
	private@FXML VBox root;
	private@FXML ProgressBar barbung;
	private@FXML ProgressBar barblock;
	private@FXML ProgressBar bareffic;
	private@FXML ProgressBar barmulti;
	private@FXML ProgressBar barcomf;
	private@FXML ProgressBar barvilla;
	private@FXML ProgressBar bartrend;
	
	private Label[] labels = new Label[14];
	private@FXML Label lblbungdur;
	private@FXML Label lblbungcost;
	private@FXML Label lblblockdur;
	private@FXML Label lblblockcost;
	private@FXML Label lblefficdur;
	private@FXML Label lblefficcost;
	private@FXML Label lblmultidur;
	private@FXML Label lblmulticost;
	private@FXML Label lblcomfdur;
	private@FXML Label lblcomfcost;
	private@FXML Label lblvilladur;
	private@FXML Label lblvillacost;
	private@FXML Label lbltrenddur;
	private@FXML Label lbltrendcost;
	private HashMap<PFHouseType, ProgressBar> map;
	private Enterprise e;
	
	public RnD(Enterprise ent) {
		FXMLLoader fxml = new FXMLLoader(getClass().getResource("/ui/fxml/RnD.fxml"));
		fxml.setController(this);
		this.e = ent;
		try {
			fxml.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		map = new HashMap<>();
		map.put(PFHouseType.BUNGALOW, barbung);
		map.put(PFHouseType.BLOCK_HOUSE, barblock);
		map.put(PFHouseType.EFFICIENCY_HOUSE, bareffic);
		map.put(PFHouseType.MULTI_FAMILY_HOUSE, barmulti);
		map.put(PFHouseType.COMFORT_HOUSE, barcomf);
		map.put(PFHouseType.CITY_VILLA, barvilla);
		map.put(PFHouseType.TRENDHOUSE, bartrend);
	}

	public void initialize(){
			labels[0] = lblbungdur;  
			labels[1] = lblbungcost; 
			labels[2] = lblblockdur; 
			labels[3] = lblblockcost;
			labels[4] = lblefficdur; 
			labels[5] = lblefficcost;
			labels[6] = lblmultidur; 
			labels[7] = lblmulticost;
			labels[8] = lblcomfdur;  
			labels[9] = lblcomfcost; 
			labels[10] = lblvilladur; 
			labels[11] = lblvillacost;
			labels[12] = lbltrenddur; 
			labels[13] = lbltrendcost;
		
		PFHouseType []types = PFHouseType.values();
		for (int i = 0; i < types.length; i++) {
			labels[i*2].setText(""+types[i].getResearchDuration());
			labels[i*2+1].setText(""+types[i].getResearchCosts());
		}
	}
	
	@FXML
	private void researchHouse(ActionEvent e){
		Button btn = (Button) e.getSource();
		
		switch (btn.getId()) {
		case "btnbungalow":
			startReseachProject(PFHouseType.BUNGALOW,btn);
			break;
		case "btnblock":
			startReseachProject(PFHouseType.BLOCK_HOUSE,btn);
			break;
		case "btneffic":
			startReseachProject(PFHouseType.EFFICIENCY_HOUSE,btn);
			break;
		case "btnmulti":
			startReseachProject(PFHouseType.MULTI_FAMILY_HOUSE,btn);
			break;
		case "btncomf":
			startReseachProject(PFHouseType.COMFORT_HOUSE,btn);
			break;
		case "btnvilla":
			startReseachProject(PFHouseType.CITY_VILLA,btn);
			break;
		case "btntrend":
			startReseachProject(PFHouseType.TRENDHOUSE,btn);
			break;
		default:
			//should never happen
			break;
		}
	}
	
	private void startReseachProject(PFHouseType type, Button b){
		//if(!e.startResearchProject(type, null)){
			b.setDisable(true);
		//}else{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Warning");
			alert.setHeaderText(null);
			alert.setContentText("House can't be explored! Either you don't have enough money, or the Architect is already busy!");
			alert.showAndWait();
		//};
	}
	
	/**
	 * @param type the housetype you want to upgrade the bar for
	 * calculating is done for you.
	 */
	private void adjustbar(PFHouseType type){
		double adjust = 1.0 / type.getResearchDuration();
		ProgressBar bar = map.get(type);
		bar.setProgress(bar.getProgress() + adjust);
	}
	
	public VBox getVBox(){
		return root;
	}
}

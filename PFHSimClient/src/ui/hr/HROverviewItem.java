package ui.hr;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import sim.hr.EmployeeType;
import sim.hr.HR;

public class HROverviewItem {
	
	private @FXML HBox hboxContainer;

	private @FXML Label lblEmpType;
	
	private @FXML TextField textOnTraining;
	private @FXML TextField textAtWorkplace;
	private @FXML TextField textFree;
	
	private @FXML Button btnView;
	
	private HR hr;
	private EmployeeType type;
	private HRPane pane;
	
	public HROverviewItem(HRPane pane, HR hr, EmployeeType t) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/fxml/hr/HROverviewItem.fxml"));
        fxmlLoader.setController(this);
        
        try{
        	this.hr = hr;
            this.type = t;
            this.pane = pane;
            fxmlLoader.load();
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
	}
	
	public void initialize(){
		lblEmpType.setText(type.toString());
		//TODO
	}
	
	@FXML
	private void onViewDetails(ActionEvent e){
		System.out.println("Details");
		pane.showDetails(type);
	}
	
	HBox getContainer() {
		return hboxContainer;
	}
	
	void setType(EmployeeType type) {
		this.type = type;
	}
}

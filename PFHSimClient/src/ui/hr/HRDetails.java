package ui.hr;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import sim.hr.EmployeeType;
import sim.hr.HR;

public class HRDetails {
	
	private @FXML VBox vboxContainer;
	
	private HRPane pane;
	
	private HR hr;
	private EmployeeType type;
	
	public HRDetails(HRPane pane, HR hr) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/fxml/hr/HRDetails.fxml"));
        fxmlLoader.setController(this);
        
        try{
        	this.pane = pane;
        	this.hr = hr;
        	this.type = EmployeeType.ARCHITECT;
            fxmlLoader.load();
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
	}
	
	public void initialize(){
		
	}
	
	public void setType(EmployeeType type) {
		this.type = type;
		initialize();
	}

	@FXML
	private void onBackButton(ActionEvent e){
		pane.showOverview();
	}
	
	VBox getContainer() {
		return vboxContainer;
	}
	
}

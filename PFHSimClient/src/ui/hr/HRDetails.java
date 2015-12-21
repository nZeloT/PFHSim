package ui.hr;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.hr.HR;

public class HRDetails {
	
	private @FXML VBox vboxContainer;
	
	private @FXML ListView<Employee> listDetails;
	
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
		Employee[] emps = hr.getAllOfType(type);
		listDetails.setItems(FXCollections.observableArrayList(emps));
		listDetails.setCellFactory((lv)->{
			return new HRDetailsListCell(hr);
		});
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

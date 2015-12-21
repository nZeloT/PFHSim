package ui.hr;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import sim.hr.EmployeeType;
import sim.hr.HR;

public class HROverview {
	
	private @FXML VBox vboxContainer;

	private @FXML ListView<EmployeeType> listOverview;
	
	private @FXML ComboBox<EmployeeType> cbbEmpType;
	private @FXML TextField textEmpCount;
	private @FXML Button btnHire;
	private @FXML Label lblEmpCosts;
	
	private HRPane pane;
	
	private HR hr;
	
	public HROverview(HRPane pane, HR hr) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/fxml/hr/HROverview.fxml"));
        fxmlLoader.setController(this);
        
        try{
        	this.pane = pane;
        	this.hr = hr;
            fxmlLoader.load();
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
	}
	
	public void initialize(){
		//Fill the combo box with content
		cbbEmpType.setItems(FXCollections.observableArrayList(EmployeeType.values()));
		cbbEmpType.setValue(EmployeeType.ARCHITECT);
		
		lblEmpCosts.setText("0");
		
		listOverview.setItems(FXCollections.observableArrayList(EmployeeType.values()));
		listOverview.setCellFactory((lv)->{
			return new HROverviewListCell(pane, hr);
		});
	}
	
	@FXML
	private void onHireNewEmployees(ActionEvent e){
		System.out.println("Hire");
	}
	
	@FXML
	private void onEmpTypeChanged(ActionEvent e){
		recalcCosts();
	}
	
	@FXML
	private void onEmpCountChanged(ActionEvent e){
		//check if only a number was entered
		int i = 0;
		try {
			i = Integer.parseInt(textEmpCount.getText());
			if(i >= 0)
				recalcCosts();
		} catch (Exception e2) {
		}
		
	}
	
	private void recalcCosts(){
		int i = Integer.parseInt(textEmpCount.getText());
		int costs = cbbEmpType.getValue().getBaseCost() * i;
		lblEmpCosts.setText("" + costs);
	}
	
	VBox getContainer() {
		return vboxContainer;
	}
}

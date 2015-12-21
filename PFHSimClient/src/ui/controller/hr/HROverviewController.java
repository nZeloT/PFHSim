package ui.controller.hr;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import sim.hr.Employee;
import sim.hr.EmployeeType;

public class HROverviewController {

	private @FXML ListView<Employee> listOverview;
	
	private @FXML ComboBox<EmployeeType> cbbEmpType;
	private @FXML TextField textEmpCount;
	private @FXML Button btnHire;
	private @FXML Label lblEmpCosts;
	
	@FXML
	private void onHireNewEmployees(ActionEvent e){
		
	}
}

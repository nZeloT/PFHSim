package ui.sections.hr;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.hr.HR;
import ui.abstraction.Container;

public class HRDetails extends Container<VBox>{

	private @FXML ListView<Employee> listDetails;

	private HRPane pane;

	private HR hr;
	private EmployeeType type;

	public HRDetails(HRPane pane, HR hr) {
		System.out.println("Constructor HRDetails");
		this.pane = pane;
		this.hr = hr;
		this.type = EmployeeType.ARCHITECT;
		load("/ui/fxml/hr/HRDetails.fxml");
	}

	public void initialize(){
		System.out.println("Initialize HRDetails");
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
}

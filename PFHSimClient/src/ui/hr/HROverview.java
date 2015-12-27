package ui.hr;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import sim.hr.EmployeeType;
import sim.hr.HR;
import ui.abstraction.Container;

public class HROverview extends Container<VBox>{
	
	private @FXML ListView<EmployeeType> listOverview;
	
	private @FXML ComboBox<EmployeeType> cbbEmpType;
	private @FXML TextField textEmpCount;
	private @FXML Button btnHire;
	private @FXML Label lblEmpCosts;
	
	private HRPane pane;
	
	private HR hr;
	
	public HROverview(HRPane pane, HR hr) {
		System.out.println("Constructor HROverview");
		this.pane = pane;
    	this.hr = hr;
		load("/ui/fxml/hr/HROverview.fxml");
	}
	
	public void initialize(){
		System.out.println("Initialize HROverview");
		//Fill the combo box with content
		cbbEmpType.setItems(FXCollections.observableArrayList(EmployeeType.values()));
		cbbEmpType.setValue(EmployeeType.ARCHITECT);
		
		lblEmpCosts.setText("0");
		
		textEmpCount.setText("0");
		
		listOverview.setItems(FXCollections.observableArrayList(EmployeeType.values()));
		listOverview.setCellFactory((lv)->{
			return new HROverviewListCell(pane, hr);
		});
	}
	
	@FXML
	private void onHireNewEmployees(ActionEvent e){
		System.out.println("Hire");
		int amount = Integer.parseInt(textEmpCount.getText());
		hr.hire(cbbEmpType.getValue(), amount);
		
		initialize();
	}
	
	@FXML
	private void onEmpTypeChanged(ActionEvent e){
		recalcCosts();
	}
	
	@FXML
	private void onEmpCountChanged(ActionEvent e){
		recalcCosts();
	}
	
	private void recalcCosts(){
		int i = 0;
		try {
			i = Integer.parseInt(textEmpCount.getText());
			if(i >= 0){
				int costs = cbbEmpType.getValue().getBaseCost() * i;
				lblEmpCosts.setText("" + costs);
			}
		} catch (Exception e2) {
		}
	}
}

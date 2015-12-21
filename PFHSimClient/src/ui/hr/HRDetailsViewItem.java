package ui.hr;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import sim.hr.Employee;
import sim.hr.HR;

public class HRDetailsViewItem {
	
	private @FXML HBox hboxContainer;
	
	private @FXML Label lblEmpName;
	private @FXML Label lblEmpCosts;
	private @FXML Label lblUpgradeLevel;
	private @FXML Label lblEmpStatus;
	
	private @FXML Button btnUpgrade;
	private @FXML Button btnFire;
	
	private HR hr;
	private Employee emp;
	
	public HRDetailsViewItem(HR hr) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/fxml/hr/HRDetailsViewItem.fxml"));
        fxmlLoader.setController(this);
        
        try{
        	this.hr = hr;
        	this.emp = null;
            fxmlLoader.load();
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
	}
	
	private void initialize(){
		if(emp != null){
			lblEmpName.setText(emp.getName());
			lblEmpCosts.setText("" + emp.getCosts());
			lblUpgradeLevel.setText("" + emp.getVisitedTrainings());
			
			String status = "";
			if(emp.isOnTraining())
				status = "On Training";
			else if(emp.isAssigned())
				status = "Working";
			else
				status = "Free";
			lblEmpStatus.setText(status);
			
			btnUpgrade.setDisable(emp.canDoTraining());
			btnFire.setDisable(emp.canBeUnassigned());
			
		}else{
			lblEmpName.setText("null");
		}
	}
	
	@FXML
	private void onUpgrade(ActionEvent e){
		
	}
	
	@FXML
	private void onFire(ActionEvent e){
		
	}
	
	void setEmployee(Employee e){
		this.emp = e;
		initialize();
	}
	
	HBox getContainer() {
		return hboxContainer;
	}
}

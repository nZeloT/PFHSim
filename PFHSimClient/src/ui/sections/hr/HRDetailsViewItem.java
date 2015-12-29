package ui.sections.hr;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import sim.hr.Employee;
import sim.hr.HR;
import ui.abstraction.Container;

public class HRDetailsViewItem extends Container<HBox> {

	private @FXML Label lblEmpName;
	private @FXML Label lblEmpCosts;
	private @FXML Label lblUpgradeLevel;
	private @FXML Label lblEmpStatus;

	private @FXML Button btnUpgrade;
	private @FXML Button btnFire;

	private HR hr;
	private Employee emp;

	public HRDetailsViewItem(HR hr) {
		this.hr = hr;
		this.emp = null;
		load("/ui/fxml/hr/HRDetailsViewItem.fxml");
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
}

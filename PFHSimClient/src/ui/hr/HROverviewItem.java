package ui.hr;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.hr.HR;
import ui.abstraction.Container;

public class HROverviewItem extends Container<HBox>{
	
	private @FXML Label lblEmpType;
	
	private @FXML TextField textOnTraining;
	private @FXML TextField textAtWorkplace;
	private @FXML TextField textFree;
	
	private @FXML Button btnView;
	
	private HR hr;
	private EmployeeType type;
	private HRPane pane;
	
	public HROverviewItem(HRPane pane, HR hr, EmployeeType t) {
        	this.hr = hr;
            this.type = t;
            this.pane = pane;
            load("/ui/fxml/hr/HROverviewItem.fxml");
	}
	
	public void initialize(){
		lblEmpType.setText(type.toString());
		
		Employee[] emps = hr.getAllOfType(type);
		int working = 0;
		int training = 0;
		int free = 0;
		for (Employee e : emps) {
			if(e.isAssigned()){
				if(e.isOnTraining())
					training++;
				else
					working ++;
			}else
				free ++;
		}
		
		textAtWorkplace.setText("" + working);
		textOnTraining.setText("" + training);
		textFree.setText("" + free);
	}
	
	@FXML
	private void onViewDetails(ActionEvent e){
		System.out.println("Details");
		pane.showDetails(type);
	}
	
	void setType(EmployeeType type) {
		this.type = type;
	}
}

package ui.hr;

import javafx.scene.layout.StackPane;
import sim.hr.EmployeeType;
import sim.hr.HR;

public class HRPane extends StackPane {
	
	private HROverview overview;
	private HRDetails details;	
	
	public HRPane(HR hr) {
		overview = new HROverview(this, hr);
		details = new HRDetails(this, hr);
		
		getChildren().addAll(overview.getContainer(), details.getContainer());
		showOverview();
	}
	
	void showOverview(){
		getChildren().get(1).setVisible(false);
		getChildren().get(0).setVisible(true);
	}
	
	void showDetails(EmployeeType t){
		details.setType(t);
		getChildren().get(0).setVisible(false);
		getChildren().get(1).setVisible(true);
	}
	
}

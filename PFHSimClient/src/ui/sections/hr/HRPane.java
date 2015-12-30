package ui.sections.hr;

import javafx.scene.layout.StackPane;
import sim.hr.EmployeeType;
import sim.hr.HR;
import ui.abstraction.UISection;

public class HRPane extends StackPane implements UISection {
	
	private HROverview overview;
	private HRDetails details;	
	
	public HRPane(HR hr) {
		System.out.println("Constructor HRPane");
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
	
	@Override
	public void update() {
		overview.initialize();
		details.initialize();
		showOverview();
	}
	
}

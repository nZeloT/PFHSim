package ui.sections.hr;

import javafx.scene.layout.StackPane;
import sim.Enterprise;
import sim.hr.EmployeeType;
import ui.abstraction.UISection;

public class HRPane extends StackPane implements UISection {
	
	private HROverview overview;
	private HRDetails details;	
	
	public HRPane(Enterprise ent, Runnable updateSidebar) {
		overview = new HROverview(this, ent, updateSidebar);
		details = new HRDetails(this, ent, updateSidebar);
		
		getChildren().addAll(overview.getContainer(), details.getContainer());
		showOverview();
	}
	
	void showOverview(){
		overview.initialize(); //because we can hire from detils pane and need to update the overview when going back
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

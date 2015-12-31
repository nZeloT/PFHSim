package ui.sections.hr;

import javafx.scene.control.ListCell;
import sim.hr.EmployeeType;
import sim.hr.HR;

public class HROverviewListCell extends ListCell<EmployeeType>{
	
	private HRPane pane;
	private HR hr;
	
	public HROverviewListCell(HRPane pane, HR hr) {
		this.hr = hr;
		this.pane = pane;
	}
	
	@Override
	protected void updateItem(EmployeeType item, boolean empty) {
		super.updateItem(item, empty);
		
		if(item != null){
			setGraphic(new HROverviewItem(pane, hr, item).getContainer());
		}
	}
	
}

package ui.sections.hr;

import javafx.scene.control.ListCell;
import sim.hr.EmployeeType;
import sim.hr.HR;

public class HROverviewListCell extends ListCell<EmployeeType>{
	
	private HRPane pane;
	private HR hr;
	private HROverviewItem data;
	
	public HROverviewListCell(HRPane pane, HR hr) {
		this.hr = hr;
		this.pane = pane;
	}
	
	@Override
	protected void updateItem(EmployeeType item, boolean empty) {
		super.updateItem(item, empty);
		
		if(item != null){
			if(data == null)
				data = new HROverviewItem(pane, hr, item);
			else
				data.setType(item);
			setGraphic(data.getContainer());
		}
	}
	
}

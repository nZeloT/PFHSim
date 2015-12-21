package ui.hr;

import javafx.scene.control.ListCell;
import sim.hr.Employee;
import sim.hr.HR;

public class HRDetailsListCell extends ListCell<Employee> {

	private HR hr;
	private HRDetailsViewItem data;
	
	public HRDetailsListCell(HR hr) {
		this.hr = hr;
	}
	
	@Override
	protected void updateItem(Employee item, boolean empty) {
		super.updateItem(item, empty);
		
		if(item != null){
			if(data == null){
				data = new HRDetailsViewItem(hr);
				data.setEmployee(item);
			}else
				data.setEmployee(item);
			setGraphic(data.getContainer());
		}
	}
	
}

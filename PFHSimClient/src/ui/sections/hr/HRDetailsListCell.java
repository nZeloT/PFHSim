package ui.sections.hr;

import javafx.scene.control.ListCell;
import sim.Enterprise;
import sim.hr.Employee;

public class HRDetailsListCell extends ListCell<Employee> {

	private Enterprise ent;
	private HRDetails parent;

	public HRDetailsListCell(Enterprise ent, HRDetails pa) {
		this.ent = ent;
		this.parent = pa;
	}

	@Override
	protected void updateItem(Employee item, boolean empty) {
		super.updateItem(item, empty);

		if(item != null){
			HRDetailsViewItem data = new HRDetailsViewItem(ent, parent);
			data.setEmployee(item);
			setGraphic(data.getContainer());
		}
	}

}

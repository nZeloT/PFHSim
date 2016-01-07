package ui.sections.hr;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import sim.Enterprise;
import sim.EnterpriseException;
import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.production.Machine;
import ui.abstraction.Container;
import ui.abstraction.Utils;

public class HRDetailsViewItem extends Container<HBox> {

	private @FXML Label lblEmpName;
	private @FXML Label lblEmpCosts;
	private @FXML Label lblUpgradeLevel;
	private @FXML Label lblEmpStatus;

	private @FXML Button btnUpgrade;
	private @FXML Button btnFire;
	
	private @FXML ComboBox<Machine> cbbAssignedWorkplace;

	private Enterprise ent;
	private Employee emp;
	
	private HRDetails parent;

	public HRDetailsViewItem(Enterprise ent, HRDetails pa) {
		this.ent = ent;
		this.emp = null;
		this.parent = pa;
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

			btnUpgrade.setDisable(!emp.canDoTraining() || !ent.getBankAccount().canBeCharged(emp.getType().getUpgradeCosts()));
			btnFire.setDisable(!emp.canBeUnassigned());
			
			if(emp.getType() == EmployeeType.PRODUCTION){
				cbbAssignedWorkplace.setVisible(true);
				cbbAssignedWorkplace.setItems(FXCollections.observableArrayList(ent.getProductionHouse().getMachines()));
				Callback<ListView<Machine>, ListCell<Machine>> factory = lv -> {
					return new ListCell<Machine>(){
						@Override
						protected void updateItem(Machine item, boolean empty){
							super.updateItem(item, empty);
							if(item != null){
								setText(item.getId());
							}
						}
					};
				};
				cbbAssignedWorkplace.setButtonCell(factory.call(null));
				cbbAssignedWorkplace.setCellFactory(factory);
				if(emp.isAssigned() && !emp.isOnTraining())
					cbbAssignedWorkplace.getSelectionModel().select((Machine)emp.getWork());
				else
					cbbAssignedWorkplace.getSelectionModel().select(-1);
				
				cbbAssignedWorkplace.setDisable(emp.isOnTraining());
			}

		}else{
			lblEmpName.setText("null");
		}
	}

	@FXML
	private void onUpgrade(ActionEvent e){
		
		try {
			ent.startEmployeeTraining(emp);
		} catch (EnterpriseException e1) {
			//this should not happen
			e1.printStackTrace();
			Utils.showError(e1.getMessage());
		}
		
		parent.initialize(); //eventually fire buttons of other employees need to be disabled
	}

	@FXML
	private void onFire(ActionEvent e){
		ent.getHR().fire(emp);
		parent.initialize();
	}
	
	@FXML
	private void onNewMachineSelected(ActionEvent e){
		if(cbbAssignedWorkplace.getValue() != emp.getWork()){
			//a new workplace was selected; try to assign
			emp.assignWorkplace(cbbAssignedWorkplace.getValue());
			initialize();
		}
	}

	void setEmployee(Employee e){
		this.emp = e;
		initialize();
	}
}

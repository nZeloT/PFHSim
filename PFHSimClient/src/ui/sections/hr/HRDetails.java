package ui.sections.hr;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import sim.Enterprise;
import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.production.Machine;
import ui.abstraction.Container;

public class HRDetails extends Container<VBox>{

	private @FXML ListView<Employee> listDetails;
	private @FXML Button btnAssignFree;
	private @FXML Button btnHire;

	private HRPane pane;
	private Runnable updateSidebar;

	private Enterprise ent;
	private EmployeeType type;

	public HRDetails(HRPane pane, Enterprise ent, Runnable updateSidebar) {
		this.pane = pane;
		this.updateSidebar = updateSidebar;
		this.ent = ent;
		this.type = EmployeeType.ARCHITECT;
		load("/ui/fxml/hr/HRDetails.fxml");
	}

	public void initialize(){
		Employee[] emps = ent.getHR().getAllOfType(type);
		listDetails.setItems(FXCollections.observableArrayList(emps));
		listDetails.setCellFactory((lv)->{
			return new HRDetailsListCell(ent, this);
		});

		if(type == EmployeeType.PRODUCTION){
			btnAssignFree.setVisible(true);
			
			btnAssignFree.setDisable(!(ent.getHR().getCountOfFreeOfType(type) > 0));
		}else
			btnAssignFree.setVisible(false);
		
		btnHire.setDisable(!ent.getHR().canHireNewEmployees(1));
		
		if(type == EmployeeType.ARCHITECT)
			btnHire.setDisable(true);
		
	}

	public void setType(EmployeeType type) {
		this.type = type;
		initialize();
	}

	@FXML
	private void onBackButton(ActionEvent e){
		pane.showOverview();
	}

	@FXML
	private void onAssignFreeEmployees(ActionEvent e){
		int free = ent.getHR().getCountOfFreeOfType(type);

		if(free > 0){
			List<Machine> machines = new ArrayList<>();
			machines.addAll(ent.getProductionHouse().getMachines());
			for (int i = 0; i < machines.size(); i++) {
				if(machines.get(i).isInUpgrade())
					machines.remove(i--);
			}
			HRAssignDialog diag = new HRAssignDialog(free, machines);
			Optional<Pair<Machine, Integer>> res = diag.showAndWait();
			if(res.isPresent()){
				Pair<Machine, Integer> p = res.get();
				//try to get some free employees and assign them to the desired machine
				Employee[] emps = ent.getHR().getUnassignedEmployees(type, p.getValue());
				if(emps != null){
					for (Employee em : emps) {
						em.assignWorkplace(p.getKey());
					}
				}
				
				initialize();
			}
		}
	}

	@FXML
	private void onHire(ActionEvent e){
		HRHireDialog diag = new HRHireDialog(ent.getHR().getRemainingHRCapacity(), type);
		diag.disableTypeChooser(true);
		Optional<Pair<EmployeeType, Integer>> res = diag.showAndWait();

		if(res.isPresent()){
			Pair<EmployeeType, Integer> p = res.get();
			Employee[] emps = ent.getHR().hire(p.getKey(), p.getValue());
			ent.autoAssignEmployees(emps); //try to auto assign them
			updateSidebar.run();
			initialize();
		}
	}
	
}

package ui.sections.hr;

import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import sim.Enterprise;
import sim.hr.Employee;
import sim.hr.EmployeeType;
import ui.abstraction.Container;

public class HROverview extends Container<VBox>{

	private @FXML ListView<EmployeeType> listOverview;

	private @FXML Button btnHire;
	private @FXML Label lblCapa;
	private @FXML Label lblUsed;
	
	private HRPane pane;

	private Enterprise ent;

	public HROverview(HRPane pane, Enterprise ent) {
		this.pane = pane;
		this.ent = ent;
		load("/ui/fxml/hr/HROverview.fxml");
	}

	public void initialize(){
		listOverview.setItems(FXCollections.observableArrayList(EmployeeType.values()));
		listOverview.setCellFactory((lv)->{
			return new HROverviewListCell(pane, ent.getHR());
		});
		btnHire.setDisable(!ent.getHR().canHireNewEmployees(1));
		lblCapa.setText("" + ent.getHR().getHRCapacity());
		lblUsed.setText("" + ent.getHR().getOverallEmployeeCount());
	}
	
	@FXML
	private void onHireNewEmployees(ActionEvent e){
		HRHireDialog diag = new HRHireDialog(ent.getHR().getRemainingHRCapacity());
		Optional<Pair<EmployeeType, Integer>> res = diag.showAndWait();

		if(res.isPresent()){
			Pair<EmployeeType, Integer> p = res.get();
			Employee[] emps = ent.getHR().hire(p.getKey(), p.getValue());
			ent.autoAssignEmployees(emps); //try to auto assign them
			initialize();
		}
	}

}

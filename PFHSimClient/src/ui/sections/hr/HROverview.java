package ui.sections.hr;

import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import sim.hr.EmployeeType;
import sim.hr.HR;
import ui.abstraction.Container;

public class HROverview extends Container<VBox>{

	private @FXML ListView<EmployeeType> listOverview;

	private @FXML Button btnHire;

	private HRPane pane;

	private HR hr;

	public HROverview(HRPane pane, HR hr) {
		System.out.println("Constructor HROverview");
		this.pane = pane;
		this.hr = hr;
		load("/ui/fxml/hr/HROverview.fxml");
	}

	public void initialize(){
		System.out.println("Initialize HROverview");

		listOverview.setItems(FXCollections.observableArrayList(EmployeeType.values()));
		listOverview.setCellFactory((lv)->{
			return new HROverviewListCell(pane, hr);
		});
	}

	@FXML
	private void onHireNewEmployees(ActionEvent e){
		System.out.println("Hire");

		HRHireDialog diag = new HRHireDialog(hr.getRemainingHRCapacity());
		Optional<Pair<EmployeeType, Integer>> res = diag.showAndWait();

		if(res.isPresent()){
			Pair<EmployeeType, Integer> p = res.get();
			//TODO: add auto assignment
			hr.hire(p.getKey(), p.getValue());
			initialize();
		}
	}

}

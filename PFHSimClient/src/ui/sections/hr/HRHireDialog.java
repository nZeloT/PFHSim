package ui.sections.hr;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import sim.hr.EmployeeType;

public class HRHireDialog extends Dialog<Pair<EmployeeType, Integer>> {

	private ComboBox<EmployeeType> cbbTypes;
	private Spinner<Integer> spinAmount;
	private Label lblCosts;
	
	public HRHireDialog(int maxTohire){
		this(maxTohire, EmployeeType.ARCHITECT);
	}

	public HRHireDialog(int maxToHire, EmployeeType type) {

		setTitle("Hire new Employees");
		setHeaderText("Hire some new and happy worker :)");

		ButtonType hireButtonType = new ButtonType("Hire", ButtonData.OK_DONE);
		getDialogPane().getButtonTypes().addAll(hireButtonType, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		cbbTypes = new ComboBox<>();
		cbbTypes.setItems(FXCollections.observableArrayList(EmployeeType.values()));
		cbbTypes.getSelectionModel().select(type);

		spinAmount = new Spinner<>(1, maxToHire, 1);

		lblCosts = new Label("0");

		grid.add(new Label("Type:"), 0, 0);
		grid.add(cbbTypes, 1, 0);
		grid.add(new Label("Amount:"), 0, 1);
		grid.add(spinAmount, 1, 1);
		grid.add(new Label("Costs:"), 0, 2);
		grid.add(lblCosts, 1, 2);

		getDialogPane().setContent(grid);

		setResultConverter(dialogButton -> {
			if(dialogButton == hireButtonType && spinAmount.getValue() <= maxToHire){
				return new Pair<EmployeeType, Integer>(cbbTypes.getValue(), spinAmount.getValue());
			}

			return null;
		});

		cbbTypes.valueProperty().addListener((observable, oldValue, newValue) -> {
			recalcCosts();
		});

		spinAmount.valueProperty().addListener((observable, oldValue, newValue) -> {
			recalcCosts();
		});
		
		recalcCosts();

		Platform.runLater(() -> cbbTypes.requestFocus());
	}


	private void recalcCosts(){
		int i = 0;
		i = spinAmount.getValue();
		if(i >= 0){
			int costs = cbbTypes.getValue().getBaseCost() * i;
			lblCosts.setText("" + costs);
		}
	}
	
	public void disableTypeChooser(boolean disable){
		cbbTypes.setDisable(disable);
	}

}

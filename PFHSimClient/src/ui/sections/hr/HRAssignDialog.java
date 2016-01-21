package ui.sections.hr;

import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.Pair;
import sim.production.Machine;

public class HRAssignDialog extends Dialog<Pair<Machine, Integer>> {

	public HRAssignDialog(int maxToAssign, List<Machine> machines) {

		setTitle("Assign free Employees");
		setHeaderText("Assign some happy worker to one of your machines :)");

		ButtonType assignButtonType = new ButtonType("Assign", ButtonData.OK_DONE);
		getDialogPane().getButtonTypes().addAll(assignButtonType, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		ComboBox<Machine> cbbTypes = new ComboBox<>();
		cbbTypes.setItems(FXCollections.observableArrayList(machines));
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
		cbbTypes.setButtonCell(factory.call(null));
		cbbTypes.setCellFactory(factory);
		cbbTypes.getSelectionModel().select(0);

		Spinner<Integer> spinAmount = new Spinner<>(0, maxToAssign, 3);
		spinAmount.setEditable(true);
		
		Label lblFree = new Label("" + (maxToAssign - spinAmount.getValue()));

		grid.add(new Label("Machine:"), 0, 0);
		grid.add(cbbTypes, 1, 0);
		grid.add(new Label("Amount:"), 0, 1);
		grid.add(spinAmount, 1, 1);
		grid.add(new Label("Remaining free:"), 0, 2);
		grid.add(lblFree, 1, 2);
		
		spinAmount.valueProperty().addListener( (observable, oldValue, newValue) -> {
			lblFree.setText("" + (maxToAssign - newValue));
		});

		getDialogPane().setContent(grid);

		setResultConverter(dialogButton -> {
			if(dialogButton == assignButtonType && spinAmount.getValue() <= maxToAssign){
				return new Pair<Machine, Integer>(cbbTypes.getValue(), spinAmount.getValue());
			}

			return null;
		});

		Platform.runLater(() -> cbbTypes.requestFocus());
	}

}

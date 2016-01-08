package ui.sections.production;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import sim.procurement.ResourceType;
import sim.production.Machine;
import sim.production.WallType;
import sim.warehouse.Warehouse;

public class ChangeProductionDialog extends Dialog<Pair<WallType, Integer>> {
	
	private Label[] lblRequired;
	private Label[] lblWarehouse;
	private ResourceType[] types;
	
	private Warehouse w;
	
	public ChangeProductionDialog(Machine m, Warehouse w) {
		this.w = w;
		setTitle("Change Production");
		setHeaderText("Change Production for " + m.getId());
		
		getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);
		
		ComboBox<WallType> cbbType = new ComboBox<>(FXCollections.observableArrayList(m.getType().getWalltypesToHandle()));
		cbbType.getSelectionModel().selectedItemProperty().addListener(this::changeWallType);
		Spinner<Integer> spinAmount = new Spinner<>(0, m.getPerformance(), m.getPerformance());
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(20);
		
		types = ResourceType.values();
		lblRequired = new Label[types.length];
		lblWarehouse = new Label[types.length];
		
		for (int i = 0; i < types.length; i++) {
			grid.add(new Label(types[i].toString()), 0, i);
			lblRequired[i] = new Label("0");
			lblWarehouse[i] = new Label("0");
			grid.add(lblRequired[i], 1, i);
			grid.add(lblWarehouse[i], 2, i);
		}
		
		VBox box = new VBox(10);
		box.setPadding(new Insets(20, 150, 10, 10));
		box.getChildren().addAll(cbbType, grid, spinAmount);
		
		getDialogPane().setContent(box);
		
		setResultConverter(dialogButotn -> {
			if(dialogButotn == ButtonType.APPLY){
				return new Pair<>(cbbType.getValue(), spinAmount.getValue());
			}
			
			return null;
		});
		
		cbbType.getSelectionModel().select(m.getProductionType());	

	}
	
	private void changeWallType(ObservableValue<? extends WallType> obs, WallType oldValue, WallType newValue){
		ResourceType[] req = newValue.getRequiredResourceTypes();
		int[] amounts = newValue.getResourceCounts();
		for (int i = 0; i < lblRequired.length; i++) {
			for (int j = 0; j < req.length; j++) {
				if(types[i] == req[j]){
					lblRequired[i].setText("" + amounts[j]);
					lblWarehouse[i].setText("" + w.getStoredAmount(types[i]));
				}
			}
		}
	}
	
}

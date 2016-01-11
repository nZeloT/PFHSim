package ui.sections.production;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import sim.Enterprise;
import sim.production.MachineType;

public class BuyDialog extends Dialog<Pair<MachineType, Integer>> {

	public BuyDialog(Enterprise ent) {
		setTitle("Buy Machine");
		setHeaderText("Which Machine do you want to buy?");

		getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

		Node yesBtn = getDialogPane().lookupButton(ButtonType.YES);

		Label lblPrice = new Label("");
		Label lblRunning = new Label("");
		
		Spinner<Integer> spinAmount = new Spinner<>(1, 10, 1);
		spinAmount.setEditable(true);

		ComboBox<MachineType> cbbType = new ComboBox<>(FXCollections.observableArrayList(MachineType.values()));
		cbbType.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			lblPrice.setText("Price: " + (newValue.getPrice() * spinAmount.getValue()) + " €");
			lblRunning.setText("Costs: " + (newValue.getBaseCosts() * spinAmount.getValue()) + " €");
			yesBtn.setDisable(!ent.getBankAccount().canBeCharged(newValue.getBaseCosts() * spinAmount.getValue()));
		});
		cbbType.getSelectionModel().select(0);
		
		VBox box = new VBox(10);
		box.setPadding(new Insets(20, 150, 10, 10));
		box.getChildren().addAll(cbbType, spinAmount, lblPrice, lblRunning);
		
		getDialogPane().setContent(box);

		setResultConverter(dialogButton -> {
			if(dialogButton == ButtonType.YES){
				return new Pair<>(cbbType.getValue(), spinAmount.getValue());
			}

			return null;
		});
	}

}

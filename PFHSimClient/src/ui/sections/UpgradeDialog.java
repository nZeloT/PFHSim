package ui.sections;

import java.util.HashMap;
import java.util.Map.Entry;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ui.abstraction.Triple;

public class UpgradeDialog extends Dialog<Boolean> {

	public UpgradeDialog(String upgradeTarget, int costs, int duration, HashMap<String, Triple<Integer, Integer, Integer>> upgradeFactors) {
		setTitle("Upgrade " + upgradeTarget);
		setHeaderText("Do you want to Upgrade " + upgradeTarget);
		
		getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
		
		
		GridPane gridFactor = new GridPane();
		gridFactor.setHgap(10);
		gridFactor.setVgap(20);
		
		gridFactor.add(new Label("Attribut"), 0, 0);
		gridFactor.add(new Label("Before"), 1, 0);
		gridFactor.add(new Label("Delta"), 2, 0);
		gridFactor.add(new Label("After"), 3, 0);
		
		int i = 1;
		for (Entry<String, Triple<Integer, Integer, Integer>> e : upgradeFactors.entrySet()) {
			gridFactor.add(new Label(e.getKey()), 0, i);
			gridFactor.add(new Label("" + e.getValue().s), 1, i);
			gridFactor.add(new Label((e.getValue().t > 0 ? "+" : "") + e.getValue().t), 2, i);
			gridFactor.add(new Label("" + e.getValue().u), 3, i);
			
			i++;
		}
		
		VBox box = new VBox(10);
		box.setPadding(new Insets(20, 150, 10, 10));
		box.getChildren().add(gridFactor);
		box.getChildren().add(new Separator(Orientation.HORIZONTAL));
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		
		grid.add(new Label("Costs"), 0, 0);
		grid.add(new Label("" + costs), 1, 0);
		grid.add(new Label("Duration"), 0, 1);
		grid.add(new Label("" + duration), 1, 1);
		
		box.getChildren().add(grid);
		box.getChildren().add(new Separator(Orientation.HORIZONTAL));
		box.getChildren().add(new Label("Continue?"));
		
		getDialogPane().setContent(box);
		
		setResultConverter(dialogButton -> {
			if(dialogButton == ButtonType.YES)
				return true;
			else
				return false;
		});
	}

}

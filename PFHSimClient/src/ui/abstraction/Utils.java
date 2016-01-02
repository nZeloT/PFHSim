package ui.abstraction;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Utils {

	public static void showError(String text){
		Alert a = new Alert(AlertType.ERROR);
		a.setContentText(text);
		a.setHeaderText("Ouch :(");
		a.setTitle("Something went wrong ...");
		a.showAndWait();
	}

}

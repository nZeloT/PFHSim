package ui;

import java.util.Optional;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class JoinDialog {
	
	private boolean userEmpty;
	private boolean serverEmpty;
	
	public Pair<String, String> showAndWait(){
		userEmpty = serverEmpty = true;
		
		// Create the custom dialog.
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Join Server");
		dialog.setHeaderText("Welcome to PFHSim");

		// Set the icon (must be included in the project).
		dialog.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/ui/res/small.JPG"))));

		// Set the button types.
		ButtonType joinButtonType = new ButtonType("Join", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(joinButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField user = new TextField();
		user.setPromptText("Name");
		TextField serverip = new TextField();
		serverip.setPromptText("Server IP");

		grid.add(new Label("Name:"), 0, 0);
		grid.add(user, 1, 0);
		grid.add(new Label("Server IP:"), 0, 1);
		grid.add(serverip, 1, 1);

		// Enable/Disable join button depending on whether a name was entered.
		Node loginButton = dialog.getDialogPane().lookupButton(joinButtonType);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		user.textProperty().addListener((observable, oldValue, newValue) -> {
			userEmpty = newValue.trim().isEmpty();
		    loginButton.setDisable(userEmpty || serverEmpty);
		});
		
		serverip.textProperty().addListener((observable, oldValue, newValue) -> {
			serverEmpty = newValue.trim().isEmpty();
			loginButton.setDisable(userEmpty || serverEmpty);
		});

		dialog.getDialogPane().setContent(grid);

		// Request focus on the username field by default.
		Platform.runLater(() -> user.requestFocus());

		// Convert the result to a username-password-pair when the login button is clicked.
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == joinButtonType) {
		        return new Pair<>(user.getText(), serverip.getText());
		    }
		    return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();
		if(result.isPresent())
			return result.get();
		else
			return null;
	}
}

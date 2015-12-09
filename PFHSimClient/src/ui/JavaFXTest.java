package ui;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class JavaFXTest extends Application {

	@Override
	public void start(Stage primaryStage) {
		URL location = getClass().getResource("fxml/ui_test.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader(location);

		try {
			Pane root = (Pane)fxmlLoader.load();
			primaryStage.setScene(new Scene(root));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

package src.ui;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import ui.offers.OfferOverviewController;

public class JavaFX extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		URL location = getClass().getResource("/ui/fxml/ui_test.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader(location);

		try {
			SplitPane root = (SplitPane)FXMLLoader.load(getClass().getResource("/ui/fxml/Menu.fxml"));
			primaryStage.setScene(new Scene(root));
			VBox box = (VBox)FXMLLoader.load(getClass().getResource("/ui/fxml/offers/OfferOverview.fxml"));
			primaryStage.setScene(new Scene(box));
			OfferOverviewController x = new OfferOverviewController();
		} catch (IOException e) {
			e.printStackTrace();  
		} 
		primaryStage.show();
	} 

	public static void main(String[] args) { 
		launch(args);
	} 
}

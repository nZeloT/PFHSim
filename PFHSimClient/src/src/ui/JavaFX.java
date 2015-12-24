package src.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sim.Enterprise;
import ui.hr.HRPane;

public class JavaFX extends Application {
	
	@Override
	public void start(Stage primaryStage) {
//		URL location = getClass().getResource("/ui/fxml/ui_test.fxml");
//		FXMLLoader fxmlLoader = new FXMLLoader(location);

//		try {
//			SplitPane root = (SplitPane)FXMLLoader.load(getClass().getResource("/ui/fxml/Menu.fxml"));
//			primaryStage.setScene(new Scene(root));
//			primaryStage.setScene(new Scene(new HRPane(null)));
//		} catch (IOException e) {
//			e.printStackTrace(); 
//		}
		try {
			Enterprise e = new Enterprise(null);
			SplitPane root = (SplitPane)FXMLLoader.load(getClass().getResource("/ui/fxml/Menu.fxml"));
			primaryStage.setScene(new Scene(root));
			
			VBox boxP = (VBox)FXMLLoader.load(getClass().getResource("/ui/fxml/offers/OfferOverview.fxml"));
 
//			Warehouse ware = new Warehouse(e); 
//			VBox box = ware.getBox();
		
			primaryStage.setScene(new Scene(boxP));   
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

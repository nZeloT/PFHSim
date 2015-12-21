package src.ui;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

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
import sim.Enterprise;
import sim.procurement.ResourceMarket;
import sim.procurement.ResourceType;

public class JavaFX extends Application {

	@Override
	public void start(Stage primaryStage) {
		URL location = getClass().getResource("/ui/fxml/ui_test.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader(location);

		try {

			ResourceType[] resources = ResourceType.values();
			HashMap<ResourceType, Integer> costs = new HashMap<>();
			for (int i = 0; i < resources.length; i++) {
				costs.put(resources[i], resources[i].getBasePrice());
			}
			ResourceMarket market = new ResourceMarket(costs);
			Enterprise e = new Enterprise(market);
			SplitPane root = (SplitPane) FXMLLoader.load(getClass().getResource("/ui/fxml/Menu.fxml"));
			primaryStage.setScene(new Scene(root));

			Procurement proc = new Procurement(e);
			VBox boxP = proc.getBox();
//			VBox boxP = (VBox) FXMLLoader.load(getClass().getResource("/ui/fxml/Procurement.fxml"));

			// Warehouse ware = new Warehouse(e);
			// VBox box = ware.getBox();

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

package ui;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.Pair;
import net.ServerConnection;
import sim.Enterprise;
import sim.procurement.ResourceMarket;
import sim.procurement.ResourceType;

public class UIClient extends Application {

	//---------------------------------------------------
	public static void main(String[] args) {
		launch(args);
	}
	//---------------------------------------------------

	private static final int SERVER_PORT = 44444;

	private ServerConnection server;

	@Override
	public void start(Stage stg) throws Exception {
//		Pair<String, String> joinInfo = new JoinDialog().showAndWait();
//		if(joinInfo != null){
//			//1. ok we got some data which server to join; now try to connect
//			Socket s = null;
//			try{
//				s = new Socket(joinInfo.getValue(), SERVER_PORT);
//				server = new ServerConnection(s);
//				server.start();
//			}catch(IOException io){
//				showError("Couldn't connect to server.");
//				Platform.exit();
//				io.printStackTrace();
//			}
//			
//		}else{
//			showError("No data provided to join a server. See you later.");
//			Platform.exit();
//		}
		
		//TODO fix
		ResourceType[] resources = ResourceType.values();
		HashMap<ResourceType, Integer> costs = new HashMap<>();
		for (int i = 0; i < resources.length; i++) {
			costs.put(resources[i], resources[i].getBasePrice());
		}
		ResourceMarket market = new ResourceMarket(costs);
		MainWindow w = new MainWindow(new Enterprise(market));
		stg.setScene(new Scene(w.getContainer()));
		stg.setTitle("PFHSim Client");

		stg.setMinHeight(650);
		stg.setMinWidth(900);

		stg.show();

	}
	
	private void showError(String text){
		Alert a = new Alert(AlertType.ERROR);
		a.setContentText(text);
		a.setHeaderText("Ouch :(");
		a.setTitle("Something went wrong ...");
		a.showAndWait();
	}

}

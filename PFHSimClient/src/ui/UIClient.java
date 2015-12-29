package ui;

import java.util.HashMap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sim.Enterprise;
import sim.procurement.ResourceMarket;
import sim.procurement.ResourceType;

public class UIClient extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stg) throws Exception {
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

}

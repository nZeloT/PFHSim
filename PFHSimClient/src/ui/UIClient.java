package ui;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import net.ServerConnection;
import net.shared.ClientMessage;
import net.shared.ServerMessage;
import sim.Enterprise;
import sim.EnterpriseException;
import sim.procurement.ResourceMarket;
import sim.production.MachineType;
import ui.abstraction.ProgressDialog;
import ui.abstraction.Utils;

public class UIClient extends Application {

	//---------------------------------------------------
	public static void main(String[] args) {
		launch(args);
	}
	//---------------------------------------------------

	private static final int SERVER_PORT = 44444;

	private ServerConnection server;
	private Enterprise ent;
	private ResourceMarket market;
	
	private String name;

	@Override
	public void start(Stage stg) throws Exception {
		if(initialise()){

			//TODO this is only for testing without server stuff :D
//			ResourceType[] resources = ResourceType.values();
//			HashMap<ResourceType, Integer> costs = new HashMap<>();
//			for (int i = 0; i < resources.length; i++) {
//				costs.put(resources[i], resources[i].getBasePrice());
//			}
//			ResourceMarket market = new ResourceMarket(costs);
//			ent = new Enterprise(market);
			ent.buyMachine(MachineType.WOODWALL_MACHINE);
			ent.buyMachine(MachineType.BRICKWALL_MACHINE);
			ent.buyMachine(MachineType.BRICKWALL_MACHINE);
			ent.buyMachine(MachineType.WOODWALL_MACHINE);
			
			MainWindow w = new MainWindow(ent, this::doRoundTrip);
			stg.setScene(new Scene(w.getContainer()));
			stg.setTitle("PFHSim Client - " + name);

			stg.setMinHeight(650);
			stg.setMinWidth(900);

			stg.show();
			
		}

	}

	private boolean initialise(){
		Pair<String, String> joinInfo = new JoinDialog().showAndWait();
		if(joinInfo != null){
			
			ProgressDialog prog = new ProgressDialog();
			prog.setProgress(0);
			prog.show();
			
			//1. ok we got some data which server to join; now try to connect
			Socket s = null;
			try{
				s = new Socket(joinInfo.getValue(), SERVER_PORT);
				server = new ServerConnection(s);
				server.start();
			}catch(IOException io){
				prog.hide();
				Utils.showError("Couldn't connect to server.");
				io.printStackTrace();
				return false;
			}
			prog.setProgress(0.33d);
			
			//2. now we`re connected. server is the first to send some info which
			//   then allows us to initialise the resource market and the enterprise
			while(!server.hasAnswered()){
				try{ Thread.sleep(500); }
				catch(InterruptedException e){/* this should be okay :) */}
			}
			
			ServerMessage msg = server.retrieveAnswer();
			prog.setProgress(0.66d);
			
			//3. we got the first message so we now can initialise the game objects
			market = new ResourceMarket(msg.getNewResourcePrices());
			ent = new Enterprise(market);
			prog.setProgress(1);
			
			name = joinInfo.getKey();
			
			prog.hide();

		}else{
			Utils.showError("No data provided to join a server. See you later.");
			return false;
		}

		return true;
	}
	
	@Override
	public void stop() throws Exception {
		super.stop();
		server.close();
	}
	
	public List<EnterpriseException> doRoundTrip(){
		//TODO: what to do when game ends
		
		//1. user input is finished; prepare the message
		ClientMessage clnt = new ClientMessage(
				new HashMap<>(market.getSoldResources()),
				new ArrayList<>(ent.getSales().getOffers())
		);
		server.placeMessasge(clnt);
		
		//2. wait for the server to answer
		while(!server.hasAnswered()){
			try {TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException e) {
				//this is intentionally not thrown ;)
			}
		}
		
		ServerMessage msg = server.retrieveAnswer();
		
		//3. process the answer and do a simulation step on the enterprise
		market.setNewResourcePrices(msg.getNewResourcePrices());
		return ent.doSimulationStep(msg.getSoldOfferAmounts());
	}

}

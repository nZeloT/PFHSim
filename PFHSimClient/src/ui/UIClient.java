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
import net.ServerConnection;
import net.shared.ClientMessage;
import net.shared.Pair;
import net.shared.ServerMessage;
import sim.Enterprise;
import sim.EnterpriseException;
import sim.procurement.ResourceMarket;
import ui.abstraction.ProgressDialog;
import ui.abstraction.Triple;
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
	private MainWindow w;

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
//			ent.buyMachine(MachineType.WOODWALL_MACHINE);
//			ent.buyMachine(MachineType.BRICKWALL_MACHINE);
//			ent.buyMachine(MachineType.BRICKWALL_MACHINE);
//			ent.buyMachine(MachineType.WOODWALL_MACHINE);
			
			w = new MainWindow(ent, this::doRoundTrip);
			stg.setScene(new Scene(w.getContainer()));
			stg.setTitle("PFHSim Client - " + name);

			stg.setMinHeight(650);
			stg.setMinWidth(900);

			stg.show();
			
		}

	}

	private boolean initialise(){
		javafx.util.Pair<String, String> joinInfo = new JoinDialog().showAndWait();
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
		server.join();
		server = null;
		
		w.cancleTimer();
	}
	
	public Triple<Boolean, Integer, Integer> doRoundTrip(Pair<List<EnterpriseException>, List<Pair<String, Integer>>> lists){
		if(server.isClosed()){
			//the server connection was closed already
			try {server.join();	} catch (InterruptedException e) 
			{e.printStackTrace(); /* do we care? */ }
			server = null;
			return new Triple<>(true, -1, -1);
		}//otherwise we hope that it will life for the whole round trip
		
		//1. user input is finished; prepare the message
		ClientMessage clnt = new ClientMessage(
				name,
				new HashMap<>(market.getSoldResources()), 
				new ArrayList<>(ent.getSales().getOffers()),
				new HashMap<>(ent.getPerRoundBuildAmounts()),
				ent.getBankAccount().isOnLimit() 
		);   
		server.placeMessasge(clnt);  
		 
		//2. wait for the server to answer
		while(!server.hasAnswered() && !server.isClosed()){
			try {TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException e) {
				//this is intentionally not thrown ;)
			}
		}
		
		ServerMessage msg = server.retrieveAnswer();
		if(msg == null)
			return new Triple<>(true, -1, -1);
		
		//3. process the answer and do a simulation step on the enterprise
		market.doSimStep(msg.getNewResourcePrices());
		lists.k.addAll(ent.doSimulationStep(msg.getSoldOfferAmounts()));
		lists.v.addAll(msg.getTopList());
		
		//4. has the game ended?
		//a few different things can happen which shall result in the end game screen being presented:
		// -> the server connection was closed; was already checked
		// -> the user reached the bank limit
		// -> the server has send the end game flag
		
		if(msg.hasGameEnded()){
			//the server send the end game flag.
			if(!server.isClosed()){
				server.close();
				try {server.join();	} catch (InterruptedException e) 
				{e.printStackTrace(); /* do we care? */ }
				server = null;
			}
			
			return new Triple<>(true, -1, -1);
		}
		
		if(ent.getBankAccount().isOnLimit()){
			//the user reached the bank limit
			//send info to the server and terminate the connection
			clnt = new ClientMessage(name, null, null, null, true);
			server.placeMessasge(clnt);
			//give the network and the server some time to receive the message not really necessary though
			try {TimeUnit.MILLISECONDS.sleep(750);
			} catch (InterruptedException e) {
				//intentionally empty
			}
			//terminate the connection
			server.close();
			try {server.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			server = null;
			
			return new Triple<>(true, -1, -1);
		}

		return new Triple<>(false, msg.getRound(), msg.getScore());
	}

}

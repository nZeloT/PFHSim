package core;

import java.util.ArrayList;

import net.shared.ClientMessage;
import net.shared.ServerMessage;
import sim.procurement.ResourceMarket;
import sim.simulation.sales.Offer;

public class ServerSimulation {
	
	public ServerMessage[] simulationStep(ClientMessage[] clntMsgs){
		return new ServerMessage[clntMsgs.length];
	}
	
	public boolean isFinished(){
		return false;
	}
	
	public ServerMessage getSetupMessage(){
		return new ServerMessage(ResourceMarket.get().getPrices(), new int[0], false, new ArrayList<Offer>());
	}
}

package core;

import java.util.ArrayList;

import net.shared.ClientMessage;
import net.shared.ServerMessage;
import sim.procurement.ResourceMarket;
import sim.simulation.sales.Offer;

public class ServerSimulation {
	
	private ResourceMarket market;
	
	public ServerSimulation() {
		market = new ResourceMarket();
	}
	
	public ServerMessage[] simulationStep(ClientMessage[] clntMsgs){
		return new ServerMessage[clntMsgs.length];
	}
	
	public boolean isFinished(){
		return false;
	}
	
	public ServerMessage getSetupMessage(){
		return new ServerMessage(market.getCosts(), new int[0], false, new ArrayList<Offer>());
	}
}

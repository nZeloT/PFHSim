

import java.util.List;

import core.ServerSimulation;
import net.shared.ClientMessage;
import net.shared.ServerMessage;
import sim.Enterprise;
import sim.EnterpriseException;
import sim.abstraction.Pair;
import sim.procurement.ResourceMarket;

public class Helper {
	
	private ServerSimulation sim;
	
	public Helper() {
		sim = new ServerSimulation();
	}
	
	public ResourceMarket[] initMarkets(int count){
		ResourceMarket[] markets = new ResourceMarket[count];
		ServerMessage ini = sim.getSetupMessage();
		for (int i = 0; i < markets.length; i++) {
			markets[i] = new ResourceMarket(ini.getNewResourcePrices());
		}
		return markets;
	}
	
	public List<Pair<String, Integer>> doRoundTrip(Enterprise... e){
		ClientMessage[] clnt = new ClientMessage[e.length];
		for (int i = 0; i < clnt.length; i++) {
			clnt[i] = new ClientMessage("" + i, 
					e[i].getMarket().getSoldResources(), 
					e[i].getSales().getOffers(), 
					e[i].getPerRoundBuildAmounts(),
					e[i].getBankAccount().isOnLimit());
		}
		
		ServerMessage[] serv = sim.simulationStep(clnt);
		
		for (int i = 0; i < serv.length; i++) {
			e[i].getMarket().doSimStep(serv[i].getNewResourcePrices());
			List<EnterpriseException> ex = e[i].doSimulationStep(serv[i].getSoldOfferAmounts());
			for (EnterpriseException enterpriseException : ex) {
				System.err.println(enterpriseException.getMessage());
			}
		}
		
		return serv[0].getTopList();
	}


}

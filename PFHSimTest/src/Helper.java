

import java.util.List;

import core.ServerSimulation;
import net.shared.ClientMessage;
import net.shared.ServerMessage;
import sim.Enterprise;
import sim.EnterpriseException;
import sim.abstraction.Pair;
import sim.procurement.ResourceMarket;
import sim.procurement.ResourceType;
import sim.warehouse.Warehouse;

public class Helper {
	
	private ServerSimulation sim;
	private static int[] amounts = { 1000, 1000, 1000, 1000, 1000, 2000 };
	
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
	
	/**
	 * some basic adjustments which have to be made every round
	 * 
	 * @param p
	 *            player you want to make the adjustments on
	 */
	public static void roundHelper(Enterprise p) {

		try {
			Warehouse house = p.getWarehouse();

			if (house.getUtilization() / house.getCapacity() >= 0.75) {
				p.startWarehouseExtension();
			}
			ResourceType[] types = ResourceType.values();
			for (int i = 0; i < types.length; i++) {
				int current = house.getStoredAmount(types[i]);
				int tobebought = amounts[i] - current;
				if (tobebought > 0) {
					p.buyResources(types[i], tobebought);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}

package sim.procurement;

import java.util.HashMap;

/**
 * this is the SERVER only part of the ResourceMarket
 */
public class ServerResourceMarket {
	
	private HashMap<ResourceType, Integer> costs;

	/**
	 * initialize with some default prices
	 */
	public ServerResourceMarket(){
		costs = new HashMap<ResourceType, Integer>();
		
		for (ResourceType t : ResourceType.values()) {
			costs.put(t, t.getBasePrice());
		}
	}
	
	/**
	 * Adjust the Prices at the end of a period based on the transactions;
	 * 	Logik atm.: For each Player(not yet implemented!!!):
	 *  More than 200 sold --> price +15%
	 * 	150-200 sold 	   --> price stays
	 * 	less than 150 sold --> price -15%
	 */
	public void adjustPrices(HashMap<ResourceType, Integer> soldAmounts){
		for (ResourceType t : ResourceType.values()) {
			int amount = soldAmounts.get(t);
			int costs = this.costs.get(t);
			if (amount < 150) {
				costs *= 0.85;
			}
			if (amount > 200) {
				costs *= 1.15;
			}
			
			if(costs < 0.8 * t.getBasePrice())
				costs = (int) (0.8 * t.getBasePrice());
			if(costs < 1)
				costs = 1;
			
			this.costs.put(t, costs);
		}
	}
	
	public HashMap<ResourceType, Integer> getCosts() {
		return costs;
	}
}

package sim.procurement;

import java.util.HashMap;

/**
 * this is the SERVER only part of the ResourceMarket
 */
public class ServerResourceMarket {
	
	private HashMap<ResourceType, Integer> costs;
	private final int baseAmount = 300;
	private final int baseRange = 100; //notice: the ranges counts up AND down, a range 50 with a base= 100 means range 50-150
	private final double coefficent = 0.15;

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
	 * 	Logik atm.: For each Player:
	 *  More than x+range sold --> price +15%
	 * 	x +-range sold 	   --> price stays
	 * 	less than x-range sold --> price -15%
	 */
	public void adjustPrices(HashMap<ResourceType, Integer> soldAmounts, int numOfEs){
		int base = baseAmount * numOfEs; //each round as players might loose before the game is ending
		int range = numOfEs * baseRange;
		for (ResourceType t : ResourceType.values()) {
			int amount = soldAmounts.get(t);
			//remove old prices from the list
			int costs = this.costs.remove(t);
			double c = 1; //assume no changes
			if (amount < (base-range)) {
				c = c - coefficent*(amount/(base-range)); //correct for low prices
			}
			if (amount > (base+range)) {
				c = c + coefficent*(amount/(base+range)); // same for high prices
			}
			//set some borders
			if (c < 0.9) {
				c = 0.9;
			} else if (c > 1.2){
				c = 1.2;
			}
			costs *= c;
			
			//some special cases
			if(costs < 0.8 * t.getBasePrice())
				costs = (int) (0.8 * t.getBasePrice());
			if(costs < 1)
				costs = 1;
			//put new prices in the List.
			this.costs.put(t, costs);
		}
	}
	
	public HashMap<ResourceType, Integer> getCosts() {
		return costs;
	}
}

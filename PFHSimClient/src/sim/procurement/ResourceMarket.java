package sim.procurement;

import java.util.HashMap;

import sim.ExceptionCategorie;

/**
 * this is the CLIENT only part of the resource market
 */
public class ResourceMarket {
	
	private HashMap<ResourceType, Integer> soldResources;
	private HashMap<ResourceType, Integer> costs;
	
	public ResourceMarket(HashMap<ResourceType, Integer> costs) {
		this.soldResources = new HashMap<>();
		for (ResourceType t : ResourceType.values()) {
			soldResources.put(t, 0);
		}
		this.costs = costs;
	}
	
	public Resource[] buyResources(ResourceType t, int amount) throws ResourceMarketException{
		if (amount < 1) {
			throw new ResourceMarketException(this, "A positive amount is required!", ExceptionCategorie.PROGRAMMING_ERROR);
		}
		
		int alreadySold = soldResources.get(t);
		soldResources.put(t, alreadySold + amount);
		
		Resource[] res = new Resource[amount];
		for (int i = 0; i < res.length; i++) {
			res[i] = new Resource(costs.get(t), t);
		}
		
		return res;
	}
	
	public int getPrice(ResourceType t){
		return costs.get(t);
	}
	
	public void setNewResourcePrices(HashMap<ResourceType, Integer> prices){
		this.costs = prices;
	}
	
	public HashMap<ResourceType, Integer> getSoldResources() {
		return soldResources;
	}
	
	public HashMap<ResourceType, Integer> getCosts() {
		return costs;
	}
	
	public void resetAmounts(){
		for (ResourceType t : ResourceType.values()) {
			soldResources.put(t, 0);
		}
	}
}

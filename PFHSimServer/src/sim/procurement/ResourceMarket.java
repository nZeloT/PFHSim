package sim.procurement;


import java.util.HashMap;


public class ResourceMarket {
	
	/**
	 * ResourceMarket a singleton because only one market exists
	 * and needs to be known to every enterprise
	 */
	private static ResourceMarket instance = new ResourceMarket();
	
	public static ResourceMarket get(){
		return instance;
	}
	

	private HashMap<ResourceType,ResourceListItem> resources;

	
	public HashMap<ResourceType, ResourceListItem> getResources() {
		return resources;
	}
	
/**
 * Don't use this!!!!!, use the get Method because this is a singleton implementation and only public for testing
 */
	public ResourceMarket(){
		resources = new HashMap<ResourceType,ResourceListItem>();
		ResourceType types [] = ResourceType.values();
		for (int i = 0; i < types.length; i++) {
			resources.put(types[i], new ResourceListItem(types[i]));
		}
	}
	
	/**
	 * Adjust the Prices at the end of a period based on the transactions;
	 * 	Logik atm.: For each Player(not yet implemented!!!):
	 *  More than 200 sold --> price +15%
	 * 	150-200 sold 	   --> price stays
	 * 	less than 150 sold --> price -15%
	 */
	public void adjustPrices(){		
		ResourceType types [] = ResourceType.values();
		for (int i = 0; i < types.length; i++) {
			ResourceListItem tmpresource = resources.get(types[i]);
			int amount = tmpresource.getAmountofSoldItems();
			int costs = tmpresource.getCosts();
			if (amount < 150) {
				costs *= 0.85;
			}
			if (amount > 200) {
				costs *= 1.15;
			}
			tmpresource.setCosts(costs);
		}
	}
	
	/**
	 * 	Get resources from the resource market
	 * @param resource The resource to sell
	 * @param amount The amount of the requested Resource
	 * If enough on the market, get the Resource Array, otherwise get null
	 */
	public Resource[] sellResources(ResourceType type, int amount) throws ResourceMarketException{
		if (amount < 1) {
			throw new ResourceMarketException("Please specify a positiv amount!");
		}
		ResourceListItem specialResource = resources.get(type);
		return specialResource.get(amount);
	}
	
	
}

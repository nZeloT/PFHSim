package sim.simulation.purchase;

import java.util.HashMap;

import sim.procurement.Resource;
import sim.procurement.ResourceType;

/**
 * ResourceMarket a singleton because only one market exists
 * and needs to be known to every enterprise
 */
public class ResourceMarket {

	private static ResourceMarket instance = new ResourceMarket();
//	private ArrayList<ResourceList> resources;
	private HashMap<ResourceType, ResourceListItem> resources;
	
	public HashMap<ResourceType, ResourceListItem> getResources() {
		return resources;
	}

	private int basiccapacity = 100;

	public static ResourceMarket get(){
		return instance;
	}

	public ResourceMarket(){
		resources = new HashMap<ResourceType,ResourceListItem>();
		ResourceType types [] = ResourceType.values();
		for (int i = 0; i < types.length; i++) {
			resources.put(types[i], new ResourceList(50, types[i], 10));
		}
	}
	/**
	 * Generate new Resources at the end of a Simulation
	 * 	
	 */
	public void generateNewResources(){		
		ResourceType types [] = ResourceType.values();
		for (int i = 0; i < types.length; i++) {
			ResourceListItem tmpresource = resources.get(types[i]);
			int toAdd = basiccapacity -  tmpresource.getAmount();
			tmpresource.addItems(toAdd);
		}
	}
	/**
	 * 	Get resources from the resource market
	 * @param resource The resource to sell
	 * @param amount The amount of the requested Resource
	 * If enough on the market, get the Resource Array, otherwise get null
	 */
	public Resource[] sellResources(ResourceType type, int amount){
		ResourceList specialResource = resources.get(type);
		if(amount > specialResource.getAmount()){
			return null;
		} else {
			return specialResource.getndelete(amount);
		}
		
	}
}
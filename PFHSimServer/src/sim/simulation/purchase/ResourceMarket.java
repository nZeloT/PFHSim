package sim.simulation.purchase;

import java.util.ArrayList;

import com.sun.org.apache.bcel.internal.generic.IfInstruction;

import sim.procurement.Resource;
import sim.procurement.ResourceList;
import sim.procurement.ResourceType;

/**
 * ResourceMarket a singleton because only one market exists
 * and needs to be known to every enterprise
 */
public class ResourceMarket {

	private static ResourceMarket instance = new ResourceMarket();
	private ArrayList<ResourceList> resources;
	int basiccapacity = 100;

	public static ResourceMarket get(){
		return instance;
	}

	public ResourceMarket(){
		resources = new ArrayList<ResourceList>();
		ResourceType types [] = ResourceType.values();
		for (int i = 0; i < types.length; i++) {
			resources.add(new ResourceList(50, types[i], 10));
		}
	}
	/**
	 * Generate new Resources at the end of a Simulation
	 * 	
	 */
	private void generateNewResources(){		
		ResourceType types [] = ResourceType.values();
		for (int i = 0; i < types.length; i++) {
			int toAdd = basiccapacity - resources.get(i).getSize();
			resources.add(new ResourceList(toAdd, types[i], 10));
		}
	}
	/**
	 * 	Get resources from the resource market
	 * @param resource The resource to sell
	 * @param amount The amount of the requested Resource
	 * If enough on the market, get the Resource Array, otherwise get null
	 */
	private Resource[] sellResources(ResourceType type, int amount){
		ResourceType tmp = null;
		int z = 0;
		do{
			tmp= resources.get(z).getType();
			z++;
		}while(tmp!=type);
		ResourceList specialResource= resources.get(z);
		if(amount > specialResource.getSize()){
			return null;
		} else {
			return specialResource.getndelete(amount);
		}
		
	}
}
package sim.simulation.purchase;

import java.util.ArrayList;

import sim.procurement.Resource;
import sim.procurement.ResourceType;

public class ResourceList {
	
	private ResourceType resourceType;
	private int amount;
	private int costs;
	
	public ResourceList(int amount, ResourceType type, int costs){
		this.amount=amount;
		this.resourceType=type;
		this.costs=costs;	
	}
	
	public void addItems(int amount){
		this.amount += amount;
	}
	
	public Resource[] getndelete(int amount){
		Resource[] resources = new Resource[amount];
		for (int i = 0; i < amount; i++) {
			resources[i] = new Resource(costs, resourceType);
		}
		return resources;
	}
	
	public int getAmount(){
		return amount;
	}
	
	public ResourceType getType(){
		return resourceType;
	}
	
	public int getCosts(){
		return costs;
	}

}
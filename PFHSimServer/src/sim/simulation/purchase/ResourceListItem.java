

package sim.simulation.purchase;


import sim.procurement.Resource;
import sim.procurement.ResourceType;

public class ResourceListItem {
	
	private int amountofSolditems;
	private int costs;
	private ResourceType type;
	
	public ResourceListItem(int costs, ResourceType type){
		this.costs = costs;	
		this.type = type;
		amountofSolditems = 0;
	}
	
	
	public Resource[] get(int amount){
		Resource[] resources = new Resource[amount];
		amountofSolditems += amount;
		for (int i = 0; i < amount; i++) {
			resources[i] = new Resource(costs, type);
		}
		return resources;
	}
	
	public int getAmountofSoldItems(){
		return amountofSolditems;
	}
	

	
	public int getCosts(){
		return costs;
	}
	
	public void setCosts(int newCosts){
		this.costs = newCosts;
	}

}
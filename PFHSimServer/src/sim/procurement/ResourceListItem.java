

package sim.procurement;

public class ResourceListItem {
	
	private int amountofSolditems;
	private final ResourceType type;
	
	public ResourceListItem(ResourceType type){
		this.type = type;
		amountofSolditems = 0;
	}
	
	
	public Resource[] get(int amount){
		Resource[] resources = new Resource[amount];
		amountofSolditems += amount;
		for (int i = 0; i < amount; i++) {
			resources[i] = new Resource(type.getPrice(), type);
		}
		return resources;
	}
	
	public int getAmountofSoldItems(){
		return amountofSolditems;
	}
	

	
	public int getCosts(){
		return type.getPrice();
	}
	
	public void setCosts(int newCosts){
		type.setPrice(newCosts);
	}

}
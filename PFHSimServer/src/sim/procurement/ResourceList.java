package sim.procurement;

import java.util.ArrayList;

public class ResourceList {
	
	private ArrayList<Resource> resource;
	
	public ResourceList(int amount, ResourceType type, int costs){
		for (int j = 0; j < amount; j++) {
			resource.add(new Resource(costs, type));
		}
	}
	
	public void addItems(int amount){
		Resource tmp = resource.get(1);
		for (int i = 0; i < amount; i++) {
			resource.add(new Resource(tmp.getCosts(),tmp.getType()));
		}
	}
	
	public Resource[] getndelete(int amount){
		Resource[] resources = new Resource[amount];
		for (int i = 0; i < amount; i++) {
			resources[i] = resource.remove(i);
		}
		return resources;
	}
	
	public int getSize(){
		return resource.size();
	}
	
	public ResourceType getType(){
		return resource.get(0).getType();
	}

}
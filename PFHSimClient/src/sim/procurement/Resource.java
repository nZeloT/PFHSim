package sim.procurement;

import sim.warehouse.StorageObject;

public class Resource implements StorageObject<ResourceType> {

	private final ResourceType type;

	private final int costs;
	private final int volume;

	private double quality;

	public Resource(int costs, ResourceType type){
		this.costs = costs;
		this.type = type;
		this.volume = type.getVolume();
	}

	public int getCosts() {
		return costs;
	}

	public ResourceType getType() {
		return type;
	}

	public int getVolume() {
		return volume;
	}

	public double getQuality() {
		return quality;
	}

	public void setQuality(double quality) {
		this.quality = quality;
	}

}

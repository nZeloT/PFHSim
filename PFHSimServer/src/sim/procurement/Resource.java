package sim.procurement;

import sim.abstraction.CostFactor;

public class Resource implements CostFactor{
	
	private ResourceType type;

	private int costs;
	private int volume;
	
	private double quality;
	
	@Override
	public int getCosts() {
		return costs;
	}
	@Override
	public void setCosts(int costs) {
		
	}
	
	public ResourceType getType() {
		return type;
	}
	
	public void setType(ResourceType type) {
		this.type = type;
	}
	
	public int getVolume() {
		return volume;
	}
	
	public void setVolume(int volume) {
		this.volume = volume;
	}
	
	public double getQuality() {
		return quality;
	}
	
	public void setQuality(double quality) {
		this.quality = quality;
	}
	
}

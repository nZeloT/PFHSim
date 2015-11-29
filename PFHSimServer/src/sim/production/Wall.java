package sim.production;

import sim.abstraction.CostFactor;
import sim.warehouse.StorageObject;

public class Wall implements CostFactor, StorageObject<WallType> {
	
	private int costs;
	private WallType type;
	
	private double quality;
	
	private int volume;
	
	public Wall(WallType type, int costs) {
		this.type = type;
		volume = type.getVolume();
		this.costs = costs;
	}

	@Override
	public int getCosts() {
		return costs;
	}

	@Override
	public void setCosts(int costs) {

	}

	public WallType getType() {
		return type;
	}

	public void setType(WallType type) {
		this.type = type;
	}

	public double getQuality() {
		return quality;
	}

	public void setQuality(double quality) {
		this.quality = quality;
	}
	
	public int getVolume() {
		return volume;
	}
	
	public void setVolume(int volume) {
		this.volume = volume;
	}

}

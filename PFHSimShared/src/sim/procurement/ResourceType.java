package sim.procurement;

import sim.abstraction.StoreableType;

public enum ResourceType implements StoreableType {
	
	WOOD(300,160, "wood"),
	INSULATION(150,5, "insulation"),
	CONCRETE(300,100, "concrete"),
	BRICK(300,90, "brick"),
	WINDOW(50,80, "window"),
	ROOF_TILE(1,1, "roof tile");
	
	private int volume;
	private String name;
	private final int basePrice;
	
	private ResourceType(int v, int p, String name) {
		this.volume = v;
		this.name = name;
		this.basePrice = p;
	}
	
	public String toString() {
		return name;
	}
	
	@Override
	public int getVolume() {
		return volume;
	}
	
	public int getBasePrice() {
		return basePrice;
	}

}

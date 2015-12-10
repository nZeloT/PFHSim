package sim.procurement;

import sim.abstraction.StoreableType;

public enum ResourceType implements StoreableType {
	WOOD(5,10, "wood"),
	INSULATION(3,20, "insulation"),
	CONCRETE(3,6, "concrete"),
	BRICK(5,18, "brick"),
	WINDOW(10,30, "window"),
	ROOF_TILE(1,1, "roof tile");
	
	private int volume;
	private int price;
	private String name;
	
	private ResourceType(int v, int p, String name) {
		this.volume = v;
		this.price = p;
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
	
	@Override
	public int getVolume() {
		return volume;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	
}

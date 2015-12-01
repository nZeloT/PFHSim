package sim.procurement;

public enum ResourceType {
	WOOD(5,10),
	INSULATION(3,20),
	CONCRETE(3,6),
	BRICK(5,18),
	WINDOW(10,30),
	ROOF_TILE(1,1);
	
	private int volume;
	private int price;
	
	private ResourceType(int v, int p) {
		this.volume = v;
		this.price = p;
	}
	
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

package sim.procurement;

public enum ResourceType {
	WOOD(5),
	INSULATION(10),
	CONCRETE(2);
	
	private int volume;
	
	private ResourceType(int v) {
		this.volume = v;
	}
	
	public int getVolume() {
		return volume;
	}
}

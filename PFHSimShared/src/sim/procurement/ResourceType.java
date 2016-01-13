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
	
	public static ResourceType fromInt(int i){
		switch (i) {
		case 0: return WOOD;
		case 1: return INSULATION;
		case 2: return CONCRETE;
		case 3: return BRICK;
		case 4: return WINDOW;
		case 5: return ROOF_TILE;

		default:
			return null;
		}
	}
	
	public static int toInt(ResourceType t){
		switch (t) {
		case WOOD: 			return 0;
		case INSULATION: 	return 1;
		case CONCRETE: 		return 2;
		case BRICK: 		return 3;
		case WINDOW: 		return 4;
		case ROOF_TILE: 	return 5;
		
		default:
			return -1;
		}
	}

}

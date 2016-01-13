package sim.production;

import sim.abstraction.StoreableType;
import sim.abstraction.Tupel;
import sim.procurement.ResourceType;

public enum WallType implements StoreableType {
	
	//Create predefined types of walls and specify the 
	//needed resources (for producing one wall).
	 GENERAL(0,0),
	 LIGHT_WEIGHT_CONSTRUCTION(1, 80, 
			new Tupel<ResourceType>(ResourceType.WOOD, 5),
			new Tupel<ResourceType>(ResourceType.INSULATION, 1),
			new Tupel<ResourceType>(ResourceType.WINDOW, 1)),
	 LIGHT_WEIGHT_CONSTRUCTION_PLUS(2, 120, 
			new Tupel<ResourceType>(ResourceType.WOOD, 5),
			new Tupel<ResourceType>(ResourceType.INSULATION, 3),
			new Tupel<ResourceType>(ResourceType.WINDOW, 1)),
	 MASSIVE_LIGHT_CONSTRUCTION(4, 80,
			new Tupel<ResourceType>(ResourceType.BRICK, 5),
			new Tupel<ResourceType>(ResourceType.INSULATION, 1),
			new Tupel<ResourceType>(ResourceType.WINDOW, 1),
			new Tupel<ResourceType>(ResourceType.CONCRETE, 1)),
	 MASSIVE_PLUS_CONSTUCTION(5, 155, 
			new Tupel<ResourceType>(ResourceType.BRICK, 5),
			new Tupel<ResourceType>(ResourceType.INSULATION, 5),
			new Tupel<ResourceType>(ResourceType.WINDOW, 1),
			new Tupel<ResourceType>(ResourceType.CONCRETE, 1)),
	 PANORAMA_WALL(8, 131,
			new Tupel<ResourceType>(ResourceType.BRICK, 2),
			new Tupel<ResourceType>(ResourceType.INSULATION, 3),
			new Tupel<ResourceType>(ResourceType.WINDOW, 5),
			new Tupel<ResourceType>(ResourceType.CONCRETE, 1));

	
	
	
	private Tupel<ResourceType>[] tupel;
	private final int volume;
	private int qualityFactor;
	private final int initialQualityFactor;
	
	/**
	 * Volume = Place needed for storing one wall in the warehouse.
	 * */
	@SafeVarargs
	private WallType(int qualityFactor, int volume, Tupel<ResourceType>... tupel) {
		this.tupel = tupel;		
		this.volume = volume;
		this.qualityFactor = qualityFactor;
		this.initialQualityFactor = qualityFactor;
	}	
	
	public int getQualityFactor() {
		return qualityFactor;
	}
	
	public void setQualityFactor(int qualityFactor) {
		this.qualityFactor = qualityFactor;
	}
	
	public int getInitialQualityFactor() {
		return initialQualityFactor;
	}

	/**
	 * Get the resource-types required for producing one wall.
	 * */
	public ResourceType[] getRequiredResourceTypes() {
		ResourceType[] rt = new ResourceType[tupel.length];
		for (int i = 0; i < rt.length; i++) {
			rt[i] = tupel[i].type;
		}
		return rt;
	}

	/**
	 * Get the number of resources of a specific resource-types required for producing one wall.
	 * */
	public int[] getResourceCounts() {
		int[] rc = new int[tupel.length];
		for (int i = 0; i < rc.length; i++) {
			rc[i] = tupel[i].count;
		}
		return rc;
	}
	
	@Override
	public int getVolume() {
		return volume;
	}
	
	public static WallType fromInt(int i){
		switch (i) {
		case 0: return GENERAL;
		case 1: return LIGHT_WEIGHT_CONSTRUCTION;
		case 2: return LIGHT_WEIGHT_CONSTRUCTION_PLUS;
		case 3: return MASSIVE_LIGHT_CONSTRUCTION;
		case 4: return MASSIVE_PLUS_CONSTUCTION;
		case 5: return PANORAMA_WALL;

		default:
			return null;
		}
	}
	
	public static int toInt(WallType t){
		switch (t) {
		case GENERAL: 								return 0;
		case LIGHT_WEIGHT_CONSTRUCTION: 			return 1;
		case LIGHT_WEIGHT_CONSTRUCTION_PLUS: 		return 2;
		case MASSIVE_LIGHT_CONSTRUCTION: 			return 3;
		case MASSIVE_PLUS_CONSTUCTION: 				return 4;
		case PANORAMA_WALL: 						return 5;
		
		default:
			return -1;
		}
	}
}
package sim.production;

import sim.abstraction.StorebleType;
import sim.abstraction.Tupel;
import sim.procurement.ResourceType;

public enum WallType implements StorebleType {
	

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
	 MASSIVE_PLUS_CONSTUCTION(6, 155, 
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
	private final int qualityFactor;
	
	
	/**
	 * Volume = Place needed for storing one wall in the warehouse.
	 * */
	@SafeVarargs
	private WallType(int qualityFactor, int volume, Tupel<ResourceType>... tupel) {
		this.tupel = tupel;		
		this.volume = volume;
		this.qualityFactor = qualityFactor;
	}	
	
	public int getQualityFactor() {
		return qualityFactor;
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
	
	
}

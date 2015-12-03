package sim.production;

import sim.procurement.ResourceType;

public enum WallType {
	

	//Create predefined types of walls and specify the 
	//needed resources (for producing one wall).
	 GENERAL(0),
	 LIGHT_WEIGHT_CONSTRUCTION(50, 
			new WallType.Tupel(ResourceType.WOOD, 5),
			new WallType.Tupel(ResourceType.INSULATION, 1),
			new WallType.Tupel(ResourceType.WINDOW, 1)),
	 LIGHT_WEIGHT_CONSTRUCTION_PLUS(50, 
			new WallType.Tupel(ResourceType.WOOD, 5),
			new WallType.Tupel(ResourceType.INSULATION, 3),
			new WallType.Tupel(ResourceType.WINDOW, 1)),
	 MASSIVE_LIGHT_CONSTRUCTION(50,
			new WallType.Tupel(ResourceType.BRICK, 5),
			new WallType.Tupel(ResourceType.INSULATION, 1),
			new WallType.Tupel(ResourceType.WINDOW, 1),
			new WallType.Tupel(ResourceType.CONCRETE, 1)),
	 MASSIVE_PLUS_CONSTUCTION(50, 
			new WallType.Tupel(ResourceType.BRICK, 5),
			new WallType.Tupel(ResourceType.INSULATION, 5),
			new WallType.Tupel(ResourceType.WINDOW, 1),
			new WallType.Tupel(ResourceType.CONCRETE, 1)),
	 PANORAMA_WALL(50,
			new WallType.Tupel(ResourceType.BRICK, 2),
			new WallType.Tupel(ResourceType.INSULATION, 3),
			new WallType.Tupel(ResourceType.WINDOW, 5),
			new WallType.Tupel(ResourceType.CONCRETE, 1));

	
	/**
	 * One Tupel represents a kind of an attribute of the class "WallType"
	 * containing a type of resource needed for producing a specific wall
	 * and the number of resources required.
	 * */
	static class Tupel {
		public ResourceType rt;
		public int count;
		
		Tupel(ResourceType rt, int count) {
			this.rt = rt;
			this.count = count;
		}
		
	}
	
	private Tupel[] tupel;
	private final int volume;
	
	
	/**
	 * Volume = Place needed for storing one wall in the warehouse.
	 * */
	private WallType(int volume, Tupel... tupel) {
		this.tupel = tupel;		
		this.volume = volume;
	}	
	
	/**
	 * Get the resource-types required for producing one wall.
	 * */
	public ResourceType[] getRequiredResourceTypes() {
		ResourceType[] rt = new ResourceType[tupel.length];
		for (int i = 0; i < rt.length; i++) {
			rt[i] = tupel[i].rt;
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
	
	public int getVolume() {
		return volume;
	}
	
	
}

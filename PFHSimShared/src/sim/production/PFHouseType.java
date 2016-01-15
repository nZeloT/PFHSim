package sim.production;

import sim.abstraction.GeneralType;
import sim.abstraction.Tupel;
import sim.procurement.ResourceType;

/**
 * House-types to be built; the player is able to customize houses himself
 * by specifying the GENERAL-walls.
 * @author Alexander
 *
 */
public enum PFHouseType implements GeneralType {
	
	/*
	 * Need to create an array of objects because the 
	 * JAVA-Compiler is not able to instantiate an array
	 * of a generic class. -> Raw-array is casted to
	 * the respective type in the constructor below.
	 */
	BUNGALOW(new Tupel[]{
				new Tupel<WallType>(WallType.GENERAL, 5)
			},
			new Tupel[] {
				new Tupel<ResourceType>(ResourceType.ROOF_TILE, 25),
				new Tupel<ResourceType>(ResourceType.WOOD, 6)
			}, 3, 1, 0, 0, 10, 1),
	BLOCK_HOUSE(new Tupel[]{
			new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 3), 
			new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS, 3)
		},
		new Tupel[] {
			new Tupel<ResourceType>(ResourceType.ROOF_TILE, 50),
			new Tupel<ResourceType>(ResourceType.WOOD, 12)
		}, 5, 1, 1, 10000, 20, 5),
	EFFICIENCY_HOUSE(new Tupel[]{
			new Tupel<WallType>(WallType.GENERAL, 6)
		},
		new Tupel[] {
			new Tupel<ResourceType>(ResourceType.CONCRETE, 4),
		}, 5, 1, 2, 20000, 30, 10),
	MULTI_FAMILY_HOUSE(new Tupel[]{
			new Tupel<WallType>(WallType.GENERAL, 12)
		},
		new Tupel[] {
			new Tupel<ResourceType>(ResourceType.ROOF_TILE, 80),
			new Tupel<ResourceType>(ResourceType.WOOD, 19)
		}, 9, 2, 3, 30000, 50, 10),
	COMFORT_HOUSE(new Tupel[]{
			new Tupel<WallType>(WallType.GENERAL, 6),
			new Tupel<WallType>(WallType.PANORAMA_WALL, 1)
		},
		new Tupel[] {
			new Tupel<ResourceType>(ResourceType.ROOF_TILE, 80),
			new Tupel<ResourceType>(ResourceType.WOOD, 19)
		}, 8, 2, 4, 40000, 60, 15),
	CITY_VILLA(new Tupel[]{
			new Tupel<WallType>(WallType.PANORAMA_WALL, 2),
			new Tupel<WallType>(WallType.GENERAL, 6)
		},
		new Tupel[] {
			new Tupel<ResourceType>(ResourceType.ROOF_TILE, 100),
			new Tupel<ResourceType>(ResourceType.WOOD, 21)
		}, 10, 2, 5, 50000, 80, 20),
	TRENDHOUSE(new Tupel[]{
				new Tupel<WallType>(WallType.PANORAMA_WALL, 3),
				new Tupel<WallType>(WallType.GENERAL, 5)
			},
			new Tupel[] {
				new Tupel<ResourceType>(ResourceType.CONCRETE, 10)
			}, 9, 2, 6, 60000, 85,15);
	
		
	private Tupel<WallType>[] walltupel;
	private Tupel<ResourceType>[] resourcetupel;
	
	private int employeecount; 
	private int constructionDuration;
	
	private int researchDuration;
	private int researchCosts;
	
	private final double scoreFactor;
	private final int baseQuality;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private PFHouseType(Tupel[] walltupel, Tupel[] resourcetupel, int employeecount, int constructionDuration,
			int researchDuration, int researchCosts, double scoreFactor, int baseQuality) {
		
		this.walltupel = walltupel;
		this.resourcetupel= resourcetupel;
		this.employeecount = employeecount;
		this.constructionDuration = constructionDuration;
		
		this.researchCosts = researchCosts;
		this.researchDuration = researchDuration;
		
		this.scoreFactor = scoreFactor;
		this.baseQuality = baseQuality;
	}
	
	public int getBaseQuality() {
		return baseQuality;
	}

	public int getNoOfWalls(WallType t) {
		for (Tupel<WallType> tupel : walltupel) {
			if (tupel.type == t) 
				return tupel.count; 
		}
		return 0;
	}
	
	public WallType[] getRequiredWallTypes() {
		WallType[] wt = new WallType[walltupel.length];
		for (int i = 0; i < walltupel.length; i++) {
			wt[i] = walltupel[i].type;
		} 
		return wt;
	}

	public int[] getWallCounts() {
		int[] wc = new int[walltupel.length];
		for (int i = 0; i < walltupel.length; i++) {
			wc[i] = walltupel[i].count;
		}
		return wc;
	}
	
	public ResourceType[] getRequiredResourceTypes() {
		ResourceType[] rt = new ResourceType[resourcetupel.length];
		for (int i = 0; i < resourcetupel.length; i++) {
			rt[i] = resourcetupel[i].type;
		}
		return rt;
	}

	public int[] getResourceCounts() {
		int[] rc = new int[resourcetupel.length];
		for (int i = 0; i < resourcetupel.length; i++) {
			rc[i] = resourcetupel[i].count;
		}
		return rc;
	}

	public int getEmployeeCount() {
		return employeecount;
	}
	
	public int getConstructionDuration() {
		return constructionDuration;
	}
	
	public int getResearchCosts() {
		return researchCosts;
	}
	
	public int getResearchDuration() {
		return researchDuration;
	}
	
	public double getScoreFactor() {
		return scoreFactor;
	}
	
	public static PFHouseType fromInt(int i){
		switch (i) {
		case 0: return BUNGALOW;
		case 1: return BLOCK_HOUSE;
		case 2: return EFFICIENCY_HOUSE;
		case 3: return MULTI_FAMILY_HOUSE;
		case 4: return COMFORT_HOUSE;
		case 5: return CITY_VILLA;
		case 6: return TRENDHOUSE;

		default:
			return null;
		}
	}
	
	public static int toInt(PFHouseType t){
		switch (t) {
		case BUNGALOW: 				return 0;
		case BLOCK_HOUSE: 			return 1;
		case EFFICIENCY_HOUSE: 		return 2;
		case MULTI_FAMILY_HOUSE: 	return 3;
		case COMFORT_HOUSE: 		return 4;
		case CITY_VILLA: 			return 5;
		case TRENDHOUSE: 			return 6;
		
		default:
			return -1;
		}
	}

}

package sim.production;

import sim.abstraction.GeneralType;
import sim.abstraction.Tupel;
import sim.hr.EmployeeType;
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
	 * of a generic class. -> Object-array is casted to
	 * the respective type in the constructor below.
	 */
	BUNGALOW(new Tupel[]{
				new Tupel<WallType>(WallType.GENERAL, 5)
			},
			new Tupel[] {
				new Tupel<ResourceType>(ResourceType.ROOF_TILE, 25),
				new Tupel<ResourceType>(ResourceType.WOOD, 6)
			},
			new Tupel[] {
				new Tupel<EmployeeType>(EmployeeType.ASSEMBLER, 3)
			}, 1),
	BLOCK_HOUSE(new Tupel[]{
			new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 3), 
			new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS, 3)
		},
		new Tupel[] {
			new Tupel<ResourceType>(ResourceType.ROOF_TILE, 50),
			new Tupel<ResourceType>(ResourceType.WOOD, 12)
		},
		new Tupel[] {
			new Tupel<EmployeeType>(EmployeeType.ASSEMBLER, 5)
		}, 1),
	EFFICIENCY_HOUSE(new Tupel[]{
			new Tupel<WallType>(WallType.GENERAL, 6)
		},
		new Tupel[] {
			new Tupel<ResourceType>(ResourceType.CONCRETE, 4),
		},
		new Tupel[] {
			new Tupel<EmployeeType>(EmployeeType.ASSEMBLER, 5)
		}, 1),
	MULTI_FAMILY_HOUSE(new Tupel[]{
			new Tupel<WallType>(WallType.GENERAL, 12)
		},
		new Tupel[] {
			new Tupel<ResourceType>(ResourceType.ROOF_TILE, 80),
			new Tupel<ResourceType>(ResourceType.WOOD, 19)
		},
		new Tupel[] {
			new Tupel<EmployeeType>(EmployeeType.ASSEMBLER, 9)
		}, 2),
	COMFORT_HOUSE(new Tupel[]{
			new Tupel<WallType>(WallType.GENERAL, 6),
			new Tupel<WallType>(WallType.PANORAMA_WALL, 1)
		},
		new Tupel[] {
			new Tupel<ResourceType>(ResourceType.ROOF_TILE, 80),
			new Tupel<ResourceType>(ResourceType.WOOD, 19)
		},
		new Tupel[] {
			new Tupel<EmployeeType>(EmployeeType.ASSEMBLER, 8)
		}, 2),
	CITY_VILLA(new Tupel[]{
			new Tupel<WallType>(WallType.PANORAMA_WALL, 2),
			new Tupel<WallType>(WallType.GENERAL, 6)
		},
		new Tupel[] {
			new Tupel<ResourceType>(ResourceType.ROOF_TILE, 100),
			new Tupel<ResourceType>(ResourceType.WOOD, 21)
		},
		new Tupel[] {
			new Tupel<EmployeeType>(EmployeeType.ASSEMBLER, 10)
		}, 2),
	TRENDHOUSE(new Tupel[]{
				new Tupel<WallType>(WallType.PANORAMA_WALL, 3),
				new Tupel<WallType>(WallType.GENERAL, 5)
			},
			new Tupel[] {
				new Tupel<ResourceType>(ResourceType.CONCRETE, 10)
			},
			new Tupel[] {
				new Tupel<EmployeeType>(EmployeeType.ASSEMBLER, 9)
			}, 2);
	
		
	private Tupel<WallType>[] walltupel;
	private Tupel<ResourceType>[] resourcetupel;
	private Tupel<EmployeeType>[] employeetupel; 
	private int constructionDuration;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private PFHouseType(Tupel[] walltupel, Tupel[] resourcetupel, Tupel[] employeetupel, int constructionDuration) {
		
		this.walltupel = walltupel;
		this.resourcetupel= resourcetupel;
		this.employeetupel = employeetupel;
		this.constructionDuration = constructionDuration;
		
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
	
	public EmployeeType[] getRequiredEmployeeTypes() {
		EmployeeType[] et = new EmployeeType[employeetupel.length];
		for (int i = 0; i < employeetupel.length; i++) {
			et[i] = employeetupel[i].type;
		}
		return et;
	}

	public int[] getEmployeeCounts() {
		int[] ec = new int[employeetupel.length];
		for (int i = 0; i < employeetupel.length; i++) {
			ec[i] = employeetupel[i].count;
		}
		return ec;
	}
	
	public int getConstructionDuration() {
		return constructionDuration;
	}
	
}

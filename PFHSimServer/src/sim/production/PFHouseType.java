package sim.production;

import sim.hr.EmployeeType;
import sim.procurement.ResourceType;

public enum PFHouseType {
	BUNGALOW(new PFHouseType.WallTupel[]{
			new WallTupel(WallType.GENERAL, 5)
			},
			new PFHouseType.ResourceTupel[] {
				new ResourceTupel(ResourceType.ROOF_TILE, 25),
				new ResourceTupel(ResourceType.WOOD, 6)
			},
			new PFHouseType.EmployeeTupel[] {
				new EmployeeTupel(EmployeeType.ASSEMBLER, 3)
			}, 1),
	BLOCK_HOUSE(new PFHouseType.WallTupel[]{
			new WallTupel(WallType.LIGHT_WEIGHT_CONSTRUCTION, 3), 
			new WallTupel(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS, 3)
		},
		new PFHouseType.ResourceTupel[] {
			new ResourceTupel(ResourceType.ROOF_TILE, 50),
			new ResourceTupel(ResourceType.WOOD, 12)
		},
		new PFHouseType.EmployeeTupel[] {
			new EmployeeTupel(EmployeeType.ASSEMBLER, 5)
		}, 1),
	EFFICIENCY_HOUSE(new PFHouseType.WallTupel[]{
			new WallTupel(WallType.GENERAL, 6)
		},
		new PFHouseType.ResourceTupel[] {
			new ResourceTupel(ResourceType.CONCRETE, 4),
		},
		new PFHouseType.EmployeeTupel[] {
			new EmployeeTupel(EmployeeType.ASSEMBLER, 5)
		}, 1),
	MULTI_FAMILY_HOUSE(new PFHouseType.WallTupel[]{
			new WallTupel(WallType.GENERAL, 12)
		},
		new PFHouseType.ResourceTupel[] {
			new ResourceTupel(ResourceType.ROOF_TILE, 80),
			new ResourceTupel(ResourceType.WOOD, 19)
		},
		new PFHouseType.EmployeeTupel[] {
			new EmployeeTupel(EmployeeType.ASSEMBLER, 9)
		}, 2),
	COMFORT_HOUSE(new PFHouseType.WallTupel[]{
			new WallTupel(WallType.GENERAL, 6),
			new WallTupel(WallType.PANORAMA_WALL, 1)
		},
		new PFHouseType.ResourceTupel[] {
			new ResourceTupel(ResourceType.ROOF_TILE, 80),
			new ResourceTupel(ResourceType.WOOD, 19)
		},
		new PFHouseType.EmployeeTupel[] {
			new EmployeeTupel(EmployeeType.ASSEMBLER, 8)
		}, 2),
	CITY_VILLA(new PFHouseType.WallTupel[]{
			new WallTupel(WallType.PANORAMA_WALL, 2),
			new WallTupel(WallType.GENERAL, 6)
		},
		new PFHouseType.ResourceTupel[] {
			new ResourceTupel(ResourceType.ROOF_TILE, 100),
			new ResourceTupel(ResourceType.WOOD, 21)
		},
		new PFHouseType.EmployeeTupel[] {
			new EmployeeTupel(EmployeeType.ASSEMBLER, 10)
		}, 2),
	TRENDHOUSE(new PFHouseType.WallTupel[]{
				new WallTupel(WallType.PANORAMA_WALL, 3),
				new WallTupel(WallType.GENERAL, 5)
			},
			new PFHouseType.ResourceTupel[] {
				new ResourceTupel(ResourceType.CONCRETE, 10)
			},
			new PFHouseType.EmployeeTupel[] {
				new EmployeeTupel(EmployeeType.ASSEMBLER, 9)
			}, 2);
	
	
	
	static class WallTupel {
		public WallType walltype;
		public int maxcount;
		public WallTupel(WallType walltype, int maxcount) {
			this.walltype = walltype;
			this.maxcount = maxcount;
		}
	}
	static class ResourceTupel {
		public ResourceType resourcetype;
		public int count;
		public ResourceTupel(ResourceType resourcetype, int count) {
			this.resourcetype = resourcetype;
			this.count = count;
		}
	}
	static class EmployeeTupel {
		public EmployeeType employeetype;
		public int count;
		public EmployeeTupel(EmployeeType employeetype, int count) {
			this.employeetype = employeetype;
			this.count = count;
		}
	}
	
	
	private WallTupel[] walltupel;
	private ResourceTupel[] resourcetupel;
	private EmployeeTupel[] employeetupel;
	private int constructionDuration;

	private PFHouseType(WallTupel[] walltupel, ResourceTupel[] resourcetupel, EmployeeTupel[] employeetupel, int constructionDuration) {
		
		this.walltupel = walltupel;
		this.resourcetupel = resourcetupel;
		this.employeetupel = employeetupel;
		this.constructionDuration = constructionDuration;
		
	}
	
	
	
	public WallType[] getRequiredWallTypes() {
		WallType[] wt = new WallType[walltupel.length];
		for (int i = 0; i < walltupel.length; i++) {
			wt[i] = walltupel[i].walltype;
		} 
		return wt;
	}

	public int[] getWallCounts() {
		int[] wc = new int[walltupel.length];
		for (int i = 0; i < wc.length; i++) {
			wc[i] = walltupel[i].maxcount;
		}
		return wc;
	}
	
	public ResourceType[] getRequiredResourceTypes() {
		ResourceType[] rt = new ResourceType[resourcetupel.length];
		for (int i = 0; i < resourcetupel.length; i++) {
			rt[i] = resourcetupel[i].resourcetype;
		}
		return rt;
	}

	public int[] getResourceCounts() {
		int[] rc = new int[resourcetupel.length];
		for (int i = 0; i < rc.length; i++) {
			rc[i] = resourcetupel[i].count;
		}
		return rc;
	}
	
	public EmployeeType[] getRequiredEmployeeTypes() {
		EmployeeType[] et = new EmployeeType[employeetupel.length];
		for (int i = 0; i < employeetupel.length; i++) {
			et[i] = employeetupel[i].employeetype;
		}
		return et;
	}

	public int[] getEmployeeCounts() {
		int[] ec = new int[employeetupel.length];
		for (int i = 0; i < ec.length; i++) {
			ec[i] = employeetupel[i].count;
		}
		return ec;
	}
	
	public int getConstructionDuration() {
		return constructionDuration;
	}
	
	
}

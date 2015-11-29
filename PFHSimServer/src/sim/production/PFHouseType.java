package sim.production;

import sim.hr.EmployeeType;
import sim.procurement.ResourceType;

public enum PFHouseType {
	NORMAL(new PFHouseType.WallTupel[]{
				new WallTupel(WallType.NORMAL, 20)
			},
			new PFHouseType.ResourceTupel[] {
				new ResourceTupel(ResourceType.CONCRETE, 10)
			},
			new PFHouseType.EmployeeTupel[] {
				new EmployeeTupel(EmployeeType.ASSEMBLER, 10)
			}, 1),
	PENTHOUSE(new PFHouseType.WallTupel[]{
				new WallTupel(WallType.NORMAL, 15),
				new WallTupel(WallType.ECO, 25)
			}, 
			new PFHouseType.ResourceTupel[] {
				new ResourceTupel(ResourceType.CONCRETE, 15)
			},
			new PFHouseType.EmployeeTupel[] {
					new EmployeeTupel(EmployeeType.ASSEMBLER, 10)
			}, 2);
	
	
	
	static class WallTupel {
		public WallType walltype;
		public int count;
		public WallTupel(WallType walltype, int count) {
			this.walltype = walltype;
			this.count = count;
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
	private int ConstructionDuration;

	private PFHouseType(WallTupel[] walltupel, ResourceTupel[] resourcetupel, EmployeeTupel[] employeetupel, int ConstructionDuration) {
		
		this.walltupel = walltupel;
		this.resourcetupel = resourcetupel;
		this.employeetupel = employeetupel;
		this.ConstructionDuration = ConstructionDuration;
		
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
			wc[i] = walltupel[i].count;
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
		return ConstructionDuration;
	}
	
	
}

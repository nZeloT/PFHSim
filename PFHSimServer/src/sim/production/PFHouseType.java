package sim.production;

import sim.procurement.ResourceType;

public enum PFHouseType {
	NORMAL(new PFHouseType.WallTupel[]{
				new WallTupel(WallType.NORMAL, 20)
			},
			new PFHouseType.ResourceTupel[] {
				new ResourceTupel(ResourceType.CONCRETE, 10)
			}),
	PENTHOUSE(new PFHouseType.WallTupel[]{
				new WallTupel(WallType.NORMAL, 15),
				new WallTupel(WallType.ECO, 25)
			}, 
			new PFHouseType.ResourceTupel[] {
				new ResourceTupel(ResourceType.CONCRETE, 15)
			});
	
	
	
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
	
	
	
	private WallTupel[] walltupel;
	private ResourceTupel[] resourcetupel;

	private PFHouseType(WallTupel[] walltupel, ResourceTupel[] resourcetype) {
		
		this.walltupel = walltupel;
		this.resourcetupel = resourcetupel;
		
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
	
	
}

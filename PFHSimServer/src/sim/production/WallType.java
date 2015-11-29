package sim.production;

import sim.procurement.ResourceType;

public enum WallType {
	ECO		(5, new WallType.Tupel(ResourceType.WOOD, 5)),
	NORMAL	(6, new WallType.Tupel(ResourceType.WOOD, 4));
	
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
	
	private WallType(int volume, Tupel... tupel) {
		this.tupel = tupel;		
		this.volume = volume;
	}	
	
	public ResourceType[] getRequiredResourceTypes() {
		ResourceType[] rt = new ResourceType[tupel.length];
		for (int i = 0; i < rt.length; i++) {
			rt[i] = tupel[i].rt;
		}
		return rt;
	}
	
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

package sim.production;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sim.procurement.ResourceType;

public enum WallType {
	ECO(new WallType.Tupel(ResourceType.WOOD, 5));
	
	static class Tupel {
		public ResourceType rt;
		public int count;
		
		Tupel(ResourceType rt, int count) {
			this.rt = rt;
			this.count = count;
		}
		
	}
	
	private Tupel[] tupel;
	
	
	private WallType(Tupel... tupel) {
		this.tupel = tupel;		
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
	
	
}

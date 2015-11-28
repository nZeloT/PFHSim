package sim.production;

import sim.abstraction.CostFactor;

public class Wall implements CostFactor {
	
	private int costs;
	private WallType type;
	
	private double quality;

	@Override
	public int getCosts() {
		return costs;
	}

	@Override
	public void setCosts(int costs) {

	}

}

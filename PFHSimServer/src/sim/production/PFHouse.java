package sim.production;

import sim.abstraction.CostFactor;

public class PFHouse implements CostFactor {
	
	private int price;
	
	private int costs;
	private double quality;
	
	private PFHouseType type;
	
	private int buildDuration;

	@Override
	public int getCosts() {
		return costs;
	}

	@Override
	public void setCosts(int costs) {

	}

}

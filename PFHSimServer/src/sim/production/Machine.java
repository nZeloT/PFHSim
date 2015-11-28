package sim.production;

import sim.abstraction.CostFactor;

public class Machine implements CostFactor {
	
	private int costs;
	private double performance;
	
	//production quality
	private double quality;

	@Override
	public int getCosts() {
		return costs;
	}

	@Override
	public void setCosts(int costs) {

	}

}

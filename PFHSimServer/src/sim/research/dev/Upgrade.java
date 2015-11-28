package sim.research.dev;

import sim.abstraction.CostFactor;

public class Upgrade implements CostFactor{
	
	private int costs;
	
	private int duration;

	@Override
	public int getCosts() {
		return costs;
	}

	@Override
	public void setCosts(int costs) {
		
	}

}

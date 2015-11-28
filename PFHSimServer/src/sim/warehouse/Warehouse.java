package sim.warehouse;

import sim.abstraction.CostFactor;

public class Warehouse implements CostFactor{

	private int capacity;
	private int costs;
	
	@Override
	public int getCosts() {
		return costs;
	}
	@Override
	public void setCosts(int costs) {
		
	}
	
}

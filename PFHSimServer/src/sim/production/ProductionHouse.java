package sim.production;

import java.util.ArrayList;

import sim.abstraction.CostFactor;

public class ProductionHouse implements CostFactor {
	
	private ArrayList<Machine> machines;
	
	private int costs;

	
	@Override
	public int getCosts() {
		return costs;
	}

	@Override
	public void setCosts(int costs) {
		
	}

}

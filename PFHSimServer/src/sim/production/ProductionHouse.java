package sim.production;

import java.util.ArrayList;

import sim.abstraction.CostFactor;

public class ProductionHouse implements CostFactor {
	
	private ArrayList<TestMachine> machines;
	
	private int costs;

	
	@Override
	public int getCosts() {
		return costs;
	}

}
	
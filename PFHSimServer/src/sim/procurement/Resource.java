package sim.procurement;

import sim.abstraction.CostFactor;

public class Resource implements CostFactor{
	
	private ResourceType type;

	private int costs;
	private int volume;
	
	private double quality;
	
	@Override
	public int getCosts() {
		return costs;
	}
	@Override
	public void setCosts(int costs) {
		
	}
	
}

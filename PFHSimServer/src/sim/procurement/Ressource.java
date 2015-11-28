package sim.procurement;

import sim.abstraction.CostFactor;

public class Ressource implements CostFactor{
	
	private RessourceType type;

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

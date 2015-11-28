package sim.hr;

import sim.abstraction.CostFactor;

public class Employee implements CostFactor{
	
	private int costs;
	
	private EmployeeType type;
	private double skill;
	
	private boolean free;
	
	public Employee(EmployeeType type) {
		free = true;
		skill= 1;
		this.type = type;
		costs = 200;
		
	}
	
	@Override
	public int getCosts() {
		return costs;
	}
	@Override
	public void setCosts(int costs) {
		
	}
}

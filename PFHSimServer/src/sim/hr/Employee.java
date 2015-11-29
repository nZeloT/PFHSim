package sim.hr;

import sim.abstraction.CostFactor;

public class Employee implements CostFactor{
	
	private int costs;
	
	private EmployeeType type;
	private double skill;
	
	private boolean free;
	
	public Employee(EmployeeType type) {
		this.free  = true;
		this.skill = 1;
		this.type  = type;
		this.costs = 800;
	}
	
	@Override
	public int getCosts() {
		return costs;
	}
	@Override
	public void setCosts(int costs) {
		this.costs = costs;
	}
	
	public EmployeeType getType() {
		return type;
	}
	
	public boolean isFree() {
		return free;
	}
	
	public void setFree(boolean free) {
		this.free = free;
	}
	
	public double getSkill() {
		return skill;
	}
}

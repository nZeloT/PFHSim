package sim.hr;

import sim.abstraction.CostFactor;

public class Employee implements CostFactor{
	
	private int costs;
	
	private EmployeeType type;
	private double skill;
	
	private Workplace work;
	
	//TODO: should this be a constructor with default visibility? bescuase hiring is done through HR
	public Employee(EmployeeType type) {
		this.skill = 1;
		this.type  = type;
		this.costs = 800;
	}
	
	public void increaseSkill(double factor){
		this.skill += factor;
	}
	
	public void increaseCosts(int costs){
		this.costs += costs;
	}
	
	public boolean assignWorkplace(Workplace w){
		//check whether the employee is assigned to a workplace if so unassign him there first
		if(isAssigned() && work.unassignEmployee(this)
				|| !isAssigned()){
			Workplace old = work;
			work = w;
			
			//could not assign the employee to the new workplace; leave him at his current
			if(work.assignEmployee(this)){
				work = old;
				work.assignEmployee(this);
			}
			return true;
		}
		
		return false;
	}
	
	public boolean unassignWorkplace(){
		if(isAssigned() && work.unassignEmployee(this)){
			work = null;
			return true;
		}
		
		return false;
	}
	
	@Override
	public int getCosts() {
		return costs;
	}

	public void setCosts(int costs) {
		this.costs = costs;
	}
	
	public EmployeeType getType() {
		return type;
	}
	
	public boolean isAssigned() {
		return work != null;
	}
	
	public double getSkill() {
		return skill;
	}
	
	public Workplace getWork() {
		return work;
	}
}

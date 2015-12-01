package sim.hr;

import sim.abstraction.CostFactor;

public class Employee implements CostFactor{
	
	private EmployeeType type;
	
	private int upgradeCount;
	
	private Workplace work;
	
	//TODO: should this be a constructor with default visibility? because hiring is done through HR
	public Employee(EmployeeType type) {
		this.type  = type;
		this.upgradeCount = 0;
	}
	
	public boolean assignWorkplace(Workplace w){
		//check whether the employee is assigned to a workplace if so unassign him there first
		if(isAssigned() && work.unassignEmployee(this)
				|| !isAssigned()){
			Workplace old = work;
			work = w;
			
			//could not assign the employee to the new workplace; leave him at his current
			if(!work.assignEmployee(this)){
				work = old;
				if(work != null)
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
		
		return !isAssigned();
	}
	
	public boolean canDoTraining(){
		
		if(upgradeCount < type.getPossibleUpgrades()){
			if(!isAssigned())
				return true;
			else{
				Workplace w = work;
				if(unassignWorkplace() && assignWorkplace(w))
					return true;
			}
		}
		
		return false;
	}
	
	@Override
	public int getCosts() {
		return type.getBaseCost() + upgradeCount * type.getUpgradeCostInc();
	}
	
	public void visitedTraining(){
		if(upgradeCount < type.getPossibleUpgrades())
			upgradeCount++;
	}
	
	public int getVisitedTrainings() {
		return upgradeCount;
	}

	public EmployeeType getType() {
		return type;
	}
	
	public boolean isAssigned() {
		return work != null;
	}
	
	public int getSkill() {
		return 1 + upgradeCount * type.getUpgradeSkillInc();
	}
	
	public Workplace getWork() {
		return work;
	}
}

package sim.hr;

import sim.abstraction.CostFactor;

/**
 * This represents one employee;
 * An Employee can be assigned(unassigned to an department; be trained and produces costs
 * 
 * @author Leon
 */
public class Employee implements CostFactor{
	private static int count = 0;
	
	private final int id;
	
	private EmployeeType type;
	
	private int upgradeCount;
	
	private Department work;
	
	private boolean onTraining;
	
	Employee(EmployeeType type) {
		this.id = count++;
		this.type  = type;
		this.upgradeCount = 0;
	}
	
	public boolean assignWorkplace(Department w){
		//check whether the employee is assigned to a workplace if so unassign him there first
		if(isAssigned() && work.unassignEmployee(this, true)
				|| !isAssigned()){
			Department old = work;
			work = w;
			
			//could not assign the employee to the new workplace; leave him at his current
			if(work != null && !work.assignEmployee(this, true)){
				work = old;
				if(work != null)
					work.assignEmployee(this, true);
			}
			return true;
		}
		
		return false;
	}
	
	public boolean unassignWorkplace(){
		if(isAssigned() && work.unassignEmployee(this, true)){
			work = null;
			return true;
		}
		
		return !isAssigned();
	}
	
	public boolean canDoTraining(){
		
		if(upgradeCount < type.getPossibleUpgrades() && !isOnTraining()){
			if(!isAssigned())
				return true;
			else{
				Department w = work;
				if(unassignWorkplace() && assignWorkplace(w))
					return true;
			}
		}
		
		return false;
	}
	
	public boolean canBeUnassigned(){
		Department w = work;
		return unassignWorkplace() && assignWorkplace(w);
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
	
	public boolean isOnTraining(){
		return onTraining;
	}
	
	public int getSkill() {
		return (type == EmployeeType.ARCHITECT ? 0 : 1) + upgradeCount * type.getUpgradeSkillInc();
	}
	
	public Department getWork() {
		return work;
	}
	
	public void setOnTraining(boolean training){
		onTraining = training;
	}
	
	public String getName(){
		return type + " #" + id;
	}
}

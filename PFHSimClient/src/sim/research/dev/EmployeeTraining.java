package sim.research.dev;

import sim.hr.Department;
import sim.hr.Employee;
import sim.hr.EmployeeType;

public class EmployeeTraining extends Upgrade<Employee> {
	
	private Employee employee;
	private UpgradeDep upgradeDep;
	
	private Department empWork;
	private boolean running;
	
	EmployeeTraining(Employee e) {
		super(e.getType().getUpgradeDuration(), e.getType().getUpgradeCosts());
		this.upgradeDep = new UpgradeDep(e.getType());
		this.employee = e;
	}

	@Override
	protected void setup() {
		empWork = employee.getWork();
		running = employee.assignWorkplace(upgradeDep);
		employee.setOnTraining(true);
	}
	
	@Override
	public void simRound() {
		if(running)
			super.simRound();
		else
			setup();
	}

	@Override
	protected void finish() {
		running = false;
		employee.assignWorkplace(empWork);
		employee.visitedTraining();
		employee.setOnTraining(false);
	}
	
	@Override Employee getUpgradeObject() {
		return employee;
	}
	
	
	private class UpgradeDep extends Department {
		
		public UpgradeDep(EmployeeType t) {
			super(t);
		}
		
		@Override
		protected boolean assignEmployee(Employee e, boolean calledFromEmployeeObject) {
			return !running && super.assignEmployee(e, calledFromEmployeeObject);
		}
		
		@Override
		protected boolean unassignEmployee(Employee e, boolean calledFromEmployeeObject) {
			return !running && super.unassignEmployee(e, calledFromEmployeeObject);
		}
		
	}
	
}

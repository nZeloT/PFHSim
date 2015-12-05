package sim.research.dev;

import sim.hr.Department;
import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.production.PFHouseType;

public class ResearchProject extends Upgrade<Employee>{

	private Employee arch;
	private UpgradeDep dep;
	
	ResearchProject(PFHouseType type, Employee architect) {
		super(type.getResearchDuration(), type.getResearchCosts());
		this.reasearchType = type;
		this.arch = architect;
		this.dep = new UpgradeDep(EmployeeType.ARCHITECT);
	}

	private PFHouseType reasearchType;
	private boolean running;

	@Override
	protected void setup() {
		arch.assignWorkplace(dep);
		running = true;
	}

	@Override
	protected void finish() {
		running = false;
		arch.unassignWorkplace();
	}
	
	public PFHouseType getReasearchType() {
		return reasearchType;
	}
	
	@Override Employee getUpgradeObject() {
		return arch;
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

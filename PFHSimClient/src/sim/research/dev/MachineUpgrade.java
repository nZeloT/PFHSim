package sim.research.dev;

import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.hr.HR;
import sim.production.Machine;

public class MachineUpgrade extends Upgrade<Machine> {

	private Machine machine;
	private HR empMgr;
	
	private boolean unassignedAll;

	MachineUpgrade(Machine m, HR empMgr) {
		super(m.getType().getUpgradeDuration(), m.getType().getUpgradeCosts());

		this.machine = m;
		this.empMgr = empMgr;
	}

	@Override
	protected void setup() {
		//1. set machine to inupgrade
		machine.setInUpgrade(true);

		//2. unassign employees
		unassignedAll = machine.unassignAllEmployees();
	}
	
	@Override
	void simRound() {
		if(unassignedAll)
			super.simRound();
		else
			setup();
	}

	@Override
	protected void finish() {
		//1. set machine to not in upgrade
		machine.upgrade();
		machine.setInUpgrade(false);

		//2. try to reassign employees
		Employee[] emps = empMgr.getUnassignedEmployees(EmployeeType.PRODUCTION, machine.getRequiredEmps());
		if(emps != null){
			for (Employee e : emps) {
				e.assignWorkplace(machine);
			}
		}
	}
	
	@Override Machine getUpgradeObject() {
		return machine;
	}

}

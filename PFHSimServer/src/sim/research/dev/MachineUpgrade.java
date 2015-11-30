package sim.research.dev;

import java.util.ArrayList;

import sim.hr.Employee;
import sim.hr.HR;
import sim.hr.EmployeeType;
import sim.production.Machine;

public class MachineUpgrade extends Upgrade {

	private double perfInc;
	private int costInc;
	private double qualityInc;
	private int requiredEmpInc;

	private Machine machine;
	private HR empMgr;

	public MachineUpgrade(Machine m, HR empMgr, int duration, int costs, double perfInc, double qualityInc, int costInc, int requiredEmpInc) {
		super(duration, costs);

		this.machine = m;
		this.empMgr = empMgr;

		this.perfInc = perfInc;
		this.qualityInc = qualityInc;
		this.costInc = costInc;
		this.requiredEmpInc = requiredEmpInc;
	}

	@Override
	public void start() {
		//1. set machine to inupgrade
		machine.setInUpgrade(true);

		//2. unassign employees
		ArrayList<Employee> emps = machine.getAssignedEmployees();
		for (Employee e : emps) {
			e.unassignWorkplace();
		}
	}

	@Override
	protected void finish() {
		//1. set machine to not in upgrade
		machine.deltaCosts(costInc);
		machine.deltaPerformance(perfInc);
		machine.deltaQuality(qualityInc);
		machine.deltaRequiredEmps(requiredEmpInc);
		machine.setInUpgrade(false);

		//2. try to reassign employees
		Employee[] emps = empMgr.getUnassignedEmployees(EmployeeType.PRODUCTION, machine.getRequiredEmps());
		if(emps != null){
			for (Employee e : emps) {
				e.assignWorkplace(machine);
			}
		}
	}

}

package sim.procurement;

import sim.hr.Department;
import sim.hr.Employee;
import sim.hr.EmployeeType;

/**
 * make sure we have at least one procurement guy to enable buying resources :P
 */
public class Procurement extends Department {

	public Procurement() {
		super(EmployeeType.PROCUREMENT);
	}
	
	@Override
	protected boolean unassignEmployee(Employee e, boolean calledFromEmployeeObject) {
		if(getEmployeeCount() - 1 > 0)
			return super.unassignEmployee(e, calledFromEmployeeObject);
		else
			return false;
	}

}

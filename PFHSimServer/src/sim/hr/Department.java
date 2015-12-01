package sim.hr;

import java.util.ArrayList;

public class Department implements Workplace {
	
	private ArrayList<Employee> employees;
	private EmployeeType type;
	
	public Department(EmployeeType type) {
		this.employees = new ArrayList<>();
		this.type = type;
	}

	@Override
	public boolean assignEmployee(Employee e) {
		if(e.getType() == type && !employees.contains(e))
			return employees.add(e);
		return false;
	}

	@Override
	public boolean unassignEmployee(Employee e) {
		if(e.getWork() != this)
			return false;
		return employees.remove(e);
	}
	
	public int getEmployeeCount(){
		return employees.size();
	}
	
	ArrayList<Employee> getEmployees(){
		return employees;
	}

}

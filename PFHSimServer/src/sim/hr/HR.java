package sim.hr;

import java.util.ArrayList;
import java.util.HashMap;

public class HR extends Department {
	
	/**
	 * register of all hired employees
	 */
	private HashMap<EmployeeType, ArrayList<Employee>> employeeList;
	
	private int employeeCount;

	public HR() {
		super(EmployeeType.HR);
		
		employeeList = new HashMap<>();
		EmployeeType[] types = EmployeeType.values();
		for (EmployeeType type : types) {
			employeeList.put(type, new ArrayList<>());
		}
	}
	
	public Employee hire(EmployeeType type){
		Employee e = new Employee(type);
		
		//TODO: check if there is enough HR capa available if we want to hire something else than an HR person
		//if(type != EmployeeType.HR){
		//}
		
		//TODO: setup the employee with the costs according to his type

		employeeCount++;
		employeeList.get(type).add(e);
		
		return e;
	}
	
	public boolean fire(Employee e){
		//TODO: implement
		return false;
	}

	public Employee getUnassignedEmployee(EmployeeType t){
		ArrayList<Employee> emps = employeeList.get(t);

		for (Employee e : emps) {
			if(!e.isAssigned())
				return e;
		}

		return null;
	}

	public Employee[] getUnassignedEmployees(EmployeeType t, int amount){
		ArrayList<Employee> emps = employeeList.get(t);

		Employee[] ret = new Employee[amount];
		int c = 0;
		for (int i = 0; i < emps.size() && c < amount; i++) {
			if(emps.get(i).getType() == t){
				ret[c++] = emps.remove(i--);
			}
		}
		
		if(c == amount)
			return ret;
		else
			return null;
	}
	
	public int getOverallEmployeeCount(){
		return employeeCount;
	}
	
	@Override
	public boolean unassignEmployee(Employee e) {
		
		if(getEmployeeCount() > 1){
			//TODO: check if there is enough HR capacity available after unassignment
			return super.unassignEmployee(e);
		}
		
		return false;
	}

}

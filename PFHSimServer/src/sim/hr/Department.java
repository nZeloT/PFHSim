package sim.hr;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the Department base class.
 * it is responsible for the employee handling for the corresponding Departments
 * 
 * use the assign and unassign methods to assign or unassign new employees
 * 
 * @author Leon
 */
public class Department {
	
	private List<Employee> employees;
	private EmployeeType type;
	
	public Department(EmployeeType type) {
		this.employees = new ArrayList<>();
		this.type = type;
	}

	/**
	 * assign an employee to the department
	 * @param e the employee to assign
	 * @return whether the assignment was successful
	 */
	public final boolean assignEmployee(Employee e) {
		return assignEmployee(e, false);
	}
	
	/**
	 * internal assignment handling; overwrite this method for further checks; but call exactly this method then from within
	 * @param e the employee to be assigned
	 * @param calledFromEmployeeObject whether the call came from the employee object; just pass this value
	 * @return whether the assignment was successful
	 */
	protected boolean assignEmployee(Employee e, boolean calledFromEmployeeObject){
		if(!calledFromEmployeeObject)
			return e.assignWorkplace(this);
		
		if(e.getType() == type && !employees.contains(e))
			return employees.add(e);
		return false;
	}

	/**
	 * unassign the given employee from this department
	 * @param e the employee to unassign
	 * @return whether the unassignment was successful
	 */
	public final boolean unassignEmployee(Employee e) {
		return unassignEmployee(e, false);
	}
	
	/**
	 * internal unassignment handling; overwrite this method for further checks; but call exactly this method then from within
	 * @param e the employee to unassign
	 * @param calledFromEmployeeObject whether the call came from the employee object; just pass this value
	 * @return whether the unassignment was successful
	 */
	protected boolean unassignEmployee(Employee e, boolean calledFromEmployeeObject){
		if(!calledFromEmployeeObject)
			return e.unassignWorkplace();
		
		if(e.getWork() != this)
			return false;
		return employees.remove(e);
	}
	
	public int getEmployeeCount(){
		return employees.size();
	}
	
	public int getEmployeeCosts(){
		int costs = 0;
		for (Employee employee : employees) {
			costs += employee.getCosts();
		}
		return costs;
	}
	
	List<Employee> getEmployees(){
		return employees;
	}

}

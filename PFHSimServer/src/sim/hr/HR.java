package sim.hr;

import java.util.ArrayList;
import java.util.HashMap;

import sim.research.dev.UpgradeFactors;

/**
 * This represents the HR department as a workplace for HR employees.<br>
 * Those HR employees provide HR capacity to hire employees of the other types.
 * This is thereby also the place to hire and fire employees of any given type.<br>
 * Additionally it is possible to retrieve a list of unassigned employees of any given type.
 * 
 * @author Leon
 */
public class HR extends Department {

	/**
	 * register of all hired employees
	 */
	private HashMap<EmployeeType, ArrayList<Employee>> employeeList;

	/**
	 * Number of all hired employees
	 */
	private int employeeCount;

	public HR() {
		super(EmployeeType.HR);

		employeeList = new HashMap<>();
		EmployeeType[] types = EmployeeType.values();
		for (EmployeeType type : types) {
			employeeList.put(type, new ArrayList<>());
		}
	}

	/**
	 * Hire a new employee of the given type
	 * @param type the employee type to be hired
	 * @return the newly hired employee or null if no new employee could be hired because of HR capa limitations
	 */
	public Employee hire(EmployeeType type){
		if(type != EmployeeType.HR){
			//Check whether there is enough HR capa for anything but new HR people ;D
			if(getHRCapacity() - employeeCount < 1)
				return null;
		}

		Employee e = new Employee(type);

		employeeCount++;
		employeeList.get(type).add(e);

		return e;
	}

	/**
	 * Try to fire the given employee
	 * @param e the employee to fire
	 * @return whether the employee could be fired successfully
	 */
	public boolean fire(Employee e){
		//1. try to unassign the employee
		if(e.unassignWorkplace() && e.getType() != EmployeeType.HR){

			//2. Fired!
			employeeCount--;
			//try to remove him from the list
			return employeeList.get(e.getType()).remove(e);
		}else{

			//temporary make the employeecount one less to unassign the HR guy
			employeeCount--;
			if(e.unassignWorkplace())
				return employeeList.get(e.getType()).remove(e);
			else
				employeeCount++;

		}

		return false;
	}

	/**
	 * Find an currently unassigned employee of the given type
	 * @param t the EmployeeType the unassigned employee should be of
	 * @return if an unassigned employee was found the employee else null
	 */
	public Employee getUnassignedEmployee(EmployeeType t){
		ArrayList<Employee> emps = employeeList.get(t);

		for (Employee e : emps) {
			if(!e.isAssigned())
				return e;
		}

		return null;
	}

	/**
	 * Find a bunch of currently unassigned employees of the given type
	 * @param t the EmployeeType the unassigned employees should be of 
	 * @param amount the amount of unassigned employees to get
	 * @return the unassigned employees if enough could be found else null
	 * @see getUnassignedEmployee
	 */
	public Employee[] getUnassignedEmployees(EmployeeType t, int amount){
		ArrayList<Employee> emps = employeeList.get(t);

		Employee[] ret = new Employee[amount];
		int c = 0;
		for (int i = 0; i < emps.size() && c < amount; i++) {
			if(emps.get(i).getType() == t && !emps.get(i).isAssigned()){
				ret[c++] = emps.get(i);
			}
		}

		if(c == amount)
			return ret;
		else
			return null;
	}

	/**
	 * @return the number of currently hired employees
	 */
	public int getOverallEmployeeCount(){
		return employeeCount;
	}

	/**
	 * @return the costs of all hired employees
	 */
	public int getOverallEmployeeCosts(){
		int costs = 0;
		for (ArrayList<Employee> emps : employeeList.values()) {
			for (Employee e : emps) {
				costs += e.getCosts();
			}
		}
		return costs;
	}

	/**
	 * @param type the employee type
	 * @return the costs of all hired employees of the given type
	 */
	public int getOverallEmployeeCosts(EmployeeType type){
		int costs = 0;
		for (Employee e : employeeList.get(type)) {
			costs += e.getCosts();
		}
		return costs;
	}

	@Override
	public boolean unassignEmployee(Employee e) {
		if(e.getWork() != this)
			return false;

		int manageable = getHRCapacity();
		manageable -= e.getSkill() * UpgradeFactors.HR_MANAGE_AMOUNT_FACTOR;

		//if we want to fire the hr guy, the employeecount is 1 less. this enables firing but not only unassigning
		if(manageable >= employeeCount)
			return super.unassignEmployee(e);

		return false;
	}

	/**
	 * @return the maximum amount of hireable employees according to the HR guys skill factor
	 */
	private int getHRCapacity(){
		int manageable = 0;
		for (Employee hrEmp : getEmployees())
			manageable += (hrEmp.getSkill() * UpgradeFactors.HR_MANAGE_AMOUNT_FACTOR);
		return manageable;
	}
}
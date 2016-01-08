package sim.hr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	 * Ensure the allowed employee count is at least 1 to hire 1 HR guy at the beginning
	 */
	private static final int BASE_EMP_COUNT = 1;

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
		//Check whether there is enough HR capa
		if(getHRCapacity() - employeeCount >= 1){
			Employee e = new Employee(type);

			employeeCount++;
			employeeList.get(type).add(e);

			return e;
		}else
			return null;
	}

	/**
	 * HIre a bunch of new employees of any given type except HR; HR guys can only be hire single wise ATM;
	 * @param t the employee type to be hired
	 * @param amount the amount of new employees
	 * @return the newly hire but unassigned employees or null if there is not enough HR capa
	 */
	public Employee[] hire(EmployeeType t, int amount){
		if(getHRCapacity() - employeeCount < amount)
			return null;

		Employee[] guys = new Employee[amount];
		for (int i = 0; i < amount; i++) {
			Employee e = new Employee(t);

			employeeCount++;
			employeeList.get(t).add(e);
			guys[i] = e;
		}

		return guys;
	}

	/**
	 * Try to fire the given employee
	 * @param e the employee to fire
	 * @return whether the employee could be fired successfully
	 */
	public boolean fire(Employee e){
		
		//1. try to unassign the employee
		if(e.unassignWorkplace()){

			//2. Fired!
			employeeCount--;
			//try to remove him from the list
			return employeeList.get(e.getType()).remove(e);
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
	 * see return-annotation.
	 * 
	 * @param t the EmployeeType the unassigned employee should be of
	 * @return return the number of unassinged Employees of a specific type
	 */
	public int getNumberOfUnassignedEmployees(EmployeeType t){
		ArrayList<Employee> emps = employeeList.get(t);
		int no = 0;

		for (Employee e : emps) {
			if(!e.isAssigned())
				no++;
		}

		return no;
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
	protected boolean unassignEmployee(Employee e, boolean calledFromEmployeeObject) {
		if(e.getWork() != this) // for the sake of safety ;)
			return false;

		int manageable = getHRCapacity();
		manageable -= e.getSkill() * UpgradeFactors.HR_MANAGE_AMOUNT_FACTOR;

		if(manageable >= employeeCount)
			return super.unassignEmployee(e, calledFromEmployeeObject);

		return false;
	}

	/**
	 * @return the maximum amount of hireable employees according to the HR guys skill factor
	 */
	public int getHRCapacity(){
		int manageable = 0;
		for (Employee hrEmp : getEmployees())
			manageable += (hrEmp.getSkill() * UpgradeFactors.HR_MANAGE_AMOUNT_FACTOR);
		manageable += BASE_EMP_COUNT;
		return manageable;
	}
	
	public int getRemainingHRCapacity(){
		return getHRCapacity() - getOverallEmployeeCount();
	}
	
	public int getAmount(EmployeeType type){
	
		ArrayList<Employee> list = employeeList.get(type);
		
		return list.size();
	}
	
	public Employee[] getAllOfType(EmployeeType t){
		return employeeList.get(t).toArray(new Employee[0]);
	}
	
	public int getCountOfFreeOfType(EmployeeType type){
		List<Employee> emps = employeeList.get(type);
		int free = 0;
		for (Employee em : emps) {
			if(!em.isAssigned())
				free++;
		}
		return free;
	}
	
	public boolean canHireNewEmployees(int amount){
		return getRemainingHRCapacity() - amount >= 0;
	}
}
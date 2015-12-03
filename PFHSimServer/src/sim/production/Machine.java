package sim.production;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sim.abstraction.CostFactor;
import sim.abstraction.WrongEmployeeTypeException;
import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.hr.Workplace;
import sim.procurement.Resource;
import sim.procurement.ResourceType;
import sim.warehouse.Warehouse;

public class Machine implements CostFactor, Workplace {

	/**
	 * Costs for a machine per period.
	 */
	private int costs;

	/**
	 * Maximum performance points of this machine per period. Performance -
	 * Utilization = Available performance points
	 */
	private int performance;

	/**
	 * Performance - Utilization = Available performance points
	 */
	private int utilization;

	/**
	 * This machine's production quality.
	 */
	private double quality;

	private boolean inUpgrade;

	private ArrayList<Employee> employees;
	private int requiredEmps;

	private MachineType type;

	/**
	 * Warehouse: A machine needs to access the warehouse to extract and store
	 * resources. Employee: When generating a new machine, it needs at least 3
	 * employees for being in operation!
	 */
	public Machine(MachineType type, List<Employee> employees) throws Exception {
		this.employees = new ArrayList<Employee>();
		this.requiredEmps = type.getRequiredEmps();

		for (int i = 0; i < employees.size(); i++) {
			if (employees.get(i).getType() != EmployeeType.PRODUCTION)
				throw new WrongEmployeeTypeException();
		}

		for (Employee e : employees) {
			e.assignWorkplace(this);
		}

		this.costs = type.getCosts();
		this.performance = type.getOutput();
		this.utilization = 0;
		this.type = type;
	}

	@Override
	public int getCosts() {
		return costs;
	}

	public void setCosts(int costs) {
		this.costs = costs;
	}

	/**
	 * Get all available performance-points for producing a wall. Formula:
	 * Performance - Utilization
	 */
	public int getAvailablePerformance() {
		return ((int) (performance - utilization));
	}

	/**
	 * 
	 * This method is called to check whether a specific wall is producable with
	 * this machine and the given resources in the warehouse. This method
	 * increases the machine's utilization likewise.
	 * 
	 */
	public boolean isProducable(WallType walltype, Warehouse warehouse) {


		// Wrong machine-type for building such a type of wall?
		boolean ableToHandle = false;
		WallType[] walltypesToHandle = type.getWalltypesToHandle();
		for (WallType tmp : walltypesToHandle) {
			if (tmp == walltype)
				ableToHandle = true;
		}
		if (!ableToHandle)
			return false;
		
		if ((performance - utilization) < 1 || !isInOperation())
			return false;

		ResourceType[] rt = walltype.getRequiredResourceTypes();
		int[] rc = walltype.getResourceCounts();

		for (int i = 0; i < rc.length; i++) {

			if (!warehouse.isInStorage(rt[i], rc[i])) {
				return false;
			}
		}

		utilization++;
		return true;
	}

	/**
	 * 
	 * Produce wall - method is to be called within the simulation-period at the
	 * end of each period to produce a wall. Therefore all required resources
	 * are taken from the warehouse and one wall is stored in the warehouse at
	 * the end. The respective production-costs are calculated, too.
	 * 
	 */
	public void produceWall(WallType walltype, Warehouse warehouse) throws MachineException  {

		// Wrong machine-type for building such a type of wall?
		boolean ableToHandle = false;
		WallType[] walltypesToHandle = type.getWalltypesToHandle();
		for (WallType tmp : walltypesToHandle) {
			if (tmp == walltype)
				ableToHandle = true;
		}
		if (!ableToHandle)
			throw new MachineException("This machine is not able to produce the given WallType!");
			
		if ((performance - utilization) < 1 || !isInOperation())
			throw new MachineException("This machine is too busy or within an upgrade and thus it cannot be used currently!");

		ResourceType[] rt = walltype.getRequiredResourceTypes();
		int[] rc = walltype.getResourceCounts();

		// Get resources from warehouse.
		ArrayList<Resource[]> removed_resources = new ArrayList<>();
		for (int i = 0; i < rc.length; i++) {
			removed_resources.add(warehouse.removeResource(rt[i], rc[i]));

			// If there are not enough resources available,
			// terminate the process of creation.
			if (removed_resources.get(i) == null) {
				// But before termination, the from the warehouse already
				// removed resources must be stored back.
				for (int j = 0; j < i; j++) {
					for (int k = 0; k < removed_resources.get(j).length; k++) {
						warehouse.storeResource(removed_resources.get(j)[k]);
					}
				}
				throw new MachineException("Not enough resources available in the warehouse!");
			}
		}

		// Calculate production cost at highest utilization possible.
		// Therefore each resource-object taken from the warehouse must
		// be included in the calculation.
		int wallcost = 0;
		for (int i = 0; i < removed_resources.size(); i++) {
			for (int j = 0; j < removed_resources.get(i).length; j++) {
				wallcost += removed_resources.get(i)[j].getCosts();
			}
		}
		// calculation at highest utilization possible.
		wallcost += (int) (1.0 / performance * this.costs);
		for (int i = 0; i < this.employees.size(); i++) {
			wallcost += (int) (1.0 / performance * employees.get(i).getCosts());
		}

		// Creation of a new wall.
		Wall wall = new Wall(walltype, wallcost);

		// Try to store the wall
		if (warehouse.storeWall(wall)) {
			if (utilization > 0)
				utilization--;
			//return true; - Successful case.
		} else {
			// If the warehouse is not able to store the wall
			// (for example because of too less free space), store the
			// already removed resources again.
			for (int j = 0; j < removed_resources.size(); j++) {
				for (int k = 0; k < removed_resources.get(j).length; k++) {
					warehouse.storeResource(removed_resources.get(j)[k]);
				}
			}
			throw new MachineException("Warehouse has not enough capacity to store the wall!");
		}

	}

	public ArrayList<Employee> getAssignedEmployees() {
		return employees;
	}

	public int getRequiredEmps() {
		return requiredEmps;
	}

	@Override
	public boolean assignEmployee(Employee e) {

		if (e.getType() == EmployeeType.PRODUCTION) {
			employees.add(e);
			return true;
		}

		return false;
	}

	@Override
	public boolean unassignEmployee(Employee e) {
		return employees.remove(e);
	}

	/**
	 * For example, a machine is not in operation when it is upgraded (the
	 * upgrade needs some time) or when not enough employees are assigned to
	 * this machine.
	 */
	public boolean isInOperation() {
		return employees.size() >= requiredEmps && !inUpgrade;
	}

	public void setInUpgrade(boolean inUpgrade) {
		this.inUpgrade = inUpgrade;
	}

	public boolean isInUpgrade() {
		return inUpgrade;
	}

	public void deltaPerformance(double factor) {
		performance += factor;
	}

	public void deltaCosts(double factor) {
		costs += factor;
	}

	public void deltaQuality(double factor) {
		quality += factor;
	}

	public void deltaRequiredEmps(int amount) {
		requiredEmps += amount;
	}

}

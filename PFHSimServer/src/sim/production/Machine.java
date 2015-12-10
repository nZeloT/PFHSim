package sim.production;

import java.util.ArrayList;
import java.util.List;

import sim.abstraction.CostFactor;
import sim.hr.Department;
import sim.hr.EmployeeType;
import sim.procurement.Resource;
import sim.procurement.ResourceType;
import sim.warehouse.Warehouse;

public class Machine extends Department implements CostFactor {

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

	private int requiredEmps;

	private MachineType type;

	private WallType productionType;
	private int maxOutput;

	/**
	 * Warehouse: A machine needs to access the warehouse to extract and store
	 * resources. Employee: When generating a new machine, it needs at least 3
	 * employees for being in operation!
	 */
	public Machine(MachineType type){
		super(EmployeeType.PRODUCTION);
		this.requiredEmps = type.getRequiredEmps();

		this.costs = type.getCosts();
		this.performance = type.getOutput();
		this.utilization = 0;
		this.type = type;

		this.maxOutput = this.performance;
		this.productionType = type.getWalltypesToHandle()[0];
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

		return true;
	}

	/**
	 * Produce as many walls as requested or until an error occurs; this is for simulation and therefore not public
	 */
	void runProductionStep(Warehouse w) throws MachineException{
		utilization = 0;
		while(utilization < maxOutput){
			produceWall(w);
		}
	}

	/**
	 * 
	 * Produce wall - method is to be called within the simulation-period at the
	 * end of each period to produce a wall. Therefore all required resources
	 * are taken from the warehouse and one wall is stored in the warehouse at
	 * the end. The respective production-costs are calculated, too.
	 * 
	 */
	public void produceWall(Warehouse warehouse) throws MachineException  {

		if ((performance - utilization) < 1 || !isInOperation() || utilization+1 > maxOutput)
			throw new MachineException(this, "This machine is too busy or within an upgrade and thus it cannot be used currently!");

		ResourceType[] rt = productionType.getRequiredResourceTypes();
		int[] rc = productionType.getResourceCounts();

		// Get resources from warehouse.
		List<Resource[]> removed_resources = new ArrayList<>();
		int[] avg_costs = new int[rt.length]; //save the avg costs before removing
		for (int i = 0; i < rc.length; i++) {
			avg_costs[i] = warehouse.calculateAvgPrice(rt[i]);
			// If there are not enough resources available,
			// terminate the process of creation.
			if (warehouse.isInStorage(rt[i], rc[i])) 
				removed_resources.add(warehouse.removeResource(rt[i], rc[i]));
			else
				throw new MachineException(this, "Not enough resources available in the warehouse!");
			
		}

		// Calculate production cost at highest utilization possible.
		// Therefore each resource-object taken from the warehouse must
		// be included in the calculation.
		int wallcost = 0;
		for (int i = 0; i < avg_costs.length; i++) {
			wallcost += avg_costs[i]*rc[i];
		}
		// calculation at highest utilization possible.
		wallcost += (int) (1.0 / performance * this.costs);
		wallcost += (int) (1.0 / performance * getEmployeeCosts());

		// Creation of a new wall.
		Wall wall = new Wall(productionType, wallcost);

		// Try to store the wall
		if (warehouse.storeWall(wall)) {
			utilization++;
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
			throw new MachineException(this, "Warehouse has not enough capacity to store the wall!");
		}

	}

	/**
	 * Set the Walltype produced by the machine within the next simulation step
	 * @param productionType the walltype to produce; needs to be one of the handable of the machine
	 * @throws MachineException in failure an exception is thrown
	 */
	public void setProductionType(WallType productionType) throws MachineException {
		// Wrong machine-type for building such a type of wall?
		boolean ableToHandle = false;
		WallType[] walltypesToHandle = type.getWalltypesToHandle();
		for (WallType tmp : walltypesToHandle) {
			if (tmp == productionType)
				ableToHandle = true;
		}
		if (!ableToHandle)
			throw new MachineException(this, "This machine is not able to produce the given WallType!");

		this.productionType = productionType;
	}

	public boolean setMaxOutput(int maxOutput) {
		if(maxOutput <= performance)
			this.maxOutput = maxOutput;
		return maxOutput <= performance;
	}

	public int getRequiredEmps() {
		return requiredEmps;
	}

	/**
	 * For example, a machine is not in operation when it is upgraded (the
	 * upgrade needs some time) or when not enough employees are assigned to
	 * this machine.
	 */
	public boolean isInOperation() {
		return getEmployeeCount() >= requiredEmps && !inUpgrade;
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

	public MachineType getType() {
		return type;
	}

	public int getPerformance() {
		return performance;
	}

	public int getMaxOutput() {
		return maxOutput;
	}

	public WallType getProductionType() {
		return productionType;
	}

}

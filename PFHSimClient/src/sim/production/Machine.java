package sim.production;

import java.util.ArrayList;
import java.util.List;

import sim.ExceptionCategorie;
import sim.abstraction.CostFactor;
import sim.hr.Department;
import sim.hr.EmployeeType;
import sim.procurement.Resource;
import sim.procurement.ResourceType;
import sim.warehouse.Warehouse;
import sim.warehouse.WarehouseException;

public class Machine extends Department implements CostFactor {
	
	private static int count = 0;

	/**
	 * Performance - Utilization = Available performance points
	 */
	private int utilization;

	private boolean inUpgrade;
	private int upgradeCount;

	private MachineType type;

	private WallType productionType;
	
	private int maxPerformanceOutput;
	
	private int no;

	/**
	 * Warehouse: A machine needs to access the warehouse to extract and store
	 * resources. Employee: When generating a new machine, it needs at least 3
	 * employees for being in operation!
	 */
	public Machine(MachineType type){
		super(EmployeeType.PRODUCTION);

		this.utilization = 0;
		this.type = type;

		this.maxPerformanceOutput = getPerformance();
		this.productionType = type.getWalltypesToHandle()[0];
		
		this.no = count ++;
	}

	public int getQuality() {
		return type.getBaseQualityFactor() + upgradeCount * type.getUpgradeQualInc();
	}

	/**
	 * Costs for a machine per period.
	 */
	@Override
	public int getCosts() {
		return type.getBaseCosts() + upgradeCount * type.getUpgradeCostInc();
	}

	/**
	 * Get all available performance-points for producing a wall. Formula:
	 * Performance - Utilization
	 */
	public int getAvailablePerformance() {
		return ((int) (getPerformance() - utilization));
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

		if ((getPerformance() - utilization) < 1 || !isInOperation())
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
	void runProductionStep(Warehouse w) throws MachineException, WarehouseException{
		utilization = 0;
		while(utilization < maxPerformanceOutput){
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
	public void produceWall(Warehouse warehouse) throws MachineException, WarehouseException  {
		
		if(isInUpgrade())
			throw new MachineException(this, "This machine is currently in Upgrade.", ExceptionCategorie.INFO);

		if ((getPerformance() - utilization) < 1 || !isInOperation() || utilization+1 > maxPerformanceOutput)
			throw new MachineException(this, "This machine is currently too busy!", ExceptionCategorie.INFO);

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
				throw new WarehouseException(this, "Not enough resources available!", ExceptionCategorie.ERROR);
			
		}

		// Calculate production cost at highest utilization possible.
		// Therefore each resource-object taken from the warehouse must
		// be included in the calculation.
		int wallcost = 0;
		for (int i = 0; i < avg_costs.length; i++) {
			wallcost += avg_costs[i]*rc[i];
		}
		// calculation at highest utilization possible.
		wallcost += (int) (1.0 / getPerformance() * getCosts());
		wallcost += (int) (1.0 / getPerformance() * getEmployeeCosts());

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
			throw new WarehouseException(this, "Can not store wall!", ExceptionCategorie.ERROR);
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
			throw new MachineException(this, "This machine is not able to produce the given WallType!", ExceptionCategorie.PROGRAMMING_ERROR);

		this.productionType = productionType;
	}

	public boolean setMaxOutput(int maxOutput) {
		if(maxOutput <= getPerformance())
			this.maxPerformanceOutput = maxOutput;
		return maxOutput <= getPerformance();
	}
	
	public boolean canDoUpgrade(){
		return upgradeCount < type.getPossibleUpgrades() && !isInUpgrade();
	}

	public int getRequiredEmps() {
		return type.getBaseRequiredEmps() + upgradeCount * type.getUpgradeEmpInc();
	}

	/**
	 * For example, a machine is not in operation when it is upgraded (the
	 * upgrade needs some time) or when not enough employees are assigned to
	 * this machine.
	 */
	public boolean isInOperation() {
		return getEmployeeCount() >= getRequiredEmps() && !inUpgrade;
	}

	public void setInUpgrade(boolean inUpgrade) {
		this.inUpgrade = inUpgrade;
	}

	public boolean isInUpgrade() {
		return inUpgrade;
	}

	public MachineType getType() {
		return type;
	}

	/**
	 * Maximum performance points of this machine per period. Performance -
	 * Utilization = Available performance points
	 */
	public int getPerformance() {
		return type.getBasePerformance() + type.getUpgradePerfInc() * upgradeCount;
	}

	public int getMaxOutput() {
		return maxPerformanceOutput;
	}

	public WallType getProductionType() {
		return productionType;
	}
	
	public String getId(){
		return type + " #" + no;
	}
	
	public void upgrade(){
		upgradeCount++;
	}

}

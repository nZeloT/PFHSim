package sim.production;

import java.util.ArrayList;
import java.util.List;

import sim.Enterprise;
import sim.abstraction.CostFactor;
import sim.hr.Employee;
import sim.procurement.Resource;
import sim.procurement.ResourceType;
import sim.warehouse.Warehouse;

public class Machine implements CostFactor {
	
	/**
	 * Costs for a machine per period.
	 * */
	private int costs;
	
	/**
	 * Maximum performance points of this machine per period.
	 * Performance - Utilization = Available performance points
	 * */
	private int performance;
	
	/**
	 * Performance - Utilization = Available performance points
	 * */
	private int utilization;
	
	/**
	 * This machine's production quality.
	 * */
	private double quality;
	
	/**For example, a machine is not in operation when
	 * it is upgraded (the upgrade needs some time) or when
	 * not enough employees are assigned to this machine.
	 * */
	private boolean inOperation;
	
	private ArrayList<Employee> employees;
	
	private Warehouse warehouse;
	
	/**
	 * Warehouse: A machine needs to access the warehouse to extract and store resources.
	 * Employee: When generating a new machine, it needs at least 3 employees for being in operation!
	 * */
	public Machine(Warehouse warehouse, ArrayList<Employee> employees) {
		this.warehouse = warehouse;
		this.employees = employees;
		this.costs = 200;
		this.performance = 30;
		this.utilization = 0;
		//3 employees or more are neccessary for
		//operating the machine.
		if (employees.size() < 3) 
			inOperation = true;
		else 
			inOperation = false;
	}
	
	@Override
	public int getCosts() {
		return costs;
	}

	@Override
	public void setCosts(int costs) {

	}
	
	/**
	 * Get all available performance-points for producing a wall.
	 * Formula: Performance - Utilization
	 * */
	public int getAvailablePerformance() {
		return ((int) (performance-utilization));
	}
	
	/**
	 * 
	 * This method is called to check whether a specific wall is producable
	 * with this machine and the given resources in the warehouse.
	 * This method increases the machine's utilization likewise.
	 * 
	 * */
	public boolean isProducable(WallType walltype) {
		
		if ((performance-utilization)<1 || !inOperation)
			return false;
		
		ResourceType[] rt = walltype.getRequiredResourceTypes();
		int[] rc = walltype.getResourceCounts();
		
		for (int i = 0; i < rc.length; i++) {
			
			if (!warehouse.isInStorage(rt[i], rc[i])) {
				System.out.println("aaaargh" + i);
				return false;
			}
		}
			
		utilization++;
		return true;
	}
	
	/**
	 * 
	 * Produce wall - method is to be called within the simulation-period at the end of each period
	 * to produce a wall. Therefore all required resources are taken from the warehouse and one wall is
	 * stored in the warehouse at the end. The respective production-costs are calculated, too.
	 * 
	 * */
	public boolean produceWall(WallType walltype) {


		if ((performance-utilization)<1 || !inOperation) 
			return false;
		
		
		ResourceType[] rt = walltype.getRequiredResourceTypes();
		int[] rc = walltype.getResourceCounts();
		
		
		//Get resources from warehouse.
		Resource[][] removed_resources = new Resource[rt.length][rc.length];
		for (int i = 0; i < rc.length; i++) {
			removed_resources[i] = warehouse.removeResource(rt[i], rc[i]);
			
			//If there are not enough resources available,
			//terminate the process of creation.
			if (removed_resources[i]==null) {
				//But before termination, the from the warehouse already
				//removed resources must be stored back.
				for (int j = 0; j < i; j++) {
					for (int k=0; k < removed_resources[j].length; k++) {
						warehouse.storeResource(removed_resources[j][k]);
					}
				}
				return false;
			}
		}
		
		
		//Calculate production cost at highest utilization possible.
		//Therefore each resource-object taken from the warehouse must
		//be included in the calculation.
		@SuppressWarnings(value = { "unused" })
		int wallcost = 0;
		for (int i = 0; i < removed_resources.length; i++) {
			for (int j = 0; j < removed_resources[i].length; j++) {
				wallcost += removed_resources[i][j].getCosts();
			}
		}
		//calculation at highest utilization  possible.
		wallcost += (int) (1.0/performance * this.costs);
		for (int i = 0; i < this.employees.size(); i++) {
			wallcost += (int) (1.0/performance * employees.get(i).getCosts());
		}
		
		//Creation of a new wall.
		Wall wall = new Wall(walltype, costs);
		
		//Try to store the wall
		if (warehouse.storeWall(wall)) {
			if (utilization>0)
				utilization--;
			return true;
		} else {
			//If the warehouse is not able to store the wall
			//(for example because of too less free space), store the
			//already removed resources again.
			for (int j = 0; j < removed_resources.length; j++) {
				for (int k=0; k < removed_resources[j].length; k++) {
					warehouse.storeResource(removed_resources[j][k]);
				}
			}
			return false;
		}
		
		
	}

}

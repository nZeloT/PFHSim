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
	
	private int costs;
	private int performance;
	private int utilization;
	
	//production quality
	private double quality;
	
	private boolean inOperation;
	
	private ArrayList<Employee> employees;
	
	private Warehouse warehouse;

	public Machine(Warehouse warehouse, ArrayList<Employee> employees) {
		this.warehouse = warehouse;
		this.performance = performance;
		this.employees = employees;
		this.costs = 200;
		this.performance = 30;
		this.utilization = 0;
		inOperation=true;
	}
	
	@Override
	public int getCosts() {
		return costs;
	}

	@Override
	public void setCosts(int costs) {

	}
	
	public int getAvailablePerformance() {
		return (performance-utilization);
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
				return false;
			}
		}
			
		utilization++;
		return true;
	}
	
	/**
	 * 
	 * Produce wall is to be called within the simulation-period at the end of each period
	 * to produce a wall. 
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
		int wallcost = 0;
		for (int i = 0; i < removed_resources.length; i++) {
			for (int j = 0; j < removed_resources[i].length; j++) {
				wallcost += removed_resources[i][j].getCosts();
			}
		}
		//calculation at highest utilization  possible.
		wallcost += (int) (1/performance * this.costs);
		for (int i = 0; i < this.employees.size(); i++) {
			wallcost += (int) (1/performance * employees.get(i).getCosts());
		}
		System.out.println("Production cost for one wall is: " + wallcost);
		
		//Creation of a new wall.
		Wall wall = new Wall(walltype, costs);
		
		
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

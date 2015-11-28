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
	private double performance;
	
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
		inOperation=true;
	}
	
	@Override
	public int getCosts() {
		return costs;
	}

	@Override
	public void setCosts(int costs) {

	}
	
	
	public boolean produceWall(WallType walltype) {


		if (performance<1 || !inOperation) 
			return false;
		
		
		ResourceType[] rt = walltype.getRequiredResourceTypes();
		int[] rc = walltype.getResourceCounts();
		
		
		//Get resources from warehouse.
		Resource[] removed_resources = null;
		for (int i = 0; i < rc.length; i++) {
			removed_resources = warehouse.removeResource(rt[i], rc[i]);
		}
		
		//If there are not enough resources available,
		//terminate the process of creation.
		if (removed_resources==null)
			return false;
		
		
		//Calculate production cost at highest performance possible.
		int wallcost = 0;
		for (int i = 0; i < removed_resources.length; i++) {
			System.out.println("" + removed_resources[i].getCosts());
				wallcost += removed_resources[i].getCosts();
		}
		wallcost += (int) (1/performance * this.costs);
		for (int i = 0; i < this.employees.size(); i++) {
			wallcost += (int) (1/performance * employees.get(i).getCosts());
		}
		System.out.println("Production cost for one wall is: " + wallcost);
		
		
		Wall wall = new Wall(walltype);
		wall.setCosts(costs);
		
		
		if (warehouse.storeWall(wall)) {
			performance--;
			return true;
		} else {
			warehouse.storeResource(removed_resources);
			return false;
		}
		
		
	}

}

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

	public Machine(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	
	@Override
	public int getCosts() {
		return costs;
	}

	@Override
	public void setCosts(int costs) {

	}
	
	
	public Wall produceWall(WallType walltype) {
		
		//isInStorage(ResourceType r, int count/WallType);
		
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
			return null;
		
		
		//Calculate production cost at highest performance possible.
		int wallcost = 0;
		for (int i = 0; i < removed_resources.length; i++) {
			wallcost += removed_resources[i].getCosts();
		}
		wallcost += 1/performance * this.costs;
		for (int i = 0; i < this.employees.size(); i++) {
			wallcost += 1/performance * employees.get(i).getCosts();
		}
		
		
		Wall wall = new Wall();
		wall.setCosts(costs);
		
		return wall;
		
		
	}

}

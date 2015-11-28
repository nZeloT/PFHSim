package sim.warehouse;

import java.util.ArrayList;

import sim.abstraction.CostFactor;
import sim.hr.Employee;
import sim.procurement.Resource;
import sim.production.Wall;

public class Warehouse implements CostFactor{

	private int capacity;
	private int costs;
	
	private ArrayList<Employee> employees;
	
	private ArrayList<Resource> resources;
	private ArrayList<Wall> walls;
	
	@Override
	public int getCosts() {
		return costs;
	}
	@Override
	public void setCosts(int costs) {
		
	}
	
}

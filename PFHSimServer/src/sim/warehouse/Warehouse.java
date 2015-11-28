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
	
	public Warehouse(){
		this(50, 10);
	}
	
	public Warehouse(int capacity, int costs){
		this.capacity = capacity;
		this.costs = costs;
	}
	
	public boolean storeWall(Wall w){
		return false;
	}
	
	public boolean storeResource(Resource r){
		
		return false;
	}
	
	public boolean removeWall(Wall w){
		return false;
	}
	
	public boolean removeResource(Resource r){
		return false;
	}
	
	public int getUtilization(){
		return 0;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	@Override
	public int getCosts() {
		return costs;
	}
	
	@Override
	public void setCosts(int costs) {
		
	}
	
}

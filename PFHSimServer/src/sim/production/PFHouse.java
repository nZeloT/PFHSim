package sim.production;

import java.util.ArrayList;

import sim.abstraction.CostFactor;
import sim.hr.Employee;

public class PFHouse implements CostFactor {
	
	private int price;
	
	private int costs;
	private double quality;
	
	private PFHouseType type;
	
	private int buildDuration;
	
	private ArrayList<Employee> employees;

	@Override
	public int getCosts() {
		return costs;
	}
	
	public PFHouse() {
		
	}
	



}

package sim.production;

import java.util.ArrayList;

import sim.abstraction.CostFactor;
import sim.hr.Employee;
import sim.hr.Workplace;

public class PFHouse implements CostFactor, Workplace {
	
	private int price;
	
	private int costs;
	private double quality;
	
	private PFHouseType type;
	
	private int buildDurationLeft;
	
	private ArrayList<Employee> employees;

	@Override
	public int getCosts() {
		return costs;
	}
	
	public PFHouse(int price, int costs, PFHouseType type, int buildDurationLeft, ArrayList<Employee> employees) {
		this.price = price;
		this.costs = costs;
		this.quality = 0;
		this.type = type;
		this.buildDurationLeft = buildDurationLeft;
		this.employees = employees;
	}

	@Override
	public boolean assignEmployee(Employee e) {
		return false;
	}

	@Override
	public boolean unassignEmployee(Employee e) {
		return false;
	}
	
	public void decreaseBuildDurationLeft() {
		buildDurationLeft--;
	}
	



}

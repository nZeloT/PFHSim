package sim.production;

import java.util.List;

import sim.abstraction.CostFactor;
import sim.hr.Department;
import sim.hr.Employee;
import sim.hr.EmployeeType;

public class PFHouse extends Department implements CostFactor {
	
	private int price;
	
	private int costs;
	private double quality;
	
	private PFHouseType type;
	
	private int buildDurationLeft;

	@Override
	public int getCosts() {
		return costs;
	}
	
	public PFHouse(int price, int costs, PFHouseType type, int buildDurationLeft, List<Employee> employees) {
		super(EmployeeType.ASSEMBLER);
		
		this.price = price;
		this.costs = costs;
		this.quality = 0;
		this.type = type;
		this.buildDurationLeft = buildDurationLeft;
		
		for (Employee e : employees) {
			e.assignWorkplace(this);
		}
	}

	public void decreaseBuildDurationLeft() {
		buildDurationLeft--;
	}

}

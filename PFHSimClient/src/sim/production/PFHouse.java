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

	public PFHouse(int price, int costs, PFHouseType type, List<Employee> employees) {
		super(EmployeeType.ASSEMBLER);
		
		this.price = price;
		this.costs = costs;
		this.quality = 0;
		this.type = type;
		this.buildDurationLeft = type.getConstructionDuration();
		
		for (Employee e : employees) {
			e.assignWorkplace(this);
		}
	}

	public void processConstruction() {
		if(getEmployeeCount() >= type.getEmployeeCount())
			buildDurationLeft--;
		if(isFinished())
			unassignAllEmployees();
	}
	
	public boolean isFinished(){
		return buildDurationLeft == 0;
	}
	
	@Override
	public int getCosts() {
		return costs;
	}
	
	public int getPrice() {
		return price;
	}
	
	public double getQuality() {
		return quality;
	}
	
	public PFHouseType getType() {
		return type;
	}
	
	@Override
	protected boolean unassignEmployee(Employee e, boolean calledFromEmployeeObject) {
		return isFinished() && super.unassignEmployee(e, calledFromEmployeeObject);
	}

}

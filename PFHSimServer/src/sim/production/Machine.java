package sim.production;

import java.util.ArrayList;

import sim.abstraction.CostFactor;
import sim.hr.Employee;

public class Machine implements CostFactor {
	
	private int costs;
	private double performance;
	
	//production quality
	private double quality;
	
	private boolean inOperation;
	
	private ArrayList<Employee> employees;

	@Override
	public int getCosts() {
		return costs;
	}

	@Override
	public void setCosts(int costs) {

	}

}

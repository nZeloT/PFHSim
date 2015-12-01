package sim.production;

import java.util.ArrayList;

import sim.abstraction.CostFactor;
import sim.hr.Employee;

public class ProductionHouse implements CostFactor {
	
	private ArrayList<Machine> machines;
	
	private int costs;

	
	public ProductionHouse() {
		machines = new ArrayList<>();
	}
	
	@Override
	public int getCosts() {
		return costs;
	}
	
	public ArrayList<Machine> getMachines() {
		return machines;
	}
	
	
	public void operateNewMachine(ArrayList<Employee> employees) {
		
		try {
			machines.add(new Machine(employees));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
	
package sim.production;

import java.util.ArrayList;
import java.util.List;

import sim.abstraction.CostFactor;
import sim.hr.Employee;

public class ProductionHouse implements CostFactor {
	
	private ArrayList<Machine> machines;
	
	private final int basecosts;

	
	public ProductionHouse() {
		machines = new ArrayList<>();
		basecosts = 500;
	}
	
	@Override
	public int getCosts() {
		return basecosts;
	}
	
	public ArrayList<Machine> getMachines() {
		return machines;
	}
	
	
	public void buyMachine(MachineType type) {
		
		try {
			machines.add(new Machine(type));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
	
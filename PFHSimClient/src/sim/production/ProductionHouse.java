package sim.production;

import java.util.ArrayList;
import java.util.List;

import sim.abstraction.CostFactor;
import sim.warehouse.Warehouse;

public class ProductionHouse implements CostFactor {

	private List<Machine> machines;

	private final int basecosts;


	public ProductionHouse() {
		machines = new ArrayList<>();
		basecosts = 500;
	}

	@Override
	public int getCosts() {
		return basecosts;
	}
	
	public int getMachineCosts(){
		int costs = 0;
		for (Machine m : machines)
			costs += m.getCosts();
		return costs;
	}

	public List<Machine> getMachines() {
		return machines;
	}

	/**
	 * Simulate one step of production in the production house
	 * @param w the warehouse for storing things
	 * @return a list of errors that occurred on the respective machines
	 */
	public List<MachineException> processProduction(Warehouse w){
		List<MachineException> errors = new ArrayList<>();

		for (Machine m : machines) {
			try {
				m.runProductionStep(w);
				System.out.println("DOSIM --  wall produced");
			} catch (MachineException me) {
				errors.add(me);
			}
		}

		return errors;
	}

	public int buyMachine(MachineType type) {
		machines.add(new Machine(type));
		return type.getPrice();

	}

}

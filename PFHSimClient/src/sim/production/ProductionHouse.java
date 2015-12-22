package sim.production;

import java.util.ArrayList;
import java.util.List;

import sim.abstraction.CostFactor;
import sim.abstraction.Tupel;
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

	public int getAvgMachineQuality(MachineType t) {
		int avg = 0;
		int count = 0;
		for (Machine machine : machines) {
			if (machine.getType() == t) {
				avg += machine.getQuality();
				count++;
			}
		}
		if (count!=0)
			return (int) (avg/count);
		else
			return 0;
	}
	public List<Tupel<MachineType>> getAllAvgMachineQualities() {
		MachineType[] t = MachineType.values();
		List<Tupel<MachineType>> avg = new ArrayList<>();
		for (int i = 0; i<t.length; i++) {
			int cAvg = getAvgMachineQuality(t[i]);
			if (cAvg != 0) {
				avg.set(i, new Tupel<MachineType>(t[i], cAvg));
			
			}
		}
		return avg;
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

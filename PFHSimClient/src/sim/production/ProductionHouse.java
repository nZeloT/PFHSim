package sim.production;

import java.util.ArrayList;
import java.util.List;

import sim.EnterpriseException;
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
			if (machine.getType() == t && machine.isInOperation() == true) {
				avg += machine.getQuality();
				count++;
			}
		}
		if (count!=0)
			return (int) ((avg+0d)/count);
		else
			return 0;
	}
	public List<Tupel<MachineType>> getAllAvgMachineQualities() {
		List<Tupel<MachineType>> avg = new ArrayList<>();
		for (MachineType t : MachineType.values()) {
			int cAvg = getAvgMachineQuality(t);
			if (cAvg != 0) {
				avg.add(new Tupel<MachineType>(t, cAvg));
			}
		}
		return avg;
	}
	
	/**
	 * Simulate one step of production in the production house
	 * @param w the warehouse for storing things
	 * @return a list of errors that occurred on the respective machines
	 */
	public List<EnterpriseException> processProduction(Warehouse w){
		List<EnterpriseException> errors = new ArrayList<>();

		for (Machine m : machines) {
			try {
				m.runProductionStep(w);
			} catch (EnterpriseException me) {
				errors.add(me);
			}
		}

		return errors;
	}

	public void buyMachine(MachineType type) {
		machines.add(new Machine(type));
	}
	
	public boolean sellMachine(Machine m){
		return !m.isInUpgrade() && m.unassignAllEmployees() && machines.remove(m);
	}

}

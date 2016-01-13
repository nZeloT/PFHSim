package sim.production;

import java.util.ArrayList;
import java.util.List;

import sim.EnterpriseException;
import sim.ExceptionCategorie;
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
	
	public void setWallQuality() {
		WallType[] t = WallType.values();
		
		for (WallType wallType : t) {
			
			wallType.setQualityFactor(wallType.getInitialQualityFactor());
			
			for (int i = 0; i < machines.size(); i++) {
				int ctr = 0;
				int qual = 0;
				if(machines.get(i).getProductionType() == wallType) {
					qual += machines.get(i).getQuality() * wallType.getInitialQualityFactor();
					ctr++;
				}
				if (ctr>0) {
					qual /= ctr;
					System.out.println("" + wallType.toString() + qual);
					wallType.setQualityFactor(qual);
				}
			}
		}
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
				errors.add(new MachineSuccessException(m, m.getProductionType(), m.getUtilization(), ExceptionCategorie.INFO));
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
	public WallType getWallTypeWithMaxQuality() {
		WallType[] t = WallType.values();
		WallType max = WallType.LIGHT_WEIGHT_CONSTRUCTION;
		for (WallType wallType : t) {
			if (wallType != WallType.GENERAL) {
				if (max.getQualityFactor()<wallType.getQualityFactor()) {
					max = wallType;
				}
			}
		}
		return max;
	}
}

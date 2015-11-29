package sim.research.dev;

import sim.production.Machine;

public class MachineUpgrade extends Upgrade {
	
	private double increase;
	private int costInc;
	private Machine machine;
	
	private boolean running;
	
	public MachineUpgrade(Machine m, int duration, int costs, double perfInc, int costInc) {
		super(duration, costs);
		this.machine = m;
		this.increase = perfInc;
		this.costInc = costInc;
	}

	@Override
	public void start() {
		//1. unassign employees
		
		//2. set machine to inupgrade
	}

	@Override
	protected void finish() {
		//1. set machine to not in upgrade
		
		//2. reassign employees
	}
	
}

package sim.research.dev;

import sim.abstraction.CostFactor;

public abstract class Upgrade implements CostFactor{
	
	private int costs;
	
	private int duration;
	
	public abstract void start();
	
	public void simRound(){
		duration--;
		if(duration == 0)
			finish();
	}
	
	protected abstract void finish();
	
	public boolean isFinished(){
		return duration == 0;
	}
	
	public Upgrade(int duration, int costs) {
		this.duration = duration;
		this.costs = costs;
	}

	@Override
	public int getCosts() {
		return costs;
	}

}

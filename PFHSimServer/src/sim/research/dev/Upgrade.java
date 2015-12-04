package sim.research.dev;

import sim.abstraction.CostFactor;

public abstract class Upgrade implements CostFactor{
	
	private int costs;
	
	private int duration;
	private boolean finished;
	
	
	public Upgrade(int duration, int costs) {
		this.duration = duration;
		this.costs = costs;
		this.finished = false;
	}
	
	public final void start(){
		if(!finished)
			setup();
	}
	
	protected abstract void setup();
	
	public void simRound(){
		if(finished)
			return;
		
		duration--;
		if(duration == 0)
			shutdown();
	}
	
	protected abstract void finish();
	
	private void shutdown(){
		if(finished)
			return;
		finished = true;
		finish();
	}
	
	public boolean isFinished(){
		return finished;
	}

	@Override
	public int getCosts() {
		return costs;
	}

}

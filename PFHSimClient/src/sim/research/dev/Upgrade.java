package sim.research.dev;

import sim.abstraction.CostFactor;

public abstract class Upgrade<T> implements CostFactor{
	
	private int costs;
	
	private int duration;
	private int progress;
	private boolean finished;	
	
	Upgrade(int duration, int costs) {
		this.duration = duration;
		this.progress = duration;
		this.costs = costs;
		this.finished = false;
	}
	
	abstract T getUpgradeObject();
	
	public final void start(){
		if(!finished)
			setup();
	}
	
	protected abstract void setup();
	
	void simRound(){
		if(finished)
			return;
		
		progress--;
		if(progress == 0)
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
	
	public double getProgress(){
		return  (duration - progress)/(duration+0.0d);
	}

}

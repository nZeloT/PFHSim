package sim.research.dev;

import sim.warehouse.Warehouse;

public class ExtendWarehouse extends Upgrade {
	
	private static final int UPGRADE_COSTS = 1000;
	private static final int UPGRADE_DURATION = 2;
	
	private Warehouse w;
	
	public ExtendWarehouse(Warehouse w) {
		super(UPGRADE_DURATION, UPGRADE_COSTS);
		this.w = w;
	}

	@Override
	protected void setup() {
		
	}

	@Override
	protected void finish() {
		w.upgrade();
	}

}

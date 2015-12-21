package sim.research.dev;

import sim.warehouse.Warehouse;

public class ExtendWarehouse extends Upgrade<Warehouse> {
	
	public static final int UPGRADE_COSTS = 1000;
	public static final int UPGRADE_DURATION = 2;
	
	private Warehouse w;
	
	ExtendWarehouse(Warehouse w) {
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
	
	@Override Warehouse getUpgradeObject() {
		return w;
	}

}

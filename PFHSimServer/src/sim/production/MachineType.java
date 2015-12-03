package sim.production;

public enum MachineType {
	
	WOODWALL_MACHINE(25, 3, 1000, 10000, WallType.LIGHT_WEIGHT_CONSTRUCTION, WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS),
	BRICKWALL_MACHINE(20, 3, 1500, 15000, WallType.MASSIVE_LIGHT_CONSTRUCTION, WallType.MASSIVE_PLUS_CONSTUCTION),
	PANORAMA_WALL_MACHINE(5, 3, 1000, 17500, WallType.PANORAMA_WALL);
	

	private int output;
	private int requiredEmps;
	private int costs;
	private int price;
	private WallType[] walltypesToHandle;
	

	private MachineType(int output, int requiredEmps, int costs, int price, WallType... walltypesToHandle) {
		this.output = output;
		this.requiredEmps = requiredEmps;
		this.costs = costs;
		this.price = price;
		this.walltypesToHandle = walltypesToHandle;
	}
	
	public int getOutput() {
		return output;
	}
	public int getRequiredEmps() {
		return requiredEmps;
	}

	public int getCosts() {
		return costs;
	}

	public int getPrice() {
		return price;
	}
	
	public WallType[] getWalltypesToHandle() {
		return walltypesToHandle;
	}
	
}

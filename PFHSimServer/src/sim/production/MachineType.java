package sim.production;

public enum MachineType {
	
	WOODWALL_MACHINE(25, 3, 1000, 10000, 3, 3333, 1, 7, 0, 0, 0, WallType.LIGHT_WEIGHT_CONSTRUCTION, WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS),
	BRICKWALL_MACHINE(20, 3, 1500, 15000, 3, 5000, 1, 7, 0, 0, 0, WallType.MASSIVE_LIGHT_CONSTRUCTION, WallType.MASSIVE_PLUS_CONSTUCTION),
	PANORAMA_WALL_MACHINE(5, 3, 1000, 17500, 3, 6000, 1, 7, 0, 0, 0, WallType.PANORAMA_WALL);
	

	private int output;
	private int requiredEmps;
	private int costs;
	private int price;
	private WallType[] walltypesToHandle;
	
	private int possibleUpgrades;
	private int upgradeCosts;
	private int upgradeDuration;
	private double upgradePerfInc;
	private double upgradeQualInc;
	private int upgradeCostInc;
	private int upgradeEmpInc;
	

	private MachineType(int output, int requiredEmps, int costs, int price, 
			int possibleUpgrades, int upgradeCosts, int upgradeDuration, double upgradePerfInc,
			double upgradeQualInc, int upgradeCostInc, int upgradeEmpInc, WallType... walltypesToHandle) {
		this.output = output;
		this.requiredEmps = requiredEmps;
		this.costs = costs;
		this.price = price;
		this.walltypesToHandle = walltypesToHandle;
		
		this.possibleUpgrades = possibleUpgrades;
		this.upgradeCosts = upgradeCosts;
		this.upgradeDuration = upgradeDuration;
		this.upgradePerfInc = upgradePerfInc;
		this.upgradeQualInc = upgradeQualInc;
		this.upgradeCostInc = upgradeCostInc;
		this.upgradeEmpInc = upgradeEmpInc;
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
	
	public int getPossibleUpgrades() {
		return possibleUpgrades;
	}

	public int getUpgradeCosts() {
		return upgradeCosts;
	}

	public int getUpgradeDuration() {
		return upgradeDuration;
	}

	public double getUpgradePerfInc() {
		return upgradePerfInc;
	}

	public double getUpgradeQualInc() {
		return upgradeQualInc;
	}

	public int getUpgradeCostInc() {
		return upgradeCostInc;
	}

	public int getUpgradeEmpInc() {
		return upgradeEmpInc;
	}

	public WallType[] getWalltypesToHandle() {
		return walltypesToHandle;
	}
	
}

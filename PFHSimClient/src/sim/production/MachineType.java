package sim.production;

import sim.abstraction.GeneralType;

public enum MachineType implements GeneralType {
	
	WOODWALL_MACHINE     (25, 3, 1000, 10000, 1, 3, 3333, 1, 7, 1, 0, 0, WallType.LIGHT_WEIGHT_CONSTRUCTION, WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS),
	BRICKWALL_MACHINE    (20, 3, 1500, 15000, 1, 3, 5000, 1, 7, 2, 0, 0, WallType.MASSIVE_LIGHT_CONSTRUCTION, WallType.MASSIVE_PLUS_CONSTUCTION),
	PANORAMA_WALL_MACHINE(5,  3, 1000, 17500, 1, 3, 6000, 1, 7, 2, 0, 0, WallType.PANORAMA_WALL);
	
 
	private int basePerformance;
	private int baseRequiredEmps;
	private int baseCosts;
	private int price;
	private WallType[] walltypesToHandle;
	private int baseQualityFactor;
	
	private int possibleUpgrades;
	private int upgradeCosts;
	private int upgradeDuration;
	private int upgradePerfInc;
	private int upgradeQualInc;
	private int upgradeCostInc;
	private int upgradeEmpInc;
	

	private MachineType(int basePerformance, int baseRequiredEmps, int baseCosts, int price, int baseQualityFactor,
			int possibleUpgrades, int upgradeCosts, int upgradeDuration, int upgradePerfInc,
			int upgradeQualInc, int upgradeCostInc, int upgradeEmpInc, WallType... walltypesToHandle) {
		this.basePerformance = basePerformance;
		this.baseRequiredEmps = baseRequiredEmps;
		this.baseCosts = baseCosts;
		this.price = price;
		this.walltypesToHandle = walltypesToHandle;
		this.baseQualityFactor = baseQualityFactor;
		
		
		this.possibleUpgrades = possibleUpgrades;
		this.upgradeCosts = upgradeCosts;
		this.upgradeDuration = upgradeDuration;
		this.upgradePerfInc = upgradePerfInc;
		this.upgradeQualInc = upgradeQualInc;
		this.upgradeCostInc = upgradeCostInc;
		this.upgradeEmpInc = upgradeEmpInc;
	}

	public int getBaseQualityFactor() {
		return baseQualityFactor;
	}

	public int getBasePerformance() {
		return basePerformance;
	}
	public int getBaseRequiredEmps() {
		return baseRequiredEmps;
	}

	public int getBaseCosts() {
		return baseCosts;
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

	public int getUpgradePerfInc() {
		return upgradePerfInc;
	}

	public int getUpgradeQualInc() {
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

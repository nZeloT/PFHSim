package sim.hr;

public enum EmployeeType {
	
	HR(1000, 1, 100, 1, 1, 5000),
	PRODUCTION(800, 0, 0, 0, 0, 0),
	ASSEMBLER(1200, 0, 0, 0, 0, 0),
	PROCUREMENT(1500, 0, 0, 0, 0, 0),
	SALES(1500, 0, 0, 0, 0, 0),
	MARKET_RESEARCH(1000, 0, 0, 0, 0, 0),
	STORE_KEEPER(700, 0, 0, 0, 0, 0),
	ARCHITECT(1500, 1, 150, 1, 1, 10000);
	
	private int 	baseCost;
	
	private int 	possibleUpgrades;
	
	private int 	upgradeCostInc;
	private double 	upgradeSkillInc;
	private int		upgradeDuration;
	private int		upgradeCosts;
	
	private EmployeeType(int baseCost, int possibleUpgrades, int upgradeCostInc, double upgradeSkillInc, int upgradeDuration,
			int upgradeCosts) {
		this.baseCost = baseCost;
		this.possibleUpgrades = possibleUpgrades;
		this.upgradeCostInc = upgradeCostInc;
		this.upgradeSkillInc = upgradeSkillInc;
		this.upgradeDuration = upgradeDuration;
		this.upgradeCosts = upgradeCosts;
	}

	public int getBaseCost() {
		return baseCost;
	}
	
	public int getPossibleUpgrades() {
		return possibleUpgrades;
	}

	public int getUpgradeCostInc() {
		return upgradeCostInc;
	}

	public double getUpgradeSkillInc() {
		return upgradeSkillInc;
	}

	public int getUpgradeDuration() {
		return upgradeDuration;
	}

	public int getUpgradeCosts() {
		return upgradeCosts;
	}
	
}

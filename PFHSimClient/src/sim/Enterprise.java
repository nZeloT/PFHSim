package sim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import sim.abstraction.Tupel;
import sim.bank.BankAccount;
import sim.bank.BankException;
import sim.hr.Department;
import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.hr.HR;
import sim.hr.HRException;
import sim.hr.WrongEmployeeTypeException;
import sim.procurement.Procurement;
import sim.procurement.Resource;
import sim.procurement.ResourceMarket;
import sim.procurement.ResourceMarketException;
import sim.procurement.ResourceType;
import sim.production.Machine;
import sim.production.MachineType;
import sim.production.PFHouse;
import sim.production.PFHouseType;
import sim.production.ProductionHouse;
import sim.production.Wall;
import sim.production.WallType;
import sim.research.dev.ExtendWarehouse;
import sim.research.dev.UpgradeException;
import sim.research.dev.UpgradeProcessor;
import sim.sales.Sales;
import sim.simulation.sales.Offer;
import sim.warehouse.Warehouse;
import sim.warehouse.WarehouseException;

public class Enterprise {

	private static final int START_CASH = 1000000;

	private BankAccount bank;

	private ResourceMarket market;

	private List<PFHouse> housesInConstruction;
	private List<PFHouseType> researchedHouseTypes;

	private UpgradeProcessor upgrades;

	private HR hr;
	private Sales sales;
	private Procurement procurement;
	private Department marketResearch;
	private Warehouse warehouse;
	private ProductionHouse production;
	
	private HashMap<PFHouseType, Integer> perRoundBuildAmounts;

	public Enterprise(ResourceMarket market) {
		bank = new BankAccount(START_CASH);

		this.market = market;

		housesInConstruction = new ArrayList<>();
		researchedHouseTypes = new ArrayList<>();
		researchedHouseTypes.add(PFHouseType.BUNGALOW);
		perRoundBuildAmounts = new HashMap<>();

		upgrades = new UpgradeProcessor();

		production = new ProductionHouse();
		sales = new Sales();
		procurement = new Procurement();
		marketResearch = new Department(EmployeeType.MARKET_RESEARCH);
		hr = new HR();

		Employee hrGuy = hr.hire(EmployeeType.HR);

		// first assign an HR guy to produce some HR capacity ;)
		hrGuy.assignWorkplace(hr);

		Employee[] storeKeeper = hr.hire(EmployeeType.STORE_KEEPER, 3);
		hr.hire(EmployeeType.SALES).assignWorkplace(sales);
		hr.hire(EmployeeType.PROCUREMENT).assignWorkplace(procurement);
		hr.hire(EmployeeType.MARKET_RESEARCH).assignWorkplace(marketResearch);
		hr.hire(EmployeeType.ARCHITECT);

		try {
			warehouse = new Warehouse(storeKeeper);
		} catch (HRException e) {
			e.printStackTrace();
		} catch (WrongEmployeeTypeException e) {
			e.printStackTrace();
		}
		
		this.setWallQuality();
	}

	/**
	 * Method to simulate one time-step for the enterprise
	 */
	public List<EnterpriseException> doSimulationStep(List<Offer> soldOffer) {
		List<EnterpriseException> msgStore = new ArrayList<>();
		
		//reset the per round build amounts hashmap
		perRoundBuildAmounts.clear();


		// process results from buyer's market-simulation.
		if (soldOffer.size() != sales.getOfferCount()) // this is for safety
			// only
			msgStore.add(new EnterpriseException(this, "Size of sold amount items and number of offers differs",
					ExceptionCategorie.PROGRAMMING_ERROR));
		else {
			for (Offer offer : soldOffer) {
				int soldAmount = 0;
				int failedAmount = 0;
				for (int i = 0; i < offer.getNumberOfPurchases() && i < offer.getProductionLimit(); i++) {
					try {
						startPFHouseProduction(offer, hr.getUnassignedEmployees(EmployeeType.ASSEMBLER,
								offer.getHousetype().getEmployeeCount()));
						soldAmount++;
					} catch (EnterpriseException e) {
						e.printStackTrace();
						msgStore.add(e);
						failedAmount++;
					}
				}
				if(failedAmount > 0)
					msgStore.add(new EnterpriseException(this, "The production of " + failedAmount + " " + offer.getHousetype() + " could not be started!", ExceptionCategorie.WARNING));
				if(soldAmount > 0)
					msgStore.add(new EnterpriseException(this, "The production of " + soldAmount + " " + offer.getHousetype() + " was started!",
						ExceptionCategorie.INFO));
			}
		}

		int finished = 0;
		for (int i = 0; i < housesInConstruction.size(); i++) {
			PFHouse h = housesInConstruction.get(i);
			h.processConstruction();

			if (h.isFinished()) {
				finished ++;
				PFHouse house = housesInConstruction.remove(i--);
				
				int amount = perRoundBuildAmounts.get(house.getType());
				amount ++;
				perRoundBuildAmounts.put(house.getType(), amount);

				try {
					bank.deposit(h.getPrice());
				} catch (BankException e) {
					msgStore.add(e);
				}
				// employee costs are handled through hr;
				// resources and walls did already cost
			}
		}
		
		if(finished > 0)
			msgStore.add(new EnterpriseException(this, finished + " have been build!", ExceptionCategorie.INFO));


		// process machine production
		List<EnterpriseException> productionErrors = production.processProduction(warehouse);
		msgStore.addAll(productionErrors);

		// Handle the upgrade progress
		upgrades.processUpgrades(this); // upgrades cost only once at the
		// beginning

		// handle more cash flow things
		try {
			bank.charge(hr.getOverallEmployeeCosts());
		} catch (BankException e) {
			msgStore.add(e);
		} // this makes sure we also pay for unassigned employees ;)
		try {
			bank.charge(production.getCosts());
		} catch (BankException e) {
			msgStore.add(e);
		}
		try {
			bank.charge(production.getMachineCosts());
		} catch (BankException e) {
			msgStore.add(e);
		}
		try {
			bank.charge(warehouse.getCosts());
		} catch (BankException e) {
			msgStore.add(e);
		}

		try {
			bank.simStep();
		} catch (BankException e) {
			msgStore.add(e);
		}

		msgStore.add(new EnterpriseException(this, "The saldo for this round is: " + bank.getSaldo(),
				ExceptionCategorie.INFO));

		// Reset number of purchases for the next simulation step.
		sales.resetOffers();


		// Set the new wall and offer qualities based on the average machine
		// quality and walltype-qualities
		this.setWallQuality();
		sales.setOfferQuality();
		
		return msgStore;
	}

	/**
	 * This can fail when: -Not enough space in the warehouse -Not enough
	 * resources on the market -Not enough money
	 * 
	 * @param type
	 *            Resource type to buy
	 * @param amount
	 *            Amount of the resource to buy
	 * @return if the order was successful
	 * @throws ResourceMarketException
	 *             for negative/zero amount
	 * @throws EnterpriseException
	 *             Not enough space in the warehouse or not enough Money
	 */
	public void buyResources(ResourceType type, int amount)
			throws BankException, ResourceMarketException, HRException, WarehouseException {
		if (procurement.getEmployeeCount() == 0) // this should never happen;
			// just for safety
			throw new HRException(this, "Procurement has no employees assigned; cannot buy on market.",
					ExceptionCategorie.PROGRAMMING_ERROR);

		int price = amount * market.getPrice(type);

		if (!warehouse.isStoreable(type, amount))
			throw new WarehouseException(this, "Could not store the requested amount of resources in the Warehouse!",
					ExceptionCategorie.ERROR);

		if (!bank.canBeCharged(price))
			throw new BankException(this, "Could not charge the bank for " + price, ExceptionCategorie.ERROR);

		// the following should now work without exception
		bank.charge(price); // we already checked whether we can charge the bank
		// for it
		Resource[] resources = market.buyResources(type, amount);
		warehouse.storeResource(resources); // we already checked that we can
		// store the required amount
	}

	/**
	 * Creation of a new house-building-object.
	 * 
	 * @param type
	 *            PFHouseType to build.
	 * @param employees
	 *            Employees assigned to building the PFHouse need to be
	 *            provided.
	 * @param price
	 *            A price for selling the house must be provided.
	 * 
	 * @throws EnterpriseException
	 *             This exception is thrown when there are not enough materials
	 *             in your storage required for building the given type of a
	 *             PFHouse.
	 * 
	 * @return This method returns a pre-factored-house-object as a result if
	 *         the given parameters are correct and enough materials are in the
	 *         warehouse.
	 * 
	 */
	public void startPFHouseProduction(Offer offer, Employee[] pEmployees)
			throws EnterpriseException, WarehouseException, HRException {

		// ------------------------------------------------------------------------------------------CONDITIONS-CHECK:START
		if (offer == null && pEmployees != null)
			throw new EnterpriseException(this, "Invalid paramteres passed!", ExceptionCategorie.PROGRAMMING_ERROR);

		// How much walls are needed for pfhousetype?
		WallType[] wt = offer.getHousetype().getRequiredWallTypes();
		int[] wc = offer.getHousetype().getWallCounts();

		Tupel<WallType>[] tupel = offer.getWalltype();

		int[] taken = new int[wt.length];

		boolean generalWallRequired = false;
		int generalWallIndex = 0;
		// Search for the essential walls in the given walls from the offer.
		for (int i = 0; i < wt.length; i++) {
			// If the currently (i) essential wall is not a GENERAL wall, check
			// whether there are enough walls given in the offer...
			if (wt[i] != WallType.GENERAL) {

				int tmp = 0;
				for (int j = 0; j < tupel.length; j++) {
					if (tupel[j].type == wt[i]) {
						tmp = tupel[j].count;
						taken[i] = wc[i];
					}
				}

				if (tmp < wc[i]) {
					// ...otherwise terminate the method.
					throw new EnterpriseException(this, "The given walls are not valid for creating a PFHouse!",
							ExceptionCategorie.ERROR);
				}
			} else {
				generalWallRequired = true;
				generalWallIndex = i;
			}
		}

		// Evaluate whether there are still enough walls in the offer for
		// meeting the need for GENERAL-walls
		int remainingWallCounts = 0;
		if (generalWallRequired) {
			for (int i = 0; i < tupel.length; i++) {
				if (i<taken.length) {
					remainingWallCounts += tupel[i].count - taken[i];
				} else {
					remainingWallCounts += tupel[i].count;
				}
			}
		}
		if (remainingWallCounts < wc[generalWallIndex]) {
			throw new EnterpriseException(this, "Not enough walls of type 'GENERAL'!", ExceptionCategorie.ERROR);
		}

		// needed walls are in the warehouse.
		for (int i = 0; i < tupel.length; i++) {
			if (!warehouse.isInStorage(tupel[i].type, tupel[i].count)) {
				throw new WarehouseException(this, "Not enough walls in your warehouse!", ExceptionCategorie.ERROR);
			}
		}

		// How much resources are needed for pfhousetype?
		ResourceType[] rt = offer.getHousetype().getRequiredResourceTypes();
		int[] rc = offer.getHousetype().getResourceCounts();

		// Check whether the needed resources are in the warehouse.
		for (int i = 0; i < rt.length; i++) {
			if (!warehouse.isInStorage(rt[i], rc[i])) {
				throw new WarehouseException(this, "Not enough resources in your warehouse!", ExceptionCategorie.ERROR);
			}
		}
		// enough resources in warehouse

		// Check whether the needed employees are provided.
		// If a not required employee is provided, he/she will be removed
		// from the list of employees needed for the house-building-job.
		List<Employee> employees = null;
		if (pEmployees != null) {
			employees = Arrays.asList(pEmployees);
			for (int i = 0; i < employees.size(); i++) {
				if (employees.get(i).getType() != EmployeeType.ASSEMBLER) {
					employees.remove(i);
					i--;
				}
			}
			if (employees.size() < offer.getHousetype().getEmployeeCount()) {
				throw new HRException(this, "Not enough employees to build this house!", ExceptionCategorie.ERROR);
			}
		} else {
			throw new HRException(this, "Not enough employees to build this house!", ExceptionCategorie.ERROR);
		}
		// enough employees

		// ------------------------------------------------------------------------------------------CONDITIONS-CHECK:END

		// - 1. Removing the walls from the warehouse.
		// No more check for "null" as return value from warehouse
		// required, because this has already been
		// checked above.
		// - 2. Calculation of a houses production-costs
		int costs = 0;
		Wall[] tmp_wall = null;
		for (int i = 0; i < tupel.length; i++) {
			tmp_wall = warehouse.removeWalls(tupel[i].type, tupel[i].count);
			for (int j = 0; j < tmp_wall.length; j++) {
				costs += tmp_wall[j].getCosts();
			}
		}

		// - 1. Removing the required resources and walls from the warehouse.
		// No more check for "null" as return value from warehouse
		// required, because this has already been
		// checked above.
		// - 2. Calculation of a houses production-costs
		Resource[] tmp_resource = null;
		for (int i = 0; i < wt.length; i++) {
			tmp_resource = warehouse.removeResource(rt[i], rc[i]);
			for (int j = 0; j < tmp_wall.length; j++) {
				costs += tmp_resource[j].getCosts();
			}
		}

		// create a new house and append it to the array-list saving the houses
		// which are in construction.
		PFHouse pfh = new PFHouse(offer.getPrice(), this.calculateVariableCosts(offer), offer.getHousetype(),
				employees);

		housesInConstruction.add(pfh);

	}

	/**
	 * This Method calculates all costs which can't be related to a single house
	 * building project including: warehouse (including storage keepers) costs
	 * architect and architect project costs employees: market_reasearch,
	 * hr_manager, salesman, procurement manager
	 * 
	 * @returns the actual FixedCosts
	 * 
	 */
	// TODO: this is only used in one test case? can be removed?
	public int calculateFixedCosts() {
		int sum = 0;
		sum += warehouse.getOverallCosts();
		sum += sales.getEmployeeCosts();
		sum += procurement.getEmployeeCosts();
		sum += marketResearch.getEmployeeCosts();
		sum += hr.getEmployeeCosts();
		// TODO add Project Costs..
		return sum;
	}

	// TODO: this method is only used in one test case. should be removed?
	public void checkRequirementsforOffer(PFHouseType housetype, int amount,
			@SuppressWarnings("unchecked") Tupel<WallType>... selectedWalltype) throws EnterpriseException {

		WallType[] walltypes = housetype.getRequiredWallTypes();
		int[] wallcount = housetype.getWallCounts();

		for (int i = 0; i < wallcount.length; i++) {
			if (selectedWalltype[0].type == walltypes[i] || walltypes[i] == WallType.PANORAMA_WALL) {
				if (!warehouse.isInStorage(walltypes[i], wallcount[i] * amount)) {
					throw new WarehouseException(this, "Not Enough Walls to create a Offer for this Type!",
							ExceptionCategorie.ERROR);
				}
			}
		}

		if (housetype.getEmployeeCount() * amount > hr.getAmount(EmployeeType.ASSEMBLER)) {
			throw new HRException(this, "Not enough Employees to build the houses", ExceptionCategorie.ERROR);
		}

		ResourceType[] resourcetypes = housetype.getRequiredResourceTypes();
		int[] resourcecount = housetype.getResourceCounts();
		for (int i = 0; i < wallcount.length; i++) {
			if (!warehouse.isInStorage(resourcetypes[i], resourcecount[i] * amount)) {
				throw new WarehouseException(this, "Not enough Resources to build an offer!", ExceptionCategorie.ERROR);
			}
		}
	}

	public int getMaxProducibleHouses(Offer o) {
		int maximum = 0;

		// check walls
		Tupel<WallType>[] walltypes = o.getWalltype();

		boolean first = true;
		for (int i = 0; i < walltypes.length; i++) {
			if (walltypes[i].count > 0) {
				int max = warehouse.getStoredAmount(walltypes[i].type) / walltypes[i].count;
				if (first) {
					maximum = max;
					first = false;
				}
				maximum = Math.min(max, maximum);
			}
		}

		// check resources
		ResourceType[] resourcetypes = o.getHousetype().getRequiredResourceTypes();
		int[] resourcecount = o.getHousetype().getResourceCounts();

		for (int i = 0; i < resourcetypes.length; i++) {
			int max = warehouse.getStoredAmount(resourcetypes[i]) / resourcecount[i];
			maximum = Math.min(max, maximum);
		}

		// check employees
		int max = hr.getAmount(EmployeeType.ASSEMBLER) / o.getHousetype().getEmployeeCount();

		maximum = Math.min(max, maximum);
		return maximum;
	}

	public int getMaxProducibleHouses(PFHouseType pfhouse) {
		int maximum = 0;

		// check walls
		WallType[] walltypes = pfhouse.getRequiredWallTypes();
		int[] wallcount = pfhouse.getWallCounts();

		boolean first = true;
		for (int i = 0; i < wallcount.length; i++) {
			int max = warehouse.getStoredAmount(walltypes[i]) / wallcount[i];
			if (first) {
				maximum = max;
				first = false;
			}
			maximum = Math.min(max, maximum);
		}

		// check resources
		ResourceType[] resourcetypes = pfhouse.getRequiredResourceTypes();
		int[] resourcecount = pfhouse.getResourceCounts();

		for (int i = 0; i < resourcetypes.length; i++) {
			int max = warehouse.getStoredAmount(resourcetypes[i]) / resourcecount[i];
			maximum = Math.min(max, maximum);
		}

		// check employees
		int max = hr.getAmount(EmployeeType.ASSEMBLER) / pfhouse.getEmployeeCount();

		maximum = Math.min(max, maximum);
		return maximum;
	}

	/**
	 * This Method doens't check if everything is available!!!!!!!! Make sure
	 * this is the case before (class Enterprise Method
	 * checkRequirementsforOffer) returns the variable costs for the offer This
	 * includes: Wall costs(machine, employee work, resources) Assembler
	 * cost(for building the house) additional resources
	 */
	public int calculateVariableCosts(Offer offer) {
		Tupel<WallType>[] walltypes = offer.getWalltype();
		int costs = 0;
		for (int i = 0; i < walltypes.length; i++) {
			costs += warehouse.calculateAvgPrice(walltypes[i].type) * walltypes[i].count;
			// walls,
			// resources
			// (see
			// Constructor)
			// available
			// so
			// no
			// rechecks
			// again
		}

		ResourceType[] resourcetypes = offer.getHousetype().getRequiredResourceTypes();
		int[] resourcecount = offer.getHousetype().getResourceCounts();
		for (int i = 0; i < resourcetypes.length; i++) {
			costs += warehouse.calculateAvgPrice(resourcetypes[i]) * resourcecount[i];
		}

		int duration = offer.getHousetype().getConstructionDuration();

		costs += EmployeeType.ASSEMBLER.getBaseCost() * offer.getHousetype().getEmployeeCount() * duration;

		return costs;
	}

	public void buyMachine(MachineType type) throws BankException {
		bank.charge(type.getPrice()); // method terminates when there was an
		// error charching the bank
		production.buyMachine(type);
	}

	public void sellMachine(Machine m) throws BankException, EnterpriseException{
		int sell = m.getType().getPrice() + m.getUpgradeCount() * m.getType().getUpgradeCosts();
		sell *= 0.66d;

		if(production.sellMachine(m))
			bank.deposit(sell);
		else
			throw new EnterpriseException(this, "Could not sell machine!", ExceptionCategorie.PROGRAMMING_ERROR);

	}

	public void startEmployeeTraining(Employee e) throws UpgradeException, BankException {
		if (bank.canBeCharged(e.getType().getUpgradeCosts())) {

			upgrades.startEmployeeTraining(e); // method terminates when there
			// was an error starting the emp
			// training
			bank.charge(e.getType().getUpgradeCosts()); // before charching the
			// bank; the check at
			// the beginning made
			// sure the bank charge won't throw an error.
		}
	}

	public void startMachineUpgrade(Machine m) throws UpgradeException, BankException {
		if (bank.canBeCharged(m.getType().getUpgradeCosts())) {
			upgrades.startMachineUpgrade(m, hr);
			bank.charge(m.getType().getUpgradeCosts());
		}
	}

	public void startWarehouseExtension() throws UpgradeException, BankException {
		if (bank.canBeCharged(ExtendWarehouse.UPGRADE_COSTS)) {
			upgrades.startWarehouseExtension(warehouse);
			bank.charge(ExtendWarehouse.UPGRADE_COSTS);
		}
	}

	public void startResearchProject(PFHouseType type) throws UpgradeException, BankException, EnterpriseException {
		if (bank.canBeCharged(type.getResearchCosts())) {
			Employee arch = hr.getUnassignedEmployee(EmployeeType.ARCHITECT);
			if (arch == null) {
				throw new EnterpriseException(this, "No Architect available in HR Deparment", ExceptionCategorie.ERROR);
			}
			upgrades.startResearchProject(type, arch);
			bank.charge(type.getResearchCosts());
		}
	}

	public void setWallQuality() {

		List<Machine> machines = this.production.getMachines();

		WallType[] t = WallType.values();
		
		for (WallType wallType : t) {
			
			wallType.setQualityFactor(wallType.getInitialQualityFactor());
			
			for (int i = 0; i < machines.size(); i++) {
				int ctr = 0;
				int qual = 0;
				if(machines.get(i).getProductionType() == wallType) {
					qual += machines.get(i).getQuality() * wallType.getInitialQualityFactor();
					ctr++;
				}
				if (ctr>0) {
					qual /= ctr;
					System.out.println("" + wallType.toString() + qual);
					wallType.setQualityFactor(qual);
				}
				
			}
			
		}

	}

	public List<Employee> autoAssignEmployees(Employee... emps) {
		for (Employee e : emps) {
			switch (e.getType()) {
			case ARCHITECT:
				// no auto assign possible; needs to be assigned to a research
				// project
				break;
			case ASSEMBLER:
				// no auto assign possible; auto assigned during production as
				// required
				break;
			case HR:
				hr.assignEmployee(e);
				break;
			case MARKET_RESEARCH:
				marketResearch.assignEmployee(e);
				break;
			case PROCUREMENT:
				procurement.assignEmployee(e);
				break;
			case PRODUCTION:
				// no auto assign possible; user needs to decide which machine
				// to assign them to
				break;
			case SALES:
				sales.assignEmployee(e);
				break;
			case STORE_KEEPER:
				warehouse.assignEmployee(e);
				break;

			default:
				break;
			}
		}

		List<Employee> unassigned = new ArrayList<>();
		for (Employee e : emps) {
			if (!e.isAssigned())
				unassigned.add(e);
		}
		return unassigned;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public ProductionHouse getProductionHouse() {
		return production;
	}

	public List<PFHouse> getHousesInConstruction() {
		return housesInConstruction;
	}

	public List<PFHouseType> getResearchedHouseTypes() {
		return researchedHouseTypes;
	}

	public HR getHR() {
		return hr;
	}

	public ResourceMarket getMarket() {
		return market;
	}

	public Sales getSales() {
		return sales;
	}

	public BankAccount getBankAccount() {
		return bank;
	}
	
	public HashMap<PFHouseType, Integer> getPerRoundBuildAmounts() {
		return perRoundBuildAmounts;
	}
}

package sim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sim.abstraction.Tupel;
import sim.hr.Department;
import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.hr.HR;
import sim.hr.WrongEmployeeTypeException;
import sim.procurement.Resource;
import sim.procurement.ResourceMarket;
import sim.procurement.ResourceMarketException;
import sim.procurement.ResourceType;
import sim.production.Machine;
import sim.production.MachineException;
import sim.production.MachineType;
import sim.production.PFHouse;
import sim.production.PFHouseType;
import sim.production.ProductionHouse;
import sim.production.Wall;
import sim.production.WallType;
import sim.research.dev.UpgradeProcessor;
import sim.simulation.sales.Offer;
import sim.warehouse.Warehouse;
import sim.warehouse.WarehouseException;

public class Enterprise {

	private static final int START_CASH = 100000;

	private int cash;
	private int saldo;

	private Warehouse warehouse;
	private ProductionHouse production;

	private List<PFHouse> housesInConstruction;
	private List<PFHouseType> researchedHouseTypes;

	// This list represents a catalogue of pfhouse-offers, which
	// specify the walls for a house-type as well as the price.
	private List<Offer> offers;

	private UpgradeProcessor upgrades;

	// Employee management for warehouse and production goes in the distinct
	// classes
	private HR hr;
	private Department sales;
	private Department procurement;
	private Department marketResearch;
	// private ResearchProject designthinking; //architect

	public Enterprise() {
		cash = START_CASH;

		housesInConstruction = new ArrayList<>();
		researchedHouseTypes = new ArrayList<>();

		upgrades = new UpgradeProcessor();

		offers = new ArrayList<>();

		production = new ProductionHouse();
		sales = new Department(EmployeeType.SALES);
		procurement = new Department(EmployeeType.PROCUREMENT);
		marketResearch = new Department(EmployeeType.MARKET_RESEARCH);
		hr = new HR();

		Employee hrGuy = hr.hire(EmployeeType.HR);

		// first assign an HR guy to produce some HR capacity ;)
		hrGuy.assignWorkplace(hr);

		Employee[] storeKeeper = hr.hire(EmployeeType.STORE_KEEPER, 3);
		hr.hire(EmployeeType.SALES).assignWorkplace(sales);
		hr.hire(EmployeeType.PROCUREMENT).assignWorkplace(procurement);
		hr.hire(EmployeeType.MARKET_RESEARCH).assignWorkplace(marketResearch);

		try {
			warehouse = new Warehouse(storeKeeper);
		} catch (WarehouseException e) {
			e.printStackTrace();
		} catch (WrongEmployeeTypeException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to simulate one time-step for the enterprise
	 */
	public List<Exception> doSimulationStep(int[] soldAmounts) {
		int oldCash = cash;
		List<Exception> errorStore = new ArrayList<>();

		// process results from buyer's market-simulation.
		if (soldAmounts.length != offers.size())
			errorStore.add(new EnterpriseException("Size of sold amount items and number of offers differs"));
		else {
			for (Offer offer : offers) {
				try {
					startPFHouseProduction(offer,
							hr.getUnassignedEmployees(EmployeeType.ASSEMBLER, offer.getHousetype().getEmployeeCount()));
				} catch (EnterpriseException e) {
					e.printStackTrace();
				}
			}
		}

		for (int i = 0; i < housesInConstruction.size(); i++) {
			PFHouse h = housesInConstruction.get(i);
			h.processConstruction();

			if (h.isFinished()) {
				housesInConstruction.remove(i--);

				cash += h.getPrice(); // employee costs are handled through hr;
										// resources and walls did already cost
			}
		}

		// process machine production
		List<MachineException> productionErrors = production.processProduction(warehouse);
		errorStore.addAll(productionErrors);

		// Handle the upgrade progress
		upgrades.processUpgrades(this); // upgrades cost only once at the
										// beginning

		// handle more cash flow things
		cash -= hr.getOverallEmployeeCosts(); // this makes sure we also pay for
												// unassigned employees ;)
		cash -= production.getCosts();
		cash -= production.getMachineCosts();
		cash -= warehouse.getCosts();

		// calculate the cash difference;
		saldo = cash - oldCash;

		return errorStore;
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
	public void buyResources(ResourceMarket market, ResourceType type, int amount) throws EnterpriseException, ResourceMarketException {
		int price = amount * market.getPrice(type);
		if (price > cash) {
			throw new EnterpriseException("Not enough Money to buy " + amount + " Resources!");
		}

		if (warehouse.isInStorage(type, amount)) {
			Resource[] resources = market.buyResources(type, amount);
			cash -= price;
			if (resources != null) {
				if (!warehouse.storeResource(resources)) {
					throw new EnterpriseException("Not enough space in your warehouse!");
				}
			}
		}
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
	public void startPFHouseProduction(Offer offer, Employee[] pEmployees) throws EnterpriseException {

		// ------------------------------------------------------------------------------------------CONDITIONS-CHECK:START
		if (offer == null)
			throw new EnterpriseException("Invalid offer given!");

		
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
					throw new EnterpriseException("The given walls are not valid for creating a PFHouse!");
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
				remainingWallCounts += tupel[i].count - taken[i];
			}
		}
		if (remainingWallCounts < wc[generalWallIndex]) {
			throw new EnterpriseException("Not enough walls of type 'GENERAL'!");
		}

		// needed walls are in the warehouse.
		for (int i = 0; i < tupel.length; i++) {
			if (!warehouse.isInStorage(tupel[i].type, taken[i])) {
				throw new EnterpriseException("Not enough walls in your warehouse!");
			}
		}

		// How much resources are needed for pfhousetype?
		ResourceType[] rt = offer.getHousetype().getRequiredResourceTypes();
		int[] rc = offer.getHousetype().getResourceCounts();

		// Check whether the needed resources are in the warehouse.
		for (int i = 0; i < rt.length; i++) {
			if (!warehouse.isInStorage(rt[i], rc[i])) {
				throw new EnterpriseException("Not enough resources in your warehouse!");
			}
		}
		// enough resources in warehouse

		// Check whether the needed employees are provided.
		// If a not required employee is provided, he/she will be removed
		// from the list of employees needed for the house-building-job.
		List<Employee> employees = Arrays.asList(pEmployees);
		for (int i = 0; i < employees.size(); i++) {
			if (employees.get(i).getType() != EmployeeType.ASSEMBLER) {
				employees.remove(i);
				i--;
			}
		}
		if (employees.size() < offer.getHousetype().getEmployeeCount()) {
			throw new EnterpriseException("Not enough employees to build this house!");
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
		for (int i = 0; i < wt.length; i++) {
			tmp_wall = warehouse.removeWalls(tupel[i].type, taken[i]);
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

	public void checkRequirementsforOffer(PFHouseType housetype, int amount, Tupel<WallType>... selectedWalltype)
			throws EnterpriseException {

		WallType[] walltypes = housetype.getRequiredWallTypes();
		int[] wallcount = housetype.getWallCounts();

		for (int i = 0; i < wallcount.length; i++) {
			if (selectedWalltype[0].type == walltypes[i] || walltypes[i] == WallType.PANORAMA_WALL) {
				if (!warehouse.isInStorage(walltypes[i], wallcount[i] * amount)) {
					throw new EnterpriseException("Not Enough Walls to create a Offer for this Type!");
				}
			}
		}

		if (housetype.getEmployeeCount() < hr.getMaxCapacity(EmployeeType.ASSEMBLER)) {
			throw new EnterpriseException("Not enough Employees to build the houses");
		}

		ResourceType[] resourcetypes = housetype.getRequiredResourceTypes();
		int[] resourcecount = housetype.getResourceCounts();
		for (int i = 0; i < wallcount.length; i++) {
			if (!warehouse.isInStorage(resourcetypes[i], resourcecount[i] * amount)) {
				throw new EnterpriseException("Not enough Resources to build an offer!");
			}
		}
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
		int max = hr.getMaxCapacity(EmployeeType.ASSEMBLER) / pfhouse.getEmployeeCount();
		
		
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

	public void buyMachine(MachineType type) throws EnterpriseException {
		if (cash < type.getPrice()) {
			throw new EnterpriseException("Not enough Money!");
		}
		cash -= production.buyMachine(type);
	}

	public boolean startEmployeeTraining(Employee e) {
		int costs = upgrades.startEmployeeTraining(e);
		if (costs > 0)
			cash -= costs;
		return costs >= 0;
	}

	public boolean startMachineUpgrade(Machine m, HR hr) {
		int costs = upgrades.startMachineUpgrade(m, hr);
		if (costs > 0)
			cash -= costs;
		return costs >= 0;
	}

	public boolean startWarehouseExtension(Warehouse w) {
		int costs = upgrades.startWarehouseExtension(w);
		if (costs > 0)
			cash -= 0;
		return costs >= 0;
	}

	public boolean startResearchProject(PFHouseType type, Employee arch) {
		int costs = upgrades.startResearchProject(type, arch);
		if (costs > 0)
			cash -= costs;
		return costs >= 0;
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

	public int getCash() {
		return cash;
	}

	public int getSaldo() {
		return saldo;
	}

	public List<Offer> getOffers() {
		return offers;
	}

	public void addOffer(Offer offer) {
		this.offers.add(offer);
	}
}

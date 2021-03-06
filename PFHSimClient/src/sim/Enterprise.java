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
import sim.production.MachineSuccessException;
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
import sim.warehouse.MissingResourceException;
import sim.warehouse.MissingWallsException;
import sim.warehouse.Warehouse;
import sim.warehouse.WarehouseException;

public class Enterprise {

	private static final int START_CASH = 1000000;

	private BankAccount bank;

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

		housesInConstruction = new ArrayList<>();
		researchedHouseTypes = new ArrayList<>();
		researchedHouseTypes.add(PFHouseType.BUNGALOW);
		perRoundBuildAmounts = new HashMap<>();

		upgrades = new UpgradeProcessor();

		production = new ProductionHouse();
		sales = new Sales();
		procurement = new Procurement(market);
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

		production.setWallQuality();
	}

	/**
	 * Method to simulate one time-step for the enterprise
	 */
	public List<EnterpriseException> doSimulationStep(List<Offer> soldOffer) {
		List<EnterpriseException> msgStore = new ArrayList<>();

		//reset the per round build amounts hashmap
		perRoundBuildAmounts.clear();

		//start the house production
		msgStore.addAll(startHouseProductions(soldOffer));

		//process and finish house production
		msgStore.addAll(finishHouseProductions());

		int[] missingRes = new int[ResourceType.values().length];
		// process machine production
		List<EnterpriseException> machineMsg = production.processProduction(warehouse);
		int[] producedWalls = new int[WallType.values().length];
		for (EnterpriseException ex : machineMsg) {
			if(ex instanceof MissingResourceException){
				MissingResourceException miss = (MissingResourceException)ex;
				missingRes[ResourceType.toInt(miss.getType())] += miss.getAmount();
			}
			else if(ex instanceof MachineSuccessException){
				MachineSuccessException succ = (MachineSuccessException)ex;
				producedWalls[WallType.toInt(succ.getType())] += succ.getProduction();
			}
			else
				msgStore.add(ex);
		}


		for (int i = 0; i < missingRes.length; i++)
			if(missingRes[i] > 0)
				msgStore.add(new EnterpriseException(this, "Missing " + missingRes[i] + " " + ResourceType.fromInt(i) + " to complete demand!", ExceptionCategorie.WARNING));

		for (int i = 0; i < producedWalls.length; i++)
			if(producedWalls[i] > 0)
				msgStore.add(new EnterpriseException(this, "Produced " + producedWalls[i] + " " + WallType.fromInt(i), ExceptionCategorie.INFO));


		// Handle the upgrade progress
		upgrades.processUpgrades(this); // upgrades cost only once at the beginning

		// handle more cash flow things
		try {
			int payment = 0;
			payment += hr.getOverallEmployeeCosts(); // this makes sure we also pay for unassigned employees ;)
			payment += production.getCosts();
			payment += production.getMachineCosts();
			payment += warehouse.getCosts();
			
			bank.chargeCritical(payment);
			bank.simStep();
		} catch (BankException e) {
			msgStore.add(e);
		} 

		msgStore.add(new EnterpriseException(this, "The saldo for this round is: " + bank.getSaldo() + " �",
				ExceptionCategorie.INFO));

		// Reset number of purchases for the next simulation step.
		sales.resetOffers();

		// Set the new wall and offer qualities based on the average machine
		// quality and walltype-qualities
		production.setWallQuality();
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
		procurement.buyResources(type, amount, warehouse, bank);
	}
	
	private List<EnterpriseException> startHouseProductions(List<Offer> sellings){
		List<EnterpriseException> msgStore = new ArrayList<>();
		int[] missingRes = new int[ResourceType.values().length];

		// process results from buyer's market-simulation.
		if (sellings.size() != sales.getOfferCount()) // this is for safety
			// only
			msgStore.add(new EnterpriseException(this, "Size of sold amount items and number of offers differs",
					ExceptionCategorie.PROGRAMMING_ERROR));
		else {
			int[] soldAmounts = new int[PFHouseType.values().length];
			int[] failedAmounts = new int[PFHouseType.values().length];

			int missingEmps = 0;
			int[] missingWalls = new int[WallType.values().length];

			for (Offer offer : sellings) {
				for (int i = 0; i < offer.getNumberOfPurchases() && i < offer.getProductionLimit(); i++) {
					try {
						startPFHouseProduction(offer, 
								hr.getUnassignedEmployees(EmployeeType.ASSEMBLER,
										offer.getHousetype().getEmployeeCount())
								);
						soldAmounts[PFHouseType.toInt(offer.getHousetype())]++;
					} catch (MissingWallsException e) {
						failedAmounts[PFHouseType.toInt(offer.getHousetype())]++;
						missingWalls[WallType.toInt(e.getType())] += e.getAmount();
					}catch(MissingResourceException e){
						failedAmounts[PFHouseType.toInt(offer.getHousetype())]++;
						missingRes[ResourceType.toInt(e.getType())] += e.getAmount();
					}catch(HRException e){
						failedAmounts[PFHouseType.toInt(offer.getHousetype())]++;
						missingEmps += offer.getHousetype().getEmployeeCount();
					}catch(EnterpriseException e){
						//every other exception directly goes into msg log
						msgStore.add(e);
					}
				}
			}

			if(missingEmps > 0)
				msgStore.add(new EnterpriseException(this, "Missing " + missingEmps + " Assembler to complete demand!", ExceptionCategorie.WARNING));

			for (int i = 0; i < missingWalls.length; i++)
				if(missingWalls[i] > 0)
					msgStore.add(new EnterpriseException(this, "Missing " + missingWalls[i] + " " + WallType.fromInt(i) + " to complete demand!", ExceptionCategorie.WARNING));

			for (int i = 0; i < missingRes.length; i++)
				if(missingRes[i] > 0)
					msgStore.add(new EnterpriseException(this, "Missing " + missingRes[i] + " " + ResourceType.fromInt(i) + " to complete demand!", ExceptionCategorie.WARNING));
			
			for (int i = 0; i < failedAmounts.length; i++)
				if(failedAmounts[i] > 0)
					msgStore.add(new EnterpriseException(this, "Couldn't start building " + failedAmounts[i] + " " + PFHouseType.fromInt(i) + "!", ExceptionCategorie.WARNING));

			for (int i = 0; i < soldAmounts.length; i++)
				if(soldAmounts[i] > 0)
					msgStore.add(new EnterpriseException(this, "Started building " + soldAmounts[i] + " " + PFHouseType.fromInt(i) + "!", ExceptionCategorie.INFO));
			
		}
		
		return msgStore;
	}
	
	private List<EnterpriseException> finishHouseProductions(){
		List<EnterpriseException> msgStore = new ArrayList<>();
		
		int[] finished = new int[PFHouseType.values().length];
		for (int i = 0; i < housesInConstruction.size(); i++) {
			PFHouse h = housesInConstruction.get(i);
			h.processConstruction();

			if (h.isFinished()) {
				finished[PFHouseType.toInt(h.getType())] ++;
				housesInConstruction.remove(i--);

				try {
					bank.deposit(h.getPrice());
				} catch (BankException e) {
					msgStore.add(e);
				}
				// employee costs are handled through hr;
				// resources and walls did already cost
			}
		}

		for (int i = 0; i < finished.length; i++){
			if(finished[i] > 0)
				msgStore.add(new EnterpriseException(this, "Finished building " + finished[i] + " " + PFHouseType.fromInt(i) + "!", ExceptionCategorie.INFO));

			perRoundBuildAmounts.put(PFHouseType.fromInt(i), finished[i]);
		}
		
		return msgStore;
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
			throws EnterpriseException, MissingWallsException, MissingResourceException, HRException {

		// ------------------------------------------------------------------------------------------CONDITIONS-CHECK:START
		if (offer == null && pEmployees != null)
			throw new EnterpriseException(this, "Invalid paramteres passed!", ExceptionCategorie.PROGRAMMING_ERROR);

		// How much walls are needed for pfhousetype?
		WallType[] wt = offer.getHousetype().getRequiredWallTypes();
		int[] wc = offer.getHousetype().getWallCounts();

		Tupel<WallType>[] tupel = offer.getWalltype();

		int[] taken = new int[tupel.length];

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
		if (generalWallRequired) {
		if (remainingWallCounts < wc[generalWallIndex]) {
			throw new EnterpriseException(this, "Not enough walls of type 'GENERAL'!", ExceptionCategorie.ERROR);
		}
		}

		// needed walls are in the warehouse.
		for (int i = 0; i < tupel.length; i++) {
			if (!warehouse.isInStorage(tupel[i].type, tupel[i].count)) {
				throw new MissingWallsException(this, tupel[i].type, tupel[i].count, ExceptionCategorie.ERROR);
			}
		}

		// How much resources are needed for pfhousetype?
		ResourceType[] rt = offer.getHousetype().getRequiredResourceTypes();
		int[] rc = offer.getHousetype().getResourceCounts();

		// Check whether the needed resources are in the warehouse.
		for (int i = 0; i < rt.length; i++) {
			if (!warehouse.isInStorage(rt[i], rc[i])) {
				throw new MissingResourceException(this, rt[i], rc[i], ExceptionCategorie.ERROR);
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
		for (int i = 0; i < rt.length; i++) {
			tmp_resource = warehouse.removeResource(rt[i], rc[i]);
			for (int j = 0; j < tmp_resource.length; j++) {
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
		return sum;
	}

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
		return procurement.getMarket();
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

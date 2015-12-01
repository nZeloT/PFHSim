package sim;

import java.util.ArrayList;

import sim.abstraction.WrongEmployeeTypeException;
import sim.hr.Department;
import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.hr.HR;
import sim.procurement.Resource;
import sim.procurement.ResourceListItem;
import sim.procurement.ResourceMarket;
import sim.procurement.ResourceMarketException;
import sim.procurement.ResourceType;
import sim.production.PFHouse;
import sim.production.PFHouseType;
import sim.production.ProductionHouse;
import sim.production.Wall;
import sim.production.WallType;
import sim.research.dev.ResearchProject;
import sim.warehouse.Warehouse;
import sim.warehouse.WarehouseException;

public class Enterprise {
	
	private int cash;
	
	private Warehouse warehouse;
	private ProductionHouse production;
	
	private ArrayList<PFHouse> housesInConstruction;
	
	//Employee management for warehouse and production goes in the distinct classes
	private HR employeemgr;
	private Department sales;
	private Department procurement;
	private Department marketResearch;
	private ResearchProject designthinking; //architect

	

	
	
	public Enterprise() {
		housesInConstruction = new ArrayList<>();
		employeemgr = new HR();
		production = new ProductionHouse();
		try {
			warehouse = new Warehouse(9999999, 150,
					new Employee(EmployeeType.STORE_KEEPER), new Employee(EmployeeType.STORE_KEEPER),
							new Employee(EmployeeType.STORE_KEEPER), new Employee(EmployeeType.STORE_KEEPER),
							new Employee(EmployeeType.STORE_KEEPER));
		} catch (WarehouseException e) {
			e.printStackTrace();
		} catch (WrongEmployeeTypeException e) {
			e.printStackTrace();
		}
		cash = 0;
	
		employeemgr = new HR();
		
		//get the first employees
		employeemgr.hire(EmployeeType.SALES);
		employeemgr.hire(EmployeeType.PROCUREMENT);
		employeemgr.hire(EmployeeType.MARKET_RESEARCH);
		employeemgr.hire(EmployeeType.ARCHITECT);
		
		//instantiate the departments and assign employees
		sales = new Department(EmployeeType.SALES);
		sales.assignEmployee(employeemgr.getUnassignedEmployee(EmployeeType.SALES));
		
		procurement = new Department(EmployeeType.SALES);
		procurement.assignEmployee(employeemgr.getUnassignedEmployee(EmployeeType.PROCUREMENT));
		
		marketResearch = new Department(EmployeeType.MARKET_RESEARCH);
		marketResearch.assignEmployee(employeemgr.getUnassignedEmployee(EmployeeType.MARKET_RESEARCH));
		
		production = new ProductionHouse();
		//TODO Add possibility to buy machines/machine type declaration
		
		designthinking = new ResearchProject();
		//TODO Add functionality to add architect and get costs ;)
	}

	/**
 * This can fail when: 
 * 	-Not enough space in the warehouse
 * 	-Not enough resources on the market
 * 	-Not enough money
 * @param type Resource type to buy
 * @param amount Amount of the resource to buy
 * @return if the order was successful
	 * @throws ResourceMarketException for negative/zero amount
	 * @throws EnterpriseException Not enough space in the warehouse or not enough Money
 */
	public void buyResources(ResourceType type, int amount) throws EnterpriseException, ResourceMarketException{
		ResourceMarket market = ResourceMarket.get();
		ResourceListItem inventory = market.getResources().get(type);
		Resource[] resources;
		int price = amount * inventory.getCosts();
		if(price < cash){
			throw new EnterpriseException("Not enough Money to buy "+amount+" Resources!");
		}
		
		resources = market.sellResources(type, amount);
		if(resources != null){
			if(!warehouse.storeResource(resources)){
				throw new EnterpriseException("Not enough space in your warehouse!");
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

	public void producePFHouse(PFHouseType type, ArrayList<Employee> employees, int price)
			throws EnterpriseException {

		// ------------------------------------------------------------------------------------------CONDITIONS-CHECK:START

		// How much walls are needed for pfhousetype?
		WallType[] wt = type.getRequiredWallTypes();
		int[] wc = type.getWallCounts();

		// Check whether the needed walls are in the warehouse.
		for (int i = 0; i < wt.length; i++) {
			if (!warehouse.isInStorage(wt[i], wc[i])) {
				throw new EnterpriseException("Not enough walls in your warehouse!");
			}
		}

		// How much resources are needed for pfhousetype?
		ResourceType[] rt = type.getRequiredResourceTypes();
		int[] rc = type.getResourceCounts();

		// Check whether the needed resources are in the warehouse.
		for (int i = 0; i < rt.length; i++) {
			if (!warehouse.isInStorage(rt[i], rc[i])) {
				throw new EnterpriseException("Not enough resources in your warehouse!");
			}
		}
		// enough resources in warehouse

		// How much employees are needed for pfhousetype?
		EmployeeType[] et = type.getRequiredEmployeeTypes();
		int[] ec = type.getEmployeeCounts();

		// Check whether the needed employees are provided.
		int[] tmp_ec = new int[ec.length];

		// Count up tmp_ec for each employee-type provided in the
		// employee-parameter.
		// If a not required employee is provided, he/she will be removed
		// from the list of employees needed for the house-building-job.
		boolean requiredForJob = false;
		for (int i = 0; i < employees.size(); i++) {
			requiredForJob = false;
			for (int j = 0; j < et.length; j++) {
				if (employees.get(i).getType() == et[j]) {
					tmp_ec[j]++;
					requiredForJob = true;
				}
			}
			if (!requiredForJob) {
				employees.remove(i);
				i--;
			}
		}
		for (int i = 0; i < et.length; i++) {
			if (tmp_ec[i] < ec[i]) {
				throw new EnterpriseException("Not enough employees to build this house!");
			}
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
			tmp_wall = warehouse.removeWalls(wt[i], wc[i]);
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

		// Also include employee-costs into the calculation.
		for (int i = 0; i < employees.size(); i++) {
			costs += employees.get(i).getCosts() * type.getConstructionDuration();
		}

		// create a new house and append it to the array-list saving the houses
		// which are in construction.
		PFHouse pfh = new PFHouse(price, costs, type, type.getConstructionDuration(), employees);

		housesInConstruction.add(pfh);

	}
/*
 * This Method calculates all costs which can't be related to a single house building project
 * including:
 * 			warehouse (including storage keepers) costs
 * 			architect and architect project costs
 * 			employees: market_reasearch, hr_manager, salesman, procurement manager
 * @returns the actual FixedCosts
 * 				
 */
	public int calculateFixedCosts(){
		int sum = 0;
		sum += warehouse.getCosts();
		sum += sales.getEmployeeCosts();
		sum += procurement.getEmployeeCosts();
		sum += marketResearch.getEmployeeCosts();
		//TODO add Project Costs..
		return sum;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public ProductionHouse getProductionHouse() {
		return production;
	}

	public ArrayList<PFHouse> getHousesInConstruction() {
		return housesInConstruction;
	}
}

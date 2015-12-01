package sim;

import java.util.ArrayList;

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
import sim.production.WallType;
import sim.research.dev.ResearchProject;
import sim.warehouse.Warehouse;

public class Enterprise {
	
	private int cash;
	
	private Warehouse warehouse;
	private ProductionHouse production;
	
	private ArrayList<PFHouse> housesInProduction;
	
	//Employee management for warehouse and production goes in the distinct classes
	private HR employeemgr;
	private Department sales;
	private Department procurement;
	private Department marketResearch;
	private ResearchProject designthinking; //architect

	
	
	public Enterprise() {
		housesInProduction = new ArrayList<>();
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
	
	public boolean producePFHouse(PFHouseType type, ArrayList<Employee> employees) throws EnterpriseException {
		
		//How much walls are needed for pfhousetype?
		WallType[] wt = type.getRequiredWallTypes();
		int[] wc = type.getWallCounts();

		//Check whether the needed walls are in the warehouse.
		for (int i = 0; i < wt.length; i++) {
			if (!warehouse.isInStorage(wt[i], wc[i])) {
				throw new EnterpriseException("Not enough walls in your warehouse!");
			}
		} 
		

		//How much resources are needed for pfhousetype?
		ResourceType[] rt = type.getRequiredResourceTypes();
		int[] rc = type.getResourceCounts();

		//Check whether the needed resources are in the warehouse.
		for (int i = 0; i < rt.length; i++) {
			if (warehouse.isInStorage(rt[i], rc[i])) {
				throw new EnterpriseException("Not enough resources in your warehouse!");
			}
		}
		//enough resources in warehouse
		

		
		//How much employees are needed for pfhousetype?
		EmployeeType[] et = type.getRequiredEmployeeTypes();
		int[] ec= type.getEmployeeCounts();

		//Check whether the needed employees are available (free). 
		int[] tmp_ec = new int[ec.length];
		
		for (int i = 0; i < employees.size(); i++) {
			for (int j = 0; j < et.length; j++) {
				if (employees.get(i).getType() == et[j])
					tmp_ec[j]++;					
			}
		}
		for (int i = 0; i < et.length; i++) {
			if (tmp_ec[i] < ec[i])
				return false;
		}
		//enough employees


		PFHouse pfh = new PFHouse(0, 0, type, type.getConstructionDuration(), employees);
		
		housesInProduction.add(pfh);
		
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
}

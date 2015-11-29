package sim;

import java.util.ArrayList;

import sim.hr.Employee;
import sim.hr.EmployeeMgr;
import sim.hr.EmployeeType;
import sim.procurement.Resource;
import sim.procurement.ResourceType;
import sim.production.PFHouse;
import sim.production.PFHouseType;
import sim.production.ProductionHouse;
import sim.production.WallType;
import sim.simulation.purchase.ResourceListItem;
import sim.simulation.purchase.ResourceMarket;
import sim.simulation.purchase.ResourceMarketException;
import sim.warehouse.Warehouse;

public class Enterprise {
	
	private int cash;
	
	private Warehouse warehouse;
	private ProductionHouse production;
	
	private ArrayList<PFHouse> housesInProduction;
	
	//Employee management for warehouse and production goes in the distinct classes
	private EmployeeMgr employeemgr;
	private ArrayList<Employee> hr;
	private ArrayList<Employee> procurement;
	private ArrayList<Employee> rnd;
	private ArrayList<Employee> market;
	
	
	public Enterprise() {
		housesInProduction = new ArrayList<>();
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
		EmployeeType[] tmp_et = new EmployeeType[et.length]; 
		int[] tmp_ec = new int[ec.length];
		for (int i = 0; i < employees.size(); i++) {
			
		}
		//enough employees


		PFHouse pfh = new PFHouse(0, 0, type, type.getConstructionDuration(), employees);
		
		housesInProduction.add(pfh);
		
		
	}
}

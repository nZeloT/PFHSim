package sim;

import java.util.ArrayList;

import sim.hr.Employee;
import sim.procurement.Resource;
import sim.procurement.ResourceType;
import sim.production.ProductionHouse;
import sim.simulation.purchase.ResourceList;
import sim.simulation.purchase.ResourceMarket;
import sim.warehouse.Warehouse;

public class Enterprise {
	
	private int cash;
	
	private Warehouse warehouse;
	private ProductionHouse production;
	
	//Employee management for warehouse and production goes in the distinct classes
	private ArrayList<Employee> hr;
	private ArrayList<Employee> procurement;
	private ArrayList<Employee> rnd;
	private ArrayList<Employee> market;
	
	public Enterprise() {
	}

	/**
 * This can fail when: 
 * 	-Not enough space in the warehouse
 * 	-Not enough resources on the market
 * 	-Not enough money
 * @param type Resource type to buy
 * @param amount Amount of the resource to buy
 * @return if the order was successfull
 */
	public boolean buyResources(ResourceType type, int amount){
		ResourceMarket market = ResourceMarket.get();
		ResourceList inventory = market.getResources().get(type);
		Resource[] resources;
		int price = amount * inventory.getCosts();
		if(price < cash){
			return false;
		}
		
		resources = market.sellResources(type, amount);
		if(resources != null){
			if(warehouse.storeResource(resources)){
				return true;
			}
		}
		
		return false;
	}
}

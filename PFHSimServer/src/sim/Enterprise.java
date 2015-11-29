package sim;

import java.util.ArrayList;

import sim.hr.Employee;
import sim.procurement.Resource;
import sim.procurement.ResourceType;
import sim.production.ProductionHouse;
import sim.simulation.purchase.ResourceListItem;
import sim.simulation.purchase.ResourceMarket;
import sim.simulation.purchase.ResourceMarketException;
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
}

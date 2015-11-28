package sim;

import java.util.ArrayList;

import sim.hr.Employee;
import sim.procurement.Resource;
import sim.procurement.ResourceType;
import sim.production.ProductionHouse;
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
	
	public void buyResources(ResourceType type, int amount){
		ResourceMarket market = ResourceMarket.get();
		Resource[] resources;
		resources = market.sellResources(type, amount);
		if(resources != null){
			warehouse.storeResource(resources);
		}
	}
}

package sim;

import java.util.ArrayList;

import sim.hr.Employee;
import sim.production.ProductionHouse;
import sim.warehouse.Warehouse;

public class Enterprise {
	
	private Warehouse warehouse;
	private ProductionHouse production;
	
	//Employee management for warehouse and production goes in the distinct classes
	private ArrayList<Employee> hr;
	private ArrayList<Employee> procurement;
	private ArrayList<Employee> rnd;
	private ArrayList<Employee> market;
	
	
}

package sim.production;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import sim.hr.Employee;
import sim.procurement.Resource;
import sim.procurement.ResourceType;
import sim.warehouse.Warehouse;

public class TestMachine {

	@Test
	public void testEmptyWarehouse() {

		ArrayList<Employee> emps = new ArrayList<>();
		emps.add(new Employee());
		emps.add(new Employee());
		emps.add(new Employee());
		Machine m = new Machine(new Warehouse(50, 100),emps);
		assertEquals(m.produceWall(WallType.ECO), false);
	
	}
	
	@Test
	public void testSuccessfulProduction() {

		ArrayList<Employee> emps = new ArrayList<>();
		emps.add(new Employee());
		emps.add(new Employee());
		emps.add(new Employee());
		Warehouse wh = new Warehouse(50, 100);
		Machine m = new Machine(wh, emps);
		Resource[] r = {new Resource(100, ResourceType.WOOD),
				new Resource(100, ResourceType.WOOD),
				new Resource(100, ResourceType.WOOD),
				new Resource(100, ResourceType.WOOD),
				new Resource(100, ResourceType.WOOD),
				new Resource(100, ResourceType.WOOD),
				new Resource(100, ResourceType.WOOD)};
		wh.storeResource(r);
		assertEquals(m.produceWall(WallType.ECO), true);
	}
	

}

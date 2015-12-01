package sim.production;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.procurement.Resource;
import sim.procurement.ResourceType;
import sim.warehouse.Warehouse;

public class TestMachine {

	@Test 
	public void testEmptyWarehouse() {

		ArrayList<Employee> emps = new ArrayList<>();
		emps.add(new Employee(EmployeeType.PRODUCTION));
		emps.add(new Employee(EmployeeType.PRODUCTION));
		emps.add(new Employee(EmployeeType.PRODUCTION));
		Warehouse wh = null;
		Machine m = null;
		try {
			wh = new Warehouse(500, 100, new Employee(EmployeeType.STORE_KEEPER));
			m = new Machine(emps);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		assertEquals(m.produceWall(WallType.ECO, wh), false);
	}
	
	@Test
	public void testSuccessfulProduction() {

		ArrayList<Employee> emps = new ArrayList<>();
		emps.add(new Employee(EmployeeType.PRODUCTION));
		emps.add(new Employee(EmployeeType.PRODUCTION));
		emps.add(new Employee(EmployeeType.PRODUCTION));
		Warehouse wh = null;
		Machine m = null;
		try { 
			wh = new Warehouse(500, 100, new Employee(EmployeeType.STORE_KEEPER));
			m = new Machine(emps);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		Resource[] r = {
				new Resource(100, ResourceType.WOOD),
				new Resource(100, ResourceType.WOOD),
				new Resource(100, ResourceType.WOOD),
				new Resource(100, ResourceType.WOOD), 
				new Resource(100, ResourceType.WOOD),  
				new Resource(100, ResourceType.WOOD),
				new Resource(100, ResourceType.WOOD)};
		wh.storeResource(r);
		assertEquals(m.produceWall(WallType.ECO, wh), true);
	}
	
	

	@Test
	public void testWrongTypesOfResourcesInWarehouse() {

		ArrayList<Employee> emps = new ArrayList<>();
		emps.add(new Employee(EmployeeType.PRODUCTION));
		emps.add(new Employee(EmployeeType.PRODUCTION));
		emps.add(new Employee(EmployeeType.PRODUCTION));
		Warehouse wh = null;
		Machine m = null;
		try {
			wh = new Warehouse(500, 100, new Employee(EmployeeType.STORE_KEEPER));
			 m = new Machine(emps);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		Resource[] r = {
				new Resource(100, ResourceType.INSULATION),
				new Resource(100, ResourceType.INSULATION),
				new Resource(100, ResourceType.INSULATION),
				new Resource(100, ResourceType.INSULATION), 
				new Resource(100, ResourceType.INSULATION), 
				new Resource(100, ResourceType.INSULATION)};
		wh.storeResource(r);
		assertEquals(m.produceWall(WallType.ECO, wh), false);
	}

	@Test
	public void testIfIsProducableAndProduceWallSuccessful() {

		ArrayList<Employee> emps = new ArrayList<>();
		emps.add(new Employee(EmployeeType.PRODUCTION));
		emps.add(new Employee(EmployeeType.PRODUCTION));
		emps.add(new Employee(EmployeeType.PRODUCTION));
		Warehouse wh = null;
		Machine m = null;
		try {
			wh = new Warehouse(500, 100, new Employee(EmployeeType.STORE_KEEPER));
			 m = new Machine(emps);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		Resource[] r = {
				new Resource(100, ResourceType.INSULATION),
				new Resource(100, ResourceType.WOOD),
				new Resource(100, ResourceType.WOOD),
				new Resource(100, ResourceType.WOOD), 
				new Resource(100, ResourceType.WOOD), 
				new Resource(100, ResourceType.WOOD)};
		wh.storeResource(r);
		assertEquals(m.isProducable(WallType.ECO, wh), true);
		assertEquals(m.produceWall(WallType.ECO, wh), true);
		
	}

	@Test
	public void testWhetherMultipleProductionProcessesWork() {

		ArrayList<Employee> emps = new ArrayList<>();
		emps.add(new Employee(EmployeeType.PRODUCTION));
		emps.add(new Employee(EmployeeType.PRODUCTION));
		emps.add(new Employee(EmployeeType.PRODUCTION));
		Warehouse wh = null;
		Machine m = null;
		try {
			wh = new Warehouse(500, 100, new Employee(EmployeeType.STORE_KEEPER));
			m = new Machine(emps);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		Resource[] r = {
				new Resource(100, ResourceType.INSULATION),
				new Resource(100, ResourceType.WOOD),
				new Resource(100, ResourceType.WOOD), 
				new Resource(100, ResourceType.WOOD),
				new Resource(100, ResourceType.WOOD), 
				new Resource(100, ResourceType.WOOD),
				new Resource(100, ResourceType.WOOD), 
				new Resource(100, ResourceType.WOOD),
				new Resource(100, ResourceType.WOOD), 
				new Resource(100, ResourceType.WOOD)};
		wh.storeResource(r);
		
		assertEquals(m.isProducable(WallType.ECO, wh), true);
		assertEquals(m.produceWall(WallType.ECO, wh), true);
		assertEquals(m.isProducable(WallType.ECO, wh), false);
		assertEquals(m.produceWall(WallType.ECO, wh), false);
		
		wh.storeResource(new Resource(100, ResourceType.WOOD));
		assertEquals(m.isProducable(WallType.ECO, wh), true);
		assertEquals(m.produceWall(WallType.ECO, wh), true);

		wh.storeResource(new Resource(100, ResourceType.WOOD));
		assertEquals(m.isProducable(WallType.ECO, wh), false);
		assertEquals(m.produceWall(WallType.ECO, wh), false);
		
		
	}

	
}

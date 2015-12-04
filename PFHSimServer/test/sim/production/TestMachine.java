package sim.production;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.hr.HR;
import sim.procurement.Resource;
import sim.procurement.ResourceType;
import sim.warehouse.Warehouse;

public class TestMachine {
	
	private Employee[] emps;
	private Warehouse w;
	private Machine m;
	
	@Before
	public void setUp(){
		HR hr = new HR();
		Employee hrG = hr.hire(EmployeeType.HR);
		hrG.assignWorkplace(hr);
		
		emps = hr.hire(EmployeeType.PRODUCTION, 3);
		
		try{
			w = new Warehouse(500, 100, hr.hire(EmployeeType.STORE_KEEPER, 3));
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
		
		try {
			m = new Machine(MachineType.BRICKWALL_MACHINE);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		
	}

	@Test 
	public void testEmptyWarehouse() {
		assertEquals(m.isProducable(WallType.LIGHT_WEIGHT_CONSTRUCTION, w), false);
	}
	
	@Test
	public void testSuccessfulProduction() {
		//Test becomes irrelevant as EnterpriseTest implements this test within its test "testEasyProductionCycle()"
	}
	
	

	@Test
	public void testWrongTypesOfResourcesInWarehouse() {
		Resource[] r = {
				new Resource(100, ResourceType.INSULATION),
				new Resource(100, ResourceType.INSULATION),
				new Resource(100, ResourceType.INSULATION),
				new Resource(100, ResourceType.INSULATION), 
				new Resource(100, ResourceType.INSULATION), 
				new Resource(100, ResourceType.INSULATION)};
		w.storeResource(r);
		assertEquals(m.isProducable(WallType.LIGHT_WEIGHT_CONSTRUCTION, w), false);
	}

	@Test
	public void testIfIsProducableAndProduceWallSuccessful() {
		Resource[] r = {
				new Resource(100, ResourceType.INSULATION),
				new Resource(100, ResourceType.WOOD),
				new Resource(100, ResourceType.WOOD),
				new Resource(100, ResourceType.WOOD), 
				new Resource(100, ResourceType.WOOD), 
				new Resource(100, ResourceType.WOOD)};
		w.storeResource(r);
		assertEquals(m.isProducable(WallType.LIGHT_WEIGHT_CONSTRUCTION, w), true);
		
	}

	@Test
	public void testWhetherMultipleProductionProcessesWork() {
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
		w.storeResource(r);
		
		assertEquals(m.isProducable(WallType.LIGHT_WEIGHT_CONSTRUCTION, w), false);
		
		w.storeResource(new Resource(100, ResourceType.WINDOW));
		assertEquals(m.isProducable(WallType.LIGHT_WEIGHT_CONSTRUCTION, w), true);

		w.storeResource(new Resource(100, ResourceType.WOOD));
		assertEquals(m.isProducable(WallType.LIGHT_WEIGHT_CONSTRUCTION, w), false);
		
		
	}

	
}

package sim.production;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import sim.EnterpriseException;
import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.hr.HR;
import sim.procurement.Resource;
import sim.procurement.ResourceType;
import sim.warehouse.Warehouse;

public class TestMachine {

	private Warehouse w;
	private Machine m;

	private Resource[] resources;

	@Before
	public void setUp(){
		HR hr = new HR();
		Employee hrG = hr.hire(EmployeeType.HR);
		hrG.assignWorkplace(hr);

		try{
			w = new Warehouse(hr.hire(EmployeeType.STORE_KEEPER, 3));
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}

		try {
			m = new Machine(MachineType.WOODWALL_MACHINE);
			Arrays.asList(hr.hire(EmployeeType.PRODUCTION, 5)).forEach((emp) -> {m.assignEmployee(emp);});	
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
				new Resource(100, ResourceType.WINDOW),
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



	}

	private void setUpWarehouse() {
		try{
			HR empMgr = new HR();
			Employee hrGuy = empMgr.hire(EmployeeType.HR);
			hrGuy.assignWorkplace(empMgr);

			w = new Warehouse(empMgr.hire(EmployeeType.STORE_KEEPER, 3));

			resources = new Resource[9];
			resources[0] = new Resource(20, ResourceType.WOOD);
			resources[1] = new Resource(20, ResourceType.INSULATION);
			resources[2] = new Resource(20, ResourceType.INSULATION);
			resources[3] = new Resource(20, ResourceType.INSULATION);
			resources[4] = new Resource(20, ResourceType.WOOD);
			resources[5] = new Resource(20, ResourceType.WOOD);
			resources[6] = new Resource(20, ResourceType.WOOD);
			resources[7] = new Resource(20, ResourceType.WOOD);
			resources[8] = new Resource(30, ResourceType.WINDOW);
			
			w.storeResource(resources);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void testProduceWOEnougEmployees(){
		setUpWarehouse();

		HR emplManager = new HR();
		Employee emp1 = emplManager.hire(EmployeeType.HR);
		emp1.assignWorkplace(emplManager);

		Machine masch = null;
		try {
			masch = new Machine(MachineType.WOODWALL_MACHINE);
		} catch (Exception e) {
			fail();
		}

		Employee emp2 = emplManager.hire(EmployeeType.PRODUCTION);
		emp2.assignWorkplace(masch);

		try {
			masch.setProductionType(WallType.LIGHT_WEIGHT_CONSTRUCTION);
			masch.produceWall(w);
		} catch (EnterpriseException e) {
			assertEquals(w.isInStorage(WallType.LIGHT_WEIGHT_CONSTRUCTION), false);
		}

	}

	@Test
	public void testProduceWToManyEmployees(){
		setUpWarehouse();

		HR emplManager = new HR();
		Employee empM = emplManager.hire(EmployeeType.HR);
		empM.assignWorkplace(emplManager);

		Machine masch = null;
		try {
			masch = new Machine(MachineType.WOODWALL_MACHINE);
		} catch (Exception e) {
			fail();
		}

		Employee[] hiredPeople = emplManager.hire(EmployeeType.PRODUCTION, 49);


		for (int i = 0; i < hiredPeople.length; i++) {
			hiredPeople[i].assignWorkplace(masch);

		}
		try {
			masch.setProductionType(WallType.LIGHT_WEIGHT_CONSTRUCTION);
			masch.produceWall(w);
		} catch (EnterpriseException e) {
			e.printStackTrace();
		}
		assertEquals(w.isInStorage(WallType.LIGHT_WEIGHT_CONSTRUCTION),true);	

	}

	@Test
	public void testProduceWithRightAmountOfEmp(){
		setUpWarehouse();

		HR emplManager = new HR();
		Employee emp1 = emplManager.hire(EmployeeType.HR);
		emp1.assignWorkplace(emplManager);

		Machine masch = null;
		try {
			masch = new Machine(MachineType.WOODWALL_MACHINE);
		} catch (Exception e) {
			fail();
		}

		Employee emp2 = emplManager.hire(EmployeeType.PRODUCTION);
		emp2.assignWorkplace(masch);

		Employee emp3 = emplManager.hire(EmployeeType.PRODUCTION);
		emp3.assignWorkplace(masch);

		Employee emp4 = emplManager.hire(EmployeeType.PRODUCTION);
		emp4.assignWorkplace(masch);
		try {
			masch.setProductionType(WallType.LIGHT_WEIGHT_CONSTRUCTION);
			masch.produceWall(w);
		} catch (EnterpriseException e) {
			fail();
		}

		assertEquals(w.isInStorage(WallType.LIGHT_WEIGHT_CONSTRUCTION), true);
	}


}

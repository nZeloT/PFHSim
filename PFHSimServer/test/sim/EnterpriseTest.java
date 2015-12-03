package sim;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.hr.HR;
import sim.procurement.Resource;
import sim.procurement.ResourceMarket;
import sim.procurement.ResourceType;
import sim.production.PFHouseType;
import sim.production.WallType;
import sim.warehouse.Warehouse;


public class EnterpriseTest {

	@Test
	public void testEasyProductionCycle() {

		Enterprise e = null;
		e = new Enterprise();

		e.getWarehouse().storeResource(new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.CONCRETE), new Resource(50, ResourceType.CONCRETE),
				new Resource(50, ResourceType.CONCRETE), new Resource(50, ResourceType.CONCRETE),
				new Resource(50, ResourceType.CONCRETE), new Resource(50, ResourceType.CONCRETE),
				new Resource(50, ResourceType.CONCRETE), new Resource(50, ResourceType.CONCRETE),
				new Resource(50, ResourceType.CONCRETE), new Resource(50, ResourceType.CONCRETE),
				new Resource(50, ResourceType.CONCRETE), new Resource(50, ResourceType.CONCRETE),
				new Resource(50, ResourceType.INSULATION));

		Employee[] p = e.getHR().hire(EmployeeType.PRODUCTION, 5);

		e.getProductionHouse().operateNewMachine(Arrays.asList(p));
		e.getProductionHouse().getMachines().get(0).produceWall(WallType.NORMAL, e.getWarehouse());
		e.getProductionHouse().getMachines().get(0).produceWall(WallType.NORMAL, e.getWarehouse());
		e.getProductionHouse().getMachines().get(0).produceWall(WallType.NORMAL, e.getWarehouse());
		e.getProductionHouse().getMachines().get(0).produceWall(WallType.NORMAL, e.getWarehouse());
		e.getProductionHouse().getMachines().get(0).produceWall(WallType.NORMAL, e.getWarehouse());

		Employee[] h = e.getHR().hire(EmployeeType.ASSEMBLER, 5);
		try {
			e.producePFHouse(PFHouseType.NORMAL, Arrays.asList(h), 9000);
		} catch (EnterpriseException e1) {
			e1.printStackTrace();
		}

		assertNotNull(e.getHousesInConstruction().get(0));

	}

	@Test
	public void testFixCosts() {
		int testfixcosts = 0;
		HR employmgr = new HR();
		Employee hrGuy = employmgr.hire(EmployeeType.HR);
		hrGuy.assignWorkplace(employmgr);
		Warehouse storage;
		try {
			storage = new Warehouse(99999, 3000, employmgr.hire(EmployeeType.STORE_KEEPER, 3));
			assertEquals(5100, storage.getOverallCosts());
			testfixcosts += storage.getOverallCosts();
			testfixcosts += employmgr.getEmployeeCosts();
			// testfixcosts += EmployeeType.ARCHITECT.getBaseCost();
			testfixcosts += EmployeeType.SALES.getBaseCost();
			testfixcosts += EmployeeType.PROCUREMENT.getBaseCost();
			testfixcosts += EmployeeType.MARKET_RESEARCH.getBaseCost();
			testfixcosts += employmgr.getEmployeeCosts();
			Enterprise e = new Enterprise();
			assertEquals(testfixcosts, e.calculateFixedCosts());

		} catch (Exception e1) {
			e1.printStackTrace();
			fail();
		}

	}

	@Test
	public void testVariableCosts() {
		//not ready
		fail();
		try {
			Enterprise e = new Enterprise();
			e.buyResources(ResourceType.WOOD, 500);
			e.buyResources(ResourceType.BRICK, 500);
			e.buyResources(ResourceType.CONCRETE, 500);
			e.buyResources(ResourceType.ROOF_TILE, 500);
			e.buyResources(ResourceType.WINDOW, 500);
			e.buyResources(ResourceType.INSULATION, 500);
			
			ResourceMarket market = ResourceMarket.get();
			market.adjustPrices();
			
			e.buyResources(ResourceType.WOOD, 500);
			e.buyResources(ResourceType.BRICK, 500);
			e.buyResources(ResourceType.CONCRETE, 500);
			e.buyResources(ResourceType.ROOF_TILE, 500);
			e.buyResources(ResourceType.WINDOW, 500);
			e.buyResources(ResourceType.INSULATION, 500);

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}

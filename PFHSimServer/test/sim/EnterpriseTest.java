package sim;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.hr.HR;
import sim.procurement.Resource;
import sim.procurement.ResourceMarket;
import sim.procurement.ResourceType;
import sim.production.MachineException;
import sim.production.MachineType;
import sim.production.PFHouseType;
import sim.production.WallType;
import sim.warehouse.Warehouse;

public class EnterpriseTest {

	@Test
	public void testEasyProductionCycle() {

		Enterprise e = null;
		e = new Enterprise();

		Employee[] s = e.getHR().hire(EmployeeType.STORE_KEEPER, 5);
		for (Employee emp : s) {
			e.getWarehouse().assignEmployee(emp);
		}

		e.getWarehouse().storeResource(new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.INSULATION), new Resource(50, ResourceType.INSULATION),
				new Resource(50, ResourceType.INSULATION), new Resource(50, ResourceType.INSULATION),
				new Resource(50, ResourceType.INSULATION), new Resource(50, ResourceType.INSULATION),
				new Resource(50, ResourceType.INSULATION), new Resource(50, ResourceType.INSULATION),
				new Resource(50, ResourceType.INSULATION), new Resource(50, ResourceType.INSULATION),
				new Resource(50, ResourceType.INSULATION), new Resource(50, ResourceType.INSULATION),
				new Resource(50, ResourceType.INSULATION), new Resource(50, ResourceType.INSULATION),
				new Resource(50, ResourceType.INSULATION), new Resource(50, ResourceType.INSULATION),
				new Resource(50, ResourceType.INSULATION), new Resource(50, ResourceType.INSULATION),
				new Resource(50, ResourceType.INSULATION), new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW), new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW), new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW), new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW), new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW), new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW), new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW), new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW), new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW), new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW), new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW), new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE), new Resource(50, ResourceType.CONCRETE),
				new Resource(50, ResourceType.CONCRETE), new Resource(50, ResourceType.CONCRETE),
				new Resource(50, ResourceType.CONCRETE), new Resource(50, ResourceType.CONCRETE),
				new Resource(50, ResourceType.CONCRETE), new Resource(50, ResourceType.CONCRETE),
				new Resource(50, ResourceType.CONCRETE), new Resource(50, ResourceType.CONCRETE),
				new Resource(50, ResourceType.CONCRETE), new Resource(50, ResourceType.CONCRETE),
				new Resource(50, ResourceType.CONCRETE));

		Employee[] p = e.getHR().hire(EmployeeType.PRODUCTION, 5);
		e.getProductionHouse().buyMachine(MachineType.WOODWALL_MACHINE, Arrays.asList(p));
		for (Employee emp : p) {
			e.getProductionHouse().getMachines().get(0).assignEmployee(emp);
		}

		try {
			e.getProductionHouse().getMachines().get(0).produceWall(WallType.LIGHT_WEIGHT_CONSTRUCTION,
					e.getWarehouse());
			e.getProductionHouse().getMachines().get(0).produceWall(WallType.LIGHT_WEIGHT_CONSTRUCTION,
					e.getWarehouse());
			e.getProductionHouse().getMachines().get(0).produceWall(WallType.LIGHT_WEIGHT_CONSTRUCTION,
					e.getWarehouse());
			e.getProductionHouse().getMachines().get(0).produceWall(WallType.LIGHT_WEIGHT_CONSTRUCTION,
					e.getWarehouse());
			e.getProductionHouse().getMachines().get(0).produceWall(WallType.LIGHT_WEIGHT_CONSTRUCTION,
					e.getWarehouse());
		} catch (MachineException e2) {
			e2.printStackTrace();
		}

		Employee[] a = e.getHR().hire(EmployeeType.ASSEMBLER, 3);

		ArrayList<WallType> walltypes = new ArrayList<>();
		walltypes.add(WallType.LIGHT_WEIGHT_CONSTRUCTION);
		ArrayList<Integer> wallcounts = new ArrayList<>();
		wallcounts.add(5);

		try {
			e.producePFHouse(PFHouseType.BUNGALOW, walltypes, wallcounts, Arrays.asList(a), 5500);
			for (Employee emp : a) {
				e.getHousesInConstruction().get(0).assignEmployee(emp);
			}
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
		// not ready
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

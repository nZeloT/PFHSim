package sim;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import sim.abstraction.Tupel;
import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.hr.HR;
import sim.procurement.ResourceMarket;
import sim.procurement.ResourceMarketException;
import sim.procurement.ResourceType;
import sim.production.Machine;
import sim.production.MachineException;
import sim.production.MachineType;
import sim.production.PFHouseType;
import sim.production.WallType;
import sim.simulation.sales.Offer;
import sim.warehouse.Warehouse;

public class EnterpriseTest {

	@Test
	public void testIfAmountOfHousesIsProducible() throws EnterpriseException, ResourceMarketException{

		Enterprise e = TestUtils.initializeEnterprise();
		ResourceMarket mar = TestUtils.getMarket();
		Employee[] assembler = e.getHR().hire(EmployeeType.ASSEMBLER, 20);
		
		e.buyResources(ResourceType.WOOD, 28);
		e.buyResources(ResourceType.ROOF_TILE, 50);
		
		try {
			Machine m = e.getProductionHouse().getMachines().get(0);
			m.setProductionType(WallType.LIGHT_WEIGHT_CONSTRUCTION);
			
			for(int i = 0; i<12; i++){
				m.produceWall(e.getWarehouse());
			}

			e.checkRequirementsforOffer(PFHouseType.BLOCK_HOUSE, 4, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION,5));
			
		} catch (MachineException e2) {
			e2.printStackTrace();
			fail();
		}
		
		}
	@Test
	public void testEasyProductionCycle() {

		Enterprise e = TestUtils.initializeEnterprise();

		try {
			Machine m = e.getProductionHouse().getMachines().get(0);
			m.setProductionType(WallType.LIGHT_WEIGHT_CONSTRUCTION);
			m.produceWall(e.getWarehouse());
			m.produceWall(e.getWarehouse());
			m.produceWall(e.getWarehouse());
			m.produceWall(e.getWarehouse());
			m.produceWall(e.getWarehouse());
		} catch (MachineException e2) {
			e2.printStackTrace();
			fail();
		}

		Employee[] a = e.getHR().hire(EmployeeType.ASSEMBLER, 3);

		ArrayList<WallType> walltypes = new ArrayList<>();
		walltypes.add(WallType.LIGHT_WEIGHT_CONSTRUCTION);
		ArrayList<Integer> wallcounts = new ArrayList<>();
		wallcounts.add(5);

		try {
			e.startPFHouseProduction(
					new Offer(1, 5500, PFHouseType.BUNGALOW, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5)),
					a);
		} catch (EnterpriseException e1) {
			e1.printStackTrace();
			fail();
		}
	}

	@Test
	public void testFixCosts() {
		int testfixcosts = 0;
		HR employmgr = new HR();
		Employee hrGuy = employmgr.hire(EmployeeType.HR);
		hrGuy.assignWorkplace(employmgr);
		Warehouse storage;
		try {
			storage = new Warehouse(employmgr.hire(EmployeeType.STORE_KEEPER, 3));
			assertEquals(5100, storage.getOverallCosts());
			testfixcosts += storage.getOverallCosts();
			testfixcosts += employmgr.getEmployeeCosts();
			// testfixcosts += EmployeeType.ARCHITECT.getBaseCost();
			testfixcosts += EmployeeType.SALES.getBaseCost();
			testfixcosts += EmployeeType.PROCUREMENT.getBaseCost();
			testfixcosts += EmployeeType.MARKET_RESEARCH.getBaseCost();
			Enterprise e = new Enterprise(TestUtils.getMarket());
			assertEquals(testfixcosts, e.calculateFixedCosts());

		} catch (Exception e1) {
			e1.printStackTrace();
			fail();
		}

	}

	@Test
	public void testVariableCosts() {
		try {
			Enterprise e = new Enterprise(TestUtils.getMarket());
			e.buyResources(ResourceType.WOOD, 500);
			e.buyResources(ResourceType.BRICK, 500);
			e.buyResources(ResourceType.CONCRETE, 500);
			e.buyResources(ResourceType.ROOF_TILE, 500);
			e.buyResources(ResourceType.WINDOW, 500);
			e.buyResources(ResourceType.INSULATION, 500);
			e.buyMachine(MachineType.WOODWALL_MACHINE);
			Machine machine = e.getProductionHouse().getMachines().get(0);
			Employee[] es = e.getHR().hire(EmployeeType.PRODUCTION, 3);
			for (int i = 0; i < es.length; i++) {
				es[i].assignWorkplace(machine);
			}
			
			machine.setProductionType(WallType.LIGHT_WEIGHT_CONSTRUCTION);
			machine.produceWall(e.getWarehouse());
			//easy part
			assertEquals(e.getWarehouse().removeWall(WallType.LIGHT_WEIGHT_CONSTRUCTION).getCosts(), 236);
			
			//TODO: how to fix this
//			m.adjustPrices();
//
//			e.buyResources(m, ResourceType.WOOD, 500);
//			e.buyResources(m, ResourceType.BRICK, 500);
//			e.buyResources(m, ResourceType.CONCRETE, 500);
//			e.buyResources(m, ResourceType.ROOF_TILE, 500);
//			e.buyResources(m, ResourceType.WINDOW, 500);
//			e.buyResources(m, ResourceType.INSULATION, 500);
//			
//			machine.produceWall(e.getWarehouse());
//			assertEquals(e.getWarehouse().removeWall(WallType.LIGHT_WEIGHT_CONSTRUCTION).getCosts(), 239);

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
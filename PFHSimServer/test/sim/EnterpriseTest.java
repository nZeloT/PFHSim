package sim;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.procurement.Resource;
import sim.procurement.ResourceType;
import sim.production.PFHouseType;
import sim.production.WallType;
import sim.simulation.sales.Offer;

public class EnterpriseTest {

	@Test
	public void testEasyProductionCycle() {
		
		Enterprise e = null;
		e = new Enterprise();
		

		Employee[] s = e.getHR().hire(EmployeeType.STORE_KEEPER, 5);
		for (Employee emp : s) {
			e.getWarehouse().assignEmployee(emp);
		}
		
		
		e.getWarehouse().storeResource(
				new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD),  
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD),  
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD),  
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD),  
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD),  
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD),  
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD),  
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD),  
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD),
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD),  
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.WOOD), 
				new Resource(50, ResourceType.INSULATION),
				new Resource(50, ResourceType.INSULATION),
				new Resource(50, ResourceType.INSULATION),
				new Resource(50, ResourceType.INSULATION),
				new Resource(50, ResourceType.INSULATION),
				new Resource(50, ResourceType.INSULATION),
				new Resource(50, ResourceType.INSULATION),
				new Resource(50, ResourceType.INSULATION),
				new Resource(50, ResourceType.INSULATION),
				new Resource(50, ResourceType.INSULATION),
				new Resource(50, ResourceType.INSULATION),
				new Resource(50, ResourceType.INSULATION),
				new Resource(50, ResourceType.INSULATION),
				new Resource(50, ResourceType.INSULATION),
				new Resource(50, ResourceType.INSULATION),
				new Resource(50, ResourceType.INSULATION),
				new Resource(50, ResourceType.INSULATION),
				new Resource(50, ResourceType.INSULATION),
				new Resource(50, ResourceType.INSULATION),
				new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.WINDOW),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.ROOF_TILE),
				new Resource(50, ResourceType.CONCRETE), 
				new Resource(50, ResourceType.CONCRETE), 
				new Resource(50, ResourceType.CONCRETE), 
				new Resource(50, ResourceType.CONCRETE), 
				new Resource(50, ResourceType.CONCRETE), 
				new Resource(50, ResourceType.CONCRETE), 
				new Resource(50, ResourceType.CONCRETE), 
				new Resource(50, ResourceType.CONCRETE), 
				new Resource(50, ResourceType.CONCRETE), 
				new Resource(50, ResourceType.CONCRETE), 
				new Resource(50, ResourceType.CONCRETE), 
				new Resource(50, ResourceType.CONCRETE));

		
		
		Employee[] p = e.getHR().hire(EmployeeType.PRODUCTION, 5);
		e.getProductionHouse().operateNewMachine(Arrays.asList(p));
		for (Employee emp : p) {
			e.getProductionHouse().getMachines().get(0).assignEmployee(emp);
		}
		
		e.getProductionHouse().getMachines().get(0).produceWall(WallType.LIGHT_WEIGHT_CONSTRUCTION, e.getWarehouse());
		e.getProductionHouse().getMachines().get(0).produceWall(WallType.LIGHT_WEIGHT_CONSTRUCTION, e.getWarehouse());
		e.getProductionHouse().getMachines().get(0).produceWall(WallType.LIGHT_WEIGHT_CONSTRUCTION, e.getWarehouse());
		e.getProductionHouse().getMachines().get(0).produceWall(WallType.LIGHT_WEIGHT_CONSTRUCTION, e.getWarehouse());
		e.getProductionHouse().getMachines().get(0).produceWall(WallType.LIGHT_WEIGHT_CONSTRUCTION, e.getWarehouse());
		

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

}

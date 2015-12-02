package sim;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

import org.junit.Test;

import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.procurement.Resource;
import sim.procurement.ResourceType;
import sim.production.PFHouseType;
import sim.production.WallType;

public class EnterpriseTest {

	@Test
	public void testEasyProductionCycle() {
		
		Enterprise e = null;
		e = new Enterprise();
		
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
				new Resource(50, ResourceType.CONCRETE),  
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

}

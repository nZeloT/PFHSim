package sim;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import sim.abstraction.WrongEmployeeTypeException;
import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.procurement.Resource;
import sim.procurement.ResourceType;
import sim.production.PFHouseType;
import sim.production.ProductionHouse;
import sim.production.WallType;
import sim.warehouse.Warehouse;
import sim.warehouse.WarehouseException;

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

		

		ArrayList<Employee> p = new ArrayList<>();
		p.add(new Employee(EmployeeType.PRODUCTION));
		p.add(new Employee(EmployeeType.PRODUCTION));
		p.add(new Employee(EmployeeType.PRODUCTION));
		p.add(new Employee(EmployeeType.PRODUCTION));
		p.add(new Employee(EmployeeType.PRODUCTION));
		
		e.getProductionHouse().operateNewMachine(p);
		e.getProductionHouse().getMachines().get(0).produceWall(WallType.NORMAL, e.getWarehouse());
		e.getProductionHouse().getMachines().get(0).produceWall(WallType.NORMAL, e.getWarehouse());
		e.getProductionHouse().getMachines().get(0).produceWall(WallType.NORMAL, e.getWarehouse());
		e.getProductionHouse().getMachines().get(0).produceWall(WallType.NORMAL, e.getWarehouse());
		e.getProductionHouse().getMachines().get(0).produceWall(WallType.NORMAL, e.getWarehouse());
		

		ArrayList<Employee> h = new ArrayList<>();
		h.add(new Employee(EmployeeType.ASSEMBLER));
		h.add(new Employee(EmployeeType.ASSEMBLER));
		h.add(new Employee(EmployeeType.ASSEMBLER));
		h.add(new Employee(EmployeeType.ASSEMBLER));
		h.add(new Employee(EmployeeType.ASSEMBLER)); 
		
		try {
			e.producePFHouse(PFHouseType.NORMAL, h, 9000);
		} catch (EnterpriseException e1) {
			e1.printStackTrace();
		}

		assertNotNull(e.getHousesInConstruction().get(0));
		
		
	}

}

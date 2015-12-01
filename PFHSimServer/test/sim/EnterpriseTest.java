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
		try {
			e = new Enterprise();
		} catch (WarehouseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (WrongEmployeeTypeException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		Warehouse wh = e.getWarehouse();
		
				wh.storeResource(
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
		e.setWarehouse(wh);

		

		ArrayList<Employee> p = new ArrayList<>();
		p.add(new Employee(EmployeeType.PRODUCTION));
		p.add(new Employee(EmployeeType.PRODUCTION));
		p.add(new Employee(EmployeeType.PRODUCTION));
		p.add(new Employee(EmployeeType.PRODUCTION));
		p.add(new Employee(EmployeeType.PRODUCTION));
		
		e.getProductionHouse().operateNewMachine(p);
		e.getProductionHouse().getMachines().get(0).produceWall(WallType.NORMAL, wh);
		e.getProductionHouse().getMachines().get(0).produceWall(WallType.NORMAL, wh);
		e.getProductionHouse().getMachines().get(0).produceWall(WallType.NORMAL, wh);
		e.getProductionHouse().getMachines().get(0).produceWall(WallType.NORMAL, wh);
		e.getProductionHouse().getMachines().get(0).produceWall(WallType.NORMAL, wh);
		e.setWarehouse(wh);
		

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

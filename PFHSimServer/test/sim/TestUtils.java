package sim;

import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.procurement.Resource;
import sim.procurement.ResourceType;
import sim.production.MachineType;

public class TestUtils {

	public Enterprise initializeEnterprise() {
		
		
		Enterprise e = new Enterprise();
		
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
		e.getProductionHouse().buyMachine(MachineType.WOODWALL_MACHINE);
		for (Employee emp : p) {
			e.getProductionHouse().getMachines().get(0).assignEmployee(emp);
		}
		
		
		
		return e;
		
	}
	
}

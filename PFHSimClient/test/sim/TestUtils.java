package sim;

import java.util.Arrays;
import java.util.HashMap;

import sim.hr.EmployeeType;
import sim.procurement.ResourceMarket;
import sim.procurement.ResourceMarketException;
import sim.procurement.ResourceType;
import sim.production.MachineType;

public class TestUtils {

	/**
	 * 
	 * @return This method returns an enterprise with the most relevant 
	 * features.
	 * -> 80 wood, 10 brick, 20 concrete, 30 insulation, 150 roof tile, 25 window
	 * + woodwall-machine and all essential employees for HR, warehouse and the machine.
	 * 
	 * @author Alexander
	 * 
	 */
	public static Enterprise initializeEnterprise() {
		
		Enterprise e = new Enterprise();
		ResourceMarket m = getMarket();
		
		e.getHR().assignEmployee(e.getHR().hire(EmployeeType.HR));
		Arrays.asList(e.getHR().hire(EmployeeType.STORE_KEEPER, 5)).forEach((emp) -> {e.getWarehouse().assignEmployee(emp);});

		try {
			e.buyResources(m, ResourceType.WOOD, 80);
			e.buyResources(m, ResourceType.BRICK, 10);
			e.buyResources(m, ResourceType.CONCRETE, 20);
			e.buyResources(m, ResourceType.INSULATION, 30);
			e.buyResources(m, ResourceType.ROOF_TILE, 150);
			e.buyResources(m, ResourceType.WINDOW, 25);
		} catch (EnterpriseException e1) {
			e1.printStackTrace();
		} catch (ResourceMarketException e1) {
			e1.printStackTrace();
		}

		e.getProductionHouse().buyMachine(MachineType.WOODWALL_MACHINE);
		Arrays.asList(e.getHR().hire(EmployeeType.PRODUCTION, 5)).forEach((emp) -> {e.getProductionHouse().getMachines().get(0).assignEmployee(emp);});
		 
		return e;
	}
	
	public static ResourceMarket getMarket(){
		HashMap<ResourceType, Integer> costs = new HashMap<ResourceType, Integer>();
		
		for (ResourceType t : ResourceType.values()) {
			costs.put(t, t.getBasePrice());
		}
		
		return new ResourceMarket(costs);
	}
	
}

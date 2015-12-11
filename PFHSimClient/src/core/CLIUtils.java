package core;

import sim.Enterprise;
import sim.EnterpriseException;
import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.procurement.ResourceMarket;
import sim.procurement.ResourceMarketException;
import sim.procurement.ResourceType;
import sim.production.Machine;
import sim.production.MachineType;

public class CLIUtils {
	public static void setup(Enterprise ent, ResourceMarket market){
		try {
			ent.buyResources(ResourceType.WOOD, 90);
			ent.buyResources(ResourceType.INSULATION, 150);
			ent.buyResources(ResourceType.WINDOW, 40);
			
			ent.buyMachine(MachineType.WOODWALL_MACHINE);
			
			Machine m1 = ent.getProductionHouse().getMachines().get(0);
			Employee[] worker = ent.getHR().hire(EmployeeType.PRODUCTION, 3);
			
			for(Employee e : worker)
				e.assignWorkplace(m1);
		} catch (EnterpriseException e) {
			e.printStackTrace();
		} catch (ResourceMarketException e) {
			e.printStackTrace();
		}
	}
}

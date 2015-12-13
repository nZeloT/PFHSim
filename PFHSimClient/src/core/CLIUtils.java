package core;

import sim.Enterprise;
import sim.EnterpriseException;
import sim.abstraction.Tupel;
import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.procurement.ResourceMarket;
import sim.procurement.ResourceMarketException;
import sim.procurement.ResourceType;
import sim.production.Machine;
import sim.production.MachineType;
import sim.production.PFHouseType;
import sim.production.WallType;
import sim.simulation.sales.Offer;

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
	
	public static void produce(Enterprise ent){
		try {
			ent.buyResources(ResourceType.WOOD, 100);
			ent.buyResources(ResourceType.INSULATION, 100);
			ent.buyResources(ResourceType.ROOF_TILE, 500);
			ent.buyResources(ResourceType.CONCRETE, 100);
			Machine machine = ent.getProductionHouse().getMachines().get(0);
			Employee[] es = ent.getHR().hire(EmployeeType.PRODUCTION, 3);
			for (Employee e : es) {
				e.assignWorkplace(machine);
			}
			ent.getHR().hire(EmployeeType.ASSEMBLER, 20);
		} catch (EnterpriseException | ResourceMarketException e) {
			e.printStackTrace();
		}
	}
	
	public static void createOffer(Enterprise ent, int price, int quality){
		ent.addOffer(new Offer(price, quality, PFHouseType.BUNGALOW, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5)));
	}
}

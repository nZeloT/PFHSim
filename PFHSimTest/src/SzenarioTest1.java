import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import core.ServerSimulation;
import sim.Enterprise;
import sim.bank.BankException;
import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.hr.HRException;
import sim.procurement.ResourceMarket;
import sim.procurement.ResourceMarketException;
import sim.procurement.ResourceType;
import sim.production.Machine;
import sim.production.MachineType;
import sim.production.PFHouseType;
import sim.production.WallType;
import sim.warehouse.WarehouseException;

public class SzenarioTest1 {
	/**
	 * @author Florian
	 * This is the first Szenario Test for the PFHouseSim Game
	 * This is described in the project Paper p. TODO add page
	 * Briefly: we'll play a game and have a look at two strategies:
	 * 	-price-performance-strategie
	 * 	-diversification
	 */

	/**
	 * setup up the game
	 * @throws Exception
	 */
	ServerSimulation sim;
	Enterprise player1;
	Enterprise player2;
	ArrayList<Enterprise> players;
	
	@Before
	public void setUp() throws Exception {
		sim = new ServerSimulation();
		player1 = new Enterprise(sim.getMarket());
		player2 = new Enterprise(sim.getMarket());
		players = new ArrayList<>();
		players.add(player1);
		players.add(player2);
	}


	@Test
	public void test() {
		//lets start playing
		//-------------------------------------------//
		//           ROUND 1                         //
		//-------------------------------------------//
		
		//buy some resources for the production
		//assume that the price performance strategie instantly goes for the higher quality bungalows, while the "diversification player" goes for all possible offers
		//still, everyone is going to need to buy resources in the first round, we'll assume that they'll buy the same
		try {
			for (Enterprise player : players) {
				player.buyResources(ResourceType.WOOD, 200);
				player.buyResources(ResourceType.CONCRETE, 200);
				player.buyResources(ResourceType.BRICK, 300);
				player.buyResources(ResourceType.ROOF_TILE, 500);
				player.buyResources(ResourceType.WOOD, 100);
			}
			//now buy some machines. This will be the same for both players, too. But we will change the production type
			//for equal starting positions we will buy the same amount again
			for (Enterprise player : players) {
				player.buyMachine(MachineType.BRICKWALL_MACHINE);
				player.buyMachine(MachineType.BRICKWALL_MACHINE);
				player.buyMachine(MachineType.WOODWALL_MACHINE);
				player.buyMachine(MachineType.WOODWALL_MACHINE);
			}
			//change the production types: for the price-performance-strategie change types to "plus" constructions
			List<Machine> machines1 = player1.getProductionHouse().getMachines();
			for (Machine machine : machines1) {
				if (machine.getProductionType().equals(WallType.LIGHT_WEIGHT_CONSTRUCTION)) {
					machine.setProductionType(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS);
				} else {
					machine.setProductionType(WallType.MASSIVE_PLUS_CONSTUCTION);
				}
			}
			//for diversification try to match as many as possible
			List<Machine> machines2 = player2.getProductionHouse().getMachines();
			machines2.get(0).setProductionType(WallType.MASSIVE_LIGHT_CONSTRUCTION);
			machines2.get(1).setProductionType(WallType.MASSIVE_PLUS_CONSTUCTION);
			machines2.get(2).setProductionType(WallType.LIGHT_WEIGHT_CONSTRUCTION);
			machines2.get(3).setProductionType(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS);
			
			//hire Employees for the machines (3 for 1 machine) and assign the Employees to the machine
			for (Enterprise player: players) {
				Employee[] workers = player.getHR().hire(EmployeeType.PRODUCTION, 12);
				for (int i = 0; i < workers.length; i++) {
					workers[i].assignWorkplace(player.getProductionHouse().getMachines().get(i/3));
				}
			}
			
			//research a new Housetype the price performance strategie will research the lower housetypes, 
			//the diversification player will research different ones
			player1.startResearchProject(PFHouseType.BLOCK_HOUSE);
			player2.startResearchProject(PFHouseType.COMFORT_HOUSE);
			
			//no offers need to be created in the first round, as walls first need to be produced
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

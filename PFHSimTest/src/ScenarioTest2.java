import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import core.ServerSimulation;
import sim.Enterprise;
import sim.abstraction.Pair;
import sim.abstraction.Tupel;
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
import sim.simulation.sales.Offer;
import sim.warehouse.WarehouseException;

public class ScenarioTest2 {
	/**
	 * @author Alexander This is the second cscnario test for the PFHouseSim
	 *         Game This is described in the project Paper p. TODO add page
	 *         Briefly: we'll play a game and have a look at two strategies: -
	 *         price-volume - mansions strategy (luxury)
	 */

	/**
	 * setup up the game
	 * 
	 * @throws Exception
	 */
	Helper sim;
	Enterprise player1;
	Enterprise player2;
	ArrayList<Enterprise> players;

	@Before
	public void setUp() throws Exception {
		sim = new Helper();
		ResourceMarket[] markets = sim.initMarkets(2);
		player1 = new Enterprise(markets[0]);
		player2 = new Enterprise(markets[1]);
		players = new ArrayList<>();
		players.add(player1);
		players.add(player2);
	}

	@Test
	public void test() {
		// lets start playing
		// -------------------------------------------//
		// ROUND 1 //
		// -------------------------------------------//

		// buy some resources for the production
		// assume that the price performance strategie instantly goes for the
		// higher quality bungalows, while the "diversification player" goes for
		// all possible offers
		// still, everyone is going to need to buy resources in the first round,
		// we'll assume that they'll buy the same
		try {
			player1.buyResources(ResourceType.WOOD, 1000);
			player1.buyResources(ResourceType.CONCRETE, 1000);
			player1.buyResources(ResourceType.BRICK, 1000);
			player1.buyResources(ResourceType.INSULATION, 1000);
			player1.buyResources(ResourceType.ROOF_TILE, 1000);
			player1.buyResources(ResourceType.WINDOW, 1000);
			player2.buyResources(ResourceType.WOOD, 1000);
			player2.buyResources(ResourceType.CONCRETE, 1000);
			player2.buyResources(ResourceType.BRICK, 1000);
			player2.buyResources(ResourceType.ROOF_TILE, 1000);
			player2.buyResources(ResourceType.INSULATION, 1000);
			player2.buyResources(ResourceType.WINDOW, 1000);

			// now buy some machines.

			player1.buyMachine(MachineType.BRICKWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player2.buyMachine(MachineType.BRICKWALL_MACHINE);
			player2.buyMachine(MachineType.PANORAMA_WALL_MACHINE); 
			player2.buyMachine(MachineType.PANORAMA_WALL_MACHINE);
			player2.buyMachine(MachineType.PANORAMA_WALL_MACHINE);

			List<Machine> machines2 = player2.getProductionHouse().getMachines();
			for (Machine machine : machines2) {
				machine.upgrade();
			}
			machines2.get(0).setProductionType(WallType.MASSIVE_PLUS_CONSTUCTION);

			// hire Employees for the machines (3 for 1 machine) and assign the
			// Employees to the machine
			for (Enterprise player : players) {
				Employee[] workers = player.getHR().hire(EmployeeType.PRODUCTION, 12);
				for (int i = 0; i < workers.length; i++) {
					workers[i].assignWorkplace(player.getProductionHouse().getMachines().get(i / 3));
				}
			}

			// research a new Housetype the price performance strategie will
			// research the lower housetypes,
			// the diversification player will research different ones
			//player1.startResearchProject(PFHouseType.BLOCK_HOUSE);
			player2.startResearchProject(PFHouseType.CITY_VILLA);

			player1.startWarehouseExtension();
			player2.startWarehouseExtension();

			// no offers need to be created in the first round, as walls first
			// need to be produced

			sim.doRoundTrip(player1, player2);

			// -------------------------------------------
			// ROUND 2
			// ------------------------------------------- 

			System.out.println("ROUND 2------------------------------------------------------------");
			
			player1.buyResources(ResourceType.WOOD, 400);
			player1.buyResources(ResourceType.CONCRETE, 200);
			player1.buyResources(ResourceType.BRICK, 300);
			player1.buyResources(ResourceType.INSULATION, 100);
			player1.buyResources(ResourceType.WINDOW, 100);

			player1.getSales().addOffer(new Offer(14000, PFHouseType.BUNGALOW, 10,
					new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5)));
			player1.getSales().addOffer(new Offer(15000, PFHouseType.BUNGALOW, 5,
					new Tupel<WallType>(WallType.MASSIVE_LIGHT_CONSTRUCTION, 5))); 

			player2.getSales().addOffer(new Offer(15950, PFHouseType.BUNGALOW, 30,
					new Tupel<WallType>(WallType.MASSIVE_PLUS_CONSTUCTION, 5)));

			player1.getHR().hire(EmployeeType.HR);
			player1.getHR().hire(EmployeeType.HR); 
			player1.getHR().hire(EmployeeType.HR);
			player1.getHR().hire(EmployeeType.HR); 
			player1.getHR().hire(EmployeeType.HR);
			player1.getHR().hire(EmployeeType.HR);
			player1.getHR().hire(EmployeeType.HR); 
			Employee[] hr1 = player1.getHR().getAllOfType(EmployeeType.HR);
			//player1.startEmployeeTraining(hr1[1]);
			player1.getHR().hire(EmployeeType.ASSEMBLER, 200);

			player2.getHR().hire(EmployeeType.HR);
			player2.getHR().hire(EmployeeType.HR);
			player2.getHR().hire(EmployeeType.HR);
			player2.getHR().hire(EmployeeType.HR);
			Employee[] hr2 = player2.getHR().getAllOfType(EmployeeType.HR);
			player2.startEmployeeTraining(hr2[1]);
			player2.startEmployeeTraining(hr2[2]);
			player2.getHR().hire(EmployeeType.ASSEMBLER, 30);

			for (Machine machine : machines2) {
				machine.upgrade();
			}
			
			sim.doRoundTrip(player1, player2);

			// -------------------------------------------
			// ROUND 3
			// -------------------------------------------

			System.out.println("ROUND 3------------------------------------------------------------");
			
			
			player1.buyResources(ResourceType.WOOD, 200);
			player1.buyResources(ResourceType.CONCRETE, 200);
			player1.buyResources(ResourceType.BRICK, 300);
			player1.buyResources(ResourceType.ROOF_TILE, 500);
			player1.buyResources(ResourceType.WOOD, 100);
			player2.buyResources(ResourceType.WOOD, 200);
			player2.buyResources(ResourceType.CONCRETE, 200);
			player2.buyResources(ResourceType.BRICK, 300);
			player2.buyResources(ResourceType.ROOF_TILE, 500);
			player2.buyResources(ResourceType.WOOD, 100);
			player2.buyResources(ResourceType.WINDOW, 100);


			for (Machine machine : machines2) {
				machine.upgrade();
			}

			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);

			// hire Employees for the machines (3 for each machine) and assign
			// the Employees to the machine
			Employee[] workers = player1.getHR().hire(EmployeeType.PRODUCTION, 24);
			for (int i = 0; i < workers.length; i++) {
				workers[i].assignWorkplace(player1.getProductionHouse().getMachines().get(i / 3 + 4));
			}

			machines2 = player2.getProductionHouse().getMachines();
			for (Machine machine : machines2) {
				machine.upgrade();
			}
			machines2.get(0).setProductionType(WallType.MASSIVE_PLUS_CONSTUCTION);

			

			//player1.getSales().addOffer(new Offer(16000, PFHouseType.BLOCK_HOUSE, 150,
			//		new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 6)));
			//player1.getSales().addOffer(new Offer(16500, PFHouseType.BLOCK_HOUSE, 150,
			//		new Tupel<WallType>(WallType.MASSIVE_LIGHT_CONSTRUCTION, 6)));
			

			player1.getHR().hire(EmployeeType.ASSEMBLER, 50);

			
			List<Pair<String, Integer>> bestlist = sim.doRoundTrip(player1, player2);
			

			player1.getHR().hire(EmployeeType.ASSEMBLER, 1);
			
			
			System.out.println("Player 1 (Price-Volume) cash: " + player1.getBankAccount().getCash());
			System.out.println("Player 2 (mansion) cash: " + player2.getBankAccount().getCash());
			
			for (int i = 0; i < bestlist.size(); i++) {
				System.out.println(bestlist.get(i).k + ": " + bestlist.get(i).v);
			}
			
			assertEquals(0,0);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

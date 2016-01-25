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
		// assume that the price volume strategy instantly goes for the
		// lower quality bungalows/block houses, while the "executive player" goes for
		// all the luxury houses.
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
			//player1 wants to produce woodwalls, player2 rather needs panorama walls.
			player1.buyMachine(MachineType.BRICKWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player2.buyMachine(MachineType.BRICKWALL_MACHINE);
			player2.buyMachine(MachineType.BRICKWALL_MACHINE);
			player2.buyMachine(MachineType.PANORAMA_WALL_MACHINE);
			player2.buyMachine(MachineType.PANORAMA_WALL_MACHINE);
			player2.buyMachine(MachineType.PANORAMA_WALL_MACHINE); 
			player2.buyMachine(MachineType.PANORAMA_WALL_MACHINE);
			player2.buyMachine(MachineType.PANORAMA_WALL_MACHINE);

			//in each round player2 will try to upgrade his machines when possible for
			//achievieng the best quality possible.
			this.upgradeMachines(player2);
			
			player2.getProductionHouse().getMachines().get(0).setProductionType(WallType.MASSIVE_PLUS_CONSTUCTION);
			player2.getProductionHouse().getMachines().get(1).setProductionType(WallType.MASSIVE_PLUS_CONSTUCTION);

			// hire Employees for the machines (3 for 1 machine) and assign the
			// Employees to the machine
			for (Enterprise player : players) {
				Employee[] workers = player.getHR().hire(EmployeeType.PRODUCTION, 21);
				for (int i = 0; i < workers.length; i++) {
					workers[i].assignWorkplace(player.getProductionHouse().getMachines().get(i / 3));
				}
			}

			// research a new Housetype according to the player's strategy.
			player1.startResearchProject(PFHouseType.BLOCK_HOUSE);
			player2.startResearchProject(PFHouseType.CITY_VILLA);

			//both players need more space in their warehouse.
			//a fatal mistake would be missing this upgrade.
			player1.startWarehouseExtension();
			player2.startWarehouseExtension();

			// no offers need to be created in the first round, as walls firstly
			// need to be produced.

			sim.doRoundTrip(player1, player2);

			// -------------------------------------------
			// ROUND 2
			// ------------------------------------------- 

			System.out.println("ROUND 2------------------------------------------------------------");

			
			//player1 needs new resources; player2 not, because he upgraded his machines --> machines are inactive.
			player1.buyResources(ResourceType.WOOD, 500);
			player1.buyResources(ResourceType.CONCRETE, 200);
			player1.buyResources(ResourceType.BRICK, 300); 
			player1.buyResources(ResourceType.INSULATION, 100);
			player1.buyResources(ResourceType.WINDOW, 100);

			//after this round the first houses can be assembled. Therefore both players
			//need to create offers.
			player1.getSales().addOffer(new Offer(25000, PFHouseType.BUNGALOW, 50,
					new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5)));
			player1.getSales().addOffer(new Offer(35000, PFHouseType.BUNGALOW, 50,
					new Tupel<WallType>(WallType.MASSIVE_LIGHT_CONSTRUCTION, 5))); 
			//until the executive-player's houses are researched, he will run out of money
			//if he does not sell houses. So also create a bungalow offer, but with high-quality-walls.
			player2.getSales().addOffer(new Offer(35950, PFHouseType.BUNGALOW, 30,
					new Tupel<WallType>(WallType.MASSIVE_PLUS_CONSTUCTION, 5)));

			//so now it is time to hire assembler, which are needed for assembling a house.
			//therefore both players firstly need new HR-capacity.
			//But the executive-player does not need as many assembler firstly, as he does not 
			//intend to sell as many houses.
			Employee e = player1.getHR().hire(EmployeeType.HR);
			Employee e2 = player1.getHR().hire(EmployeeType.HR);
			player1.autoAssignEmployees(e, e2, player1.getHR().hire(EmployeeType.HR),player1.getHR().hire(EmployeeType.HR),player1.getHR().hire(EmployeeType.HR), player1.getHR().hire(EmployeeType.HR), player1.getHR().hire(EmployeeType.HR), player1.getHR().hire(EmployeeType.HR), player1.getHR().hire(EmployeeType.HR)); 
			Employee[] hr1 = player1.getHR().getAllOfType(EmployeeType.HR);
			player1.getHR().hire(EmployeeType.ASSEMBLER, 250);

			e = player2.getHR().hire(EmployeeType.HR);
			e2 = player2.getHR().hire(EmployeeType.HR);
			player2.autoAssignEmployees(e, e2, player2.getHR().hire(EmployeeType.HR)); 
			Employee[] hr2 = player2.getHR().getAllOfType(EmployeeType.HR);
			player2.startEmployeeTraining(hr2[1]);
			player2.startEmployeeTraining(hr2[2]);
			player2.startEmployeeTraining(hr2[3]);
			player2.getHR().hire(EmployeeType.ASSEMBLER, 30);

			this.upgradeMachines(player2);
			

			sim.doRoundTrip(player1, player2);

			// -------------------------------------------
			// ROUND 3
			// -------------------------------------------

			System.out.println("ROUND 3------------------------------------------------------------");
			
			//now both players need more resources.
			player1.buyResources(ResourceType.WOOD, 3000);
			player1.buyResources(ResourceType.CONCRETE, 200);
			player1.buyResources(ResourceType.BRICK, 300);
			player1.buyResources(ResourceType.ROOF_TILE, 2500);
			player1.buyResources(ResourceType.INSULATION, 100);
			player2.buyResources(ResourceType.WOOD, 1000);
			player2.buyResources(ResourceType.CONCRETE, 200);
			player2.buyResources(ResourceType.BRICK, 300);
			player2.buyResources(ResourceType.ROOF_TILE, 1500);
			player2.buyResources(ResourceType.INSULATION, 500);
			player2.buyResources(ResourceType.WINDOW, 100);

			//as the price-volume-player wants to sell as many houases as possible
			//he needs more walls. therefore purchase further machines
			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player1.buyMachine(MachineType.BRICKWALL_MACHINE);
			player1.buyMachine(MachineType.BRICKWALL_MACHINE);
			player1.buyMachine(MachineType.BRICKWALL_MACHINE);
			player1.buyMachine(MachineType.BRICKWALL_MACHINE);
			player1.buyMachine(MachineType.BRICKWALL_MACHINE);
			player1.buyMachine(MachineType.BRICKWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);
			player1.buyMachine(MachineType.WOODWALL_MACHINE);

			// hire Employees for the machines (3 for each machine) and assign
			// the Employees to the machine
			Employee[] workers = player1.getHR().hire(EmployeeType.PRODUCTION, 72);
			for (int i = 0; i < workers.length; i++) {
				workers[i].assignWorkplace(player1.getProductionHouse().getMachines().get(i / 3 + 7));
			}
			List<Machine> machines1 = player1.getProductionHouse().getMachines();
			machines1.get(10).setProductionType(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS);
			machines1.get(11).setProductionType(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS);
			machines1.get(12).setProductionType(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS);
			machines1.get(13).setProductionType(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS);
			machines1.get(14).setProductionType(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS);
			machines1.get(15).setProductionType(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS);

			
			this.upgradeMachines(player2);

			
			//the block house is researched now. the price-volume-player is able to create 
			//an block-house offer.
			player1.getSales().addOffer(new Offer(40000, PFHouseType.BLOCK_HOUSE, 150,
					new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 3),
					new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS, 3)));
			
			
			//as he will sell more houses with the new offer he needs more assemblers
			player1.getHR().hire(EmployeeType.ASSEMBLER, 200);

			//also, another warehouse-extension can be processed.
			player1.startWarehouseExtension();
			player2.startWarehouseExtension();
			
			List<Pair<String, Integer>> bestlist = sim.doRoundTrip(player1, player2);
			

			
			
			System.out.println("Player 1 (Price-Volume) cash: " + player1.getBankAccount().getCash());
			System.out.println("Player 2 (executive) cash: " + player2.getBankAccount().getCash());
			
			for (int i = 0; i < bestlist.size(); i++) {
				System.out.println(bestlist.get(i).k + ": " + bestlist.get(i).v);
			}
			
			

			// -------------------------------------------
			// ROUND 4
			// -------------------------------------------

System.out.println("ROUND 4------------------------------------------------------------");

			
			player1.buyResources(ResourceType.WOOD, 2000);
			player1.buyResources(ResourceType.CONCRETE, 200);
			player1.buyResources(ResourceType.ROOF_TILE, 1500);
			player1.buyResources(ResourceType.INSULATION, 300);
			player1.buyResources(ResourceType.WINDOW, 300);
			player2.buyResources(ResourceType.WOOD, 200);
			player2.buyResources(ResourceType.CONCRETE, 200);
			player2.buyResources(ResourceType.INSULATION, 500);
			player2.buyResources(ResourceType.BRICK, 300);
			player2.buyResources(ResourceType.ROOF_TILE, 500);
			player2.buyResources(ResourceType.WOOD, 100);
			player2.buyResources(ResourceType.WINDOW, 100);


			

			this.upgradeMachines(player2);
			
			bestlist = sim.doRoundTrip(player1, player2);

			// -------------------------------------------
			// ROUND 5
			// -------------------------------------------

			System.out.println("ROUND 5------------------------------------------------------------");
			
			System.out.println("----------------------------------------------------------------------------------------");
			System.out.println("Player 1 (Price-Volume) cash: " + player1.getBankAccount().getCash());
			System.out.println("Player 2 (mansion) cash: " + player2.getBankAccount().getCash());
			
			for (int i = 0; i < bestlist.size(); i++) {
				System.out.println(bestlist.get(i).k + ": " + bestlist.get(i).v);
			}

			System.out.println("ROUND 3------------------------------------------------------------");

			
			

			player1.buyResources(ResourceType.WOOD, 2000);
			player1.buyResources(ResourceType.CONCRETE, 200);
			player1.buyResources(ResourceType.BRICK, 300);
			player1.buyResources(ResourceType.INSULATION, 100);
			player1.buyResources(ResourceType.ROOF_TILE, 1000);
			player2.buyResources(ResourceType.WOOD, 200);
			player2.buyResources(ResourceType.CONCRETE, 200);
			player2.buyResources(ResourceType.BRICK, 300);
			player1.buyResources(ResourceType.INSULATION, 1000);
			player2.buyResources(ResourceType.ROOF_TILE, 1000);
			player2.buyResources(ResourceType.WOOD, 500);
			player2.buyResources(ResourceType.WINDOW, 2000);
			

			//now the city villa is researched. Create respective offers.
			//the low-quality-offer from previous rounds (bungalow) is removed
			//from the executive-player's catalogue.
			player2.getHR().hire(EmployeeType.ASSEMBLER, 240);
			player2.getSales().removeOffer(0);
			player2.getSales().addOffer(new Offer(500000, PFHouseType.CITY_VILLA, 30,
					new Tupel<WallType>(WallType.MASSIVE_PLUS_CONSTUCTION, 2),
					new Tupel<WallType>(WallType.PANORAMA_WALL, 6)));


			this.upgradeMachines(player2);
			
			bestlist = sim.doRoundTrip(player1, player2);
			//ROUND 6
			System.out.println("ROUND 6------------------------------------------------------------");
			

			this.upgradeMachines(player2);
			
			
			System.out.println("Player 1 (Price-Volume) cash: " + player1.getBankAccount().getCash());
			System.out.println("Player 2 (mansion) cash: " + player2.getBankAccount().getCash());
			
			for (int i = 0; i < bestlist.size(); i++) {
				System.out.println(bestlist.get(i).k + ": " + bestlist.get(i).v);
			}

			player2.startResearchProject(PFHouseType.TRENDHOUSE);
			
			bestlist = sim.doRoundTrip(player1, player2);
			
			
			
			
			
			//ROUND 7
			System.out.println("ROUND 7------------------------------------------------------------");
			
			
			
			
			player1.buyResources(ResourceType.WOOD, 4000);
			player1.buyResources(ResourceType.WINDOW, 2000);
			player1.buyResources(ResourceType.ROOF_TILE, 3000);
			player1.buyResources(ResourceType.BRICK, 2000);
			player1.buyResources(ResourceType.INSULATION, 1000);
			this.upgradeMachines(player2);
			bestlist = sim.doRoundTrip(player1, player2);
			
			
			
			
			//ROUND 8
			System.out.println("ROUND 8------------------------------------------------------------");
			
			
			
			player1.buyResources(ResourceType.WOOD, 1500);
			
			
			player2.buyResources(ResourceType.WOOD, 2000);
			player2.buyResources(ResourceType.CONCRETE, 200);
			player2.buyResources(ResourceType.BRICK, 1000);
			player2.buyResources(ResourceType.ROOF_TILE, 1000);
			player2.buyResources(ResourceType.INSULATION, 1500);
			player2.buyResources(ResourceType.WINDOW, 500);
			
			//the price-volume player is now able to hire even more employees, 
			//because he has enough money for paying their salary now.
			hr1 = new Employee[]{player2.getHR().hire(EmployeeType.HR),player1.getHR().hire(EmployeeType.HR),player1.getHR().hire(EmployeeType.HR),player1.getHR().hire(EmployeeType.HR)};
			player1.autoAssignEmployees(hr1);
			player1.getHR().hire(EmployeeType.ASSEMBLER, 200);
			
			
		
			//since the executive-player is making profit by selling city-mansions,
			//he needs more panorama-walls, because panorama-wall-machines have 
			//a bad performance. Now he has enough money to buy further machines.
			player2.buyMachine(MachineType.PANORAMA_WALL_MACHINE);
			player2.buyMachine(MachineType.PANORAMA_WALL_MACHINE);
			player2.buyMachine(MachineType.PANORAMA_WALL_MACHINE); 
			player2.buyMachine(MachineType.PANORAMA_WALL_MACHINE);



			// hire Employees for the machines (3 for 1 machine) and assign the
			// Employees to the machine			for (Enterprise player : players) {
			workers = player2.getHR().hire(EmployeeType.PRODUCTION, 12);
			for (int i = 0; i < workers.length; i++) {
				workers[i].assignWorkplace(player2.getProductionHouse().getMachines().get(i / 3 + 7));
			}
			
			this.upgradeMachines(player2);
			

			bestlist = sim.doRoundTrip(player1, player2);
			//ROUND 9
			System.out.println("ROUND 9------------------------------------------------------------");

			player1.buyResources(ResourceType.WOOD, 4000);
			player1.buyResources(ResourceType.WINDOW, 2000);
			player1.buyResources(ResourceType.ROOF_TILE, 3000);
			player1.buyResources(ResourceType.BRICK, 2000);
			player1.buyResources(ResourceType.INSULATION, 1000);
			
			this.upgradeMachines(player2);
			
			
			System.out.println("Player 1 (Price-Volume) cash: " + player1.getBankAccount().getCash());
			System.out.println("Player 2 (mansion) cash: " + player2.getBankAccount().getCash());

			for (int i = 0; i < bestlist.size(); i++) {
				System.out.println(bestlist.get(i).k + ": " + bestlist.get(i).v);
			}

			


			player2.startWarehouseExtension();
			player1.startWarehouseExtension();
			
			player2.buyResources(ResourceType.CONCRETE, 200);
			player2.buyResources(ResourceType.BRICK, 1500);
			player2.buyResources(ResourceType.ROOF_TILE, 6000);
			player2.buyResources(ResourceType.INSULATION, 1500);
			player2.buyResources(ResourceType.WINDOW, 1000);
			
			
			bestlist = sim.doRoundTrip(player1, player2);
			
			
			
			//ROUND 10
			System.out.println("ROUND 10------------------------------------------------------------");

			player1.buyResources(ResourceType.WOOD, 1500);
			player1.buyResources(ResourceType.ROOF_TILE, 6000);
			player1.buyResources(ResourceType.INSULATION, 500);

			this.upgradeMachines(player2);
			
			
			bestlist = sim.doRoundTrip(player1, player2);
			
			
			
			//ROUND 11
			System.out.println("ROUND 11------------------------------------------------------------");


			player1.buyResources(ResourceType.WOOD, 2000);
			player1.buyResources(ResourceType.ROOF_TILE, 6000);
			player1.buyResources(ResourceType.INSULATION, 1000);

			this.upgradeMachines(player2);
			
			player2.buyResources(ResourceType.INSULATION, 2000);
			player2.buyResources(ResourceType.BRICK, 3000);
			player2.buyResources(ResourceType.ROOF_TILE, 6000);
			player2.buyResources(ResourceType.WINDOW, 4000);
			bestlist = sim.doRoundTrip(player1, player2);
			
			
			
			
			
			//ROUND 12
			System.out.println("ROUND 12------------------------------------------------------------");

			

			player1.buyResources(ResourceType.WOOD, 4000);
			player1.buyResources(ResourceType.ROOF_TILE, 4000);
			player1.buyResources(ResourceType.INSULATION, 1500);
			
			//as the executive-player's machines are upgraded now, he producec more walls.
			//this means he is able to sell more houses
			//So more assemblers are needed, especially because the city-villa-construction takes two rounds!
			hr2 = new Employee[]{player2.getHR().hire(EmployeeType.HR),player2.getHR().hire(EmployeeType.HR),player2.getHR().hire(EmployeeType.HR),player2.getHR().hire(EmployeeType.HR)};
			player2.autoAssignEmployees(hr2);
			player2.getHR().hire(EmployeeType.ASSEMBLER, 210);
			player2.getSales().addOffer(new Offer(750000, PFHouseType.TRENDHOUSE, 30,
					new Tupel<WallType>(WallType.PANORAMA_WALL, 8)));

			//player2 (executive) is generating really much profit now.
			//He can buy further machines.
			player2.buyMachine(MachineType.PANORAMA_WALL_MACHINE);
			player2.buyMachine(MachineType.PANORAMA_WALL_MACHINE);
			player2.buyMachine(MachineType.PANORAMA_WALL_MACHINE); 
			player2.buyMachine(MachineType.PANORAMA_WALL_MACHINE);
			player2.buyMachine(MachineType.PANORAMA_WALL_MACHINE);
			player2.buyMachine(MachineType.PANORAMA_WALL_MACHINE);
			player2.buyMachine(MachineType.PANORAMA_WALL_MACHINE);		

			// hire Employees for the machines (3 for 1 machine) and assign the
			// Employees to the machine			for (Enterprise player : players) {
			int noOfMachines = player2.getProductionHouse().getMachines().size();
			workers = player2.getHR().hire(EmployeeType.PRODUCTION, (noOfMachines-11)*3);
			for (int i = 0; i < workers.length; i++) {
				if (!player2.getProductionHouse().getMachines().get(noOfMachines-1-i/3).isInOperation())
					workers[i].assignWorkplace(player2.getProductionHouse().getMachines().get(noOfMachines-i/3-1));
			}

			this.upgradeMachines(player2);
			
			bestlist = sim.doRoundTrip(player1, player2);
			//ROUND 13
			System.out.println("ROUND 13------------------------------------------------------------");


			player1.buyResources(ResourceType.WOOD, 3000);
			player1.buyResources(ResourceType.ROOF_TILE, 6000);
			player1.buyResources(ResourceType.INSULATION, 1000);
			
			
			this.upgradeMachines(player2);

			player2.buyResources(ResourceType.INSULATION, 5000);
			player2.buyResources(ResourceType.ROOF_TILE, 3000);
			player2.buyResources(ResourceType.WINDOW, 5000);
			player2.buyResources(ResourceType.CONCRETE, 3000);
			player2.buyResources(ResourceType.BRICK, 3000);
			
			bestlist = sim.doRoundTrip(player1, player2);
			//ROUND 14
			System.out.println("ROUND 14------------------------------------------------------------");


			player1.buyResources(ResourceType.WOOD, 3000);
			player1.buyResources(ResourceType.ROOF_TILE, 6000);
			player1.buyResources(ResourceType.INSULATION, 500);
			
			
			this.upgradeMachines(player2);

			bestlist = sim.doRoundTrip(player1, player2);
			//ROUND 15
			System.out.println("ROUND 15------------------------------------------------------------");
			

			this.upgradeMachines(player2);
			
			System.out.println("Player 1 (Price-Volume) cash: " + player1.getBankAccount().getCash());
			System.out.println("Player 2 (mansion) cash: " + player2.getBankAccount().getCash());

			for (int i = 0; i < bestlist.size(); i++) {
				System.out.println(bestlist.get(i).k + ": " + bestlist.get(i).v);
			}
			
			
			/*
			 * The end:
			 * the price-volume-player will win as he sells much more houses in contrast to 
			 * the executive player. Although one sold luxury house generates a higher score 
			 * than a bungalow or block house, the number of sold houses by the price-volume-player
			 * generate a higher score. --> and as the command line says that >200 assemblers are missing
			 * for selling more bungalows/block houses, the price-volume-player has much more potential.
			 * 
			 * But best strategy will definitely be: 
			 * - high quality-offers for making high profit
			 * - low-quality-offers with nearly no profit for generating score-points
			 * -> result: the player sells a huge amount of houses providing him with a high score
			 * 			  for winning the game.
			 * 
			 * */
			
		
			System.out.println("Expected Result: Player 1 (enterprise 0 wins)");
			
			System.out.println("Actual Result: Player with Enterprise "+ bestlist.get(1).k+" wins.");
			
			String res = bestlist.get(1).k;
			
			assertEquals("0",res);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void upgradeMachines(Enterprise player) {
		List<Machine> machines = player.getProductionHouse().getMachines();
		for (Machine machine : machines) {
			if (machine.canDoUpgrade())
				machine.upgrade();
			else 
				machine.setMaxOutput(machine.getType().getBasePerformance()+machine.getUpgradeCount()*machine.getType().getUpgradePerfInc());	
			
		}
	}

}

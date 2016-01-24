import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import sim.Enterprise;
import sim.abstraction.Pair;
import sim.abstraction.Tupel;
import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.procurement.ResourceMarket;
import sim.procurement.ResourceType;
import sim.production.Machine;
import sim.production.MachineType;
import sim.production.PFHouseType;
import sim.production.WallType;
import sim.simulation.sales.Offer;
import sim.warehouse.Warehouse;

public class SzenarioTest1 {
	/**
	 * @author Florian This is the first Szenario Test for the PFHouseSim Game
	 *         This is described in the project Paper under Scenario Test 1.
	 *         Briefly: we'll play a game and have a look at two strategies:
	 *         -price-performance-strategic -diversification
	 */

	/**
	 * setup up the game
	 * 
	 * @throws Exception
	 */
	private Helper sim;
	private Enterprise player1;
	private Enterprise player2;
	private ArrayList<Enterprise> players;

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
		// assume that the price performance strategic instantly goes for the
		// higher quality bungalows, while the "diversification player" goes for
		// all possible offers
		// still, everyone is going to need to buy resources in the first round,
		// we'll assume that they'll buy the same
		try {
			for (Enterprise player : players) {
				player.buyResources(ResourceType.WOOD, 600);
				player.buyResources(ResourceType.CONCRETE, 500);
				player.buyResources(ResourceType.INSULATION, 500);
				player.buyResources(ResourceType.BRICK, 500);
				player.buyResources(ResourceType.ROOF_TILE, 1000);
				player.buyResources(ResourceType.WINDOW, 300);
			}
			// now buy some machines. This will be the same for both players,
			// too. But we will change the production type
			// for equal starting positions we will buy the same amount again
			for (Enterprise player : players) {
				player.buyMachine(MachineType.BRICKWALL_MACHINE);
				player.buyMachine(MachineType.BRICKWALL_MACHINE);
				player.buyMachine(MachineType.WOODWALL_MACHINE);
				player.buyMachine(MachineType.WOODWALL_MACHINE);
			}
			// change the production types: for the price-performance-strategic
			// change types to "plus" constructions, keep one on light
			// constructions for block houses
			List<Machine> machines1 = player1.getProductionHouse().getMachines();
			machines1.get(0).setProductionType(WallType.MASSIVE_PLUS_CONSTUCTION);
			machines1.get(1).setProductionType(WallType.MASSIVE_PLUS_CONSTUCTION);
			machines1.get(2).setProductionType(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS);
			machines1.get(3).setProductionType(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS);

			// for diversification try to match as many as possible
			List<Machine> machines2 = player2.getProductionHouse().getMachines();
			machines2.get(0).setProductionType(WallType.MASSIVE_LIGHT_CONSTRUCTION);
			machines2.get(1).setProductionType(WallType.MASSIVE_PLUS_CONSTUCTION);
			machines2.get(2).setProductionType(WallType.LIGHT_WEIGHT_CONSTRUCTION);
			machines2.get(3).setProductionType(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS);

			// hire Employees for the machines (3 for 1 machine) and assign the
			// Employees to the machine
			for (Enterprise player : players) {
				Employee[] workers = player.getHR().hire(EmployeeType.PRODUCTION, 12);
				for (int i = 0; i < workers.length; i++) {
					workers[i].assignWorkplace(player.getProductionHouse().getMachines().get(i / 3));
				}
			}

			// research a new Housetype. the price performance strategie will
			// research the lower housetypes,
			// the diversification player will research different ones
			player1.startResearchProject(PFHouseType.EFFICIENCY_HOUSE);
			player2.startResearchProject(PFHouseType.MULTI_FAMILY_HOUSE);

			// no offers need to be created in the first round, as walls first
			// need to be produced
			sim.doRoundTrip(player1, player2);
			System.out.println("---------------------------------ROUND 2 begins -------------------------------");

			// do some basic round adjustments, buy resources, extend warehouse,
			// etc.
			Helper.roundHelper(player1);
			Helper.roundHelper(player2);
			// as we have produced some walls now, we'll need to hire some
			// assemblers to sell and build up our first houses.
			// As we are going to need a lot of them, hire 2 more HR people for
			// bigger
			// Employee Capacity
			for (Enterprise p : players) {
				Employee[] es = p.getHR().hire(EmployeeType.HR, 4);
				for (int i = 0; i < es.length; i++) {
					es[i].assignWorkplace(p.getHR());
				}
				assertNotNull(p.getHR().hire(EmployeeType.ASSEMBLER, 100));
				// Employee[] es = p.getHR().getAllOfType(EmployeeType.HR);
				// p.getHR().unassignEmployee(es[0]);
				// p.startEmployeeTraining(es[0]);
			}
			// produce some more walls
			// buy new machines to cover the demand which might be possible for
			// the new housetypes
			for (Enterprise player : players) {
				player.buyMachine(MachineType.BRICKWALL_MACHINE);
				player.buyMachine(MachineType.BRICKWALL_MACHINE);
				player.buyMachine(MachineType.BRICKWALL_MACHINE);
				player.buyMachine(MachineType.WOODWALL_MACHINE);
				player.buyMachine(MachineType.WOODWALL_MACHINE);
				player.buyMachine(MachineType.WOODWALL_MACHINE);
			}
			// change the production types: for the price-performance-strategic
			// change types to "plus" constructions
			machines1 = player1.getProductionHouse().getMachines();
			machines1.get(4).setProductionType(WallType.MASSIVE_PLUS_CONSTUCTION);
			machines1.get(5).setProductionType(WallType.MASSIVE_PLUS_CONSTUCTION);
			machines1.get(6).setProductionType(WallType.MASSIVE_PLUS_CONSTUCTION);
			machines1.get(7).setProductionType(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS);
			machines1.get(8).setProductionType(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS);
			machines1.get(9).setProductionType(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS);

			machines2 = player2.getProductionHouse().getMachines();
			machines2.get(4).setProductionType(WallType.MASSIVE_LIGHT_CONSTRUCTION);
			machines2.get(5).setProductionType(WallType.MASSIVE_PLUS_CONSTUCTION);
			machines2.get(6).setProductionType(WallType.MASSIVE_PLUS_CONSTUCTION);
			machines2.get(7).setProductionType(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS);
			machines2.get(8).setProductionType(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS);
			machines2.get(9).setProductionType(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS);

			for (Enterprise player : players) {
				Employee[] workers = player.getHR().hire(EmployeeType.PRODUCTION, 18);
				for (int i = 0; i < workers.length; i++) {
					workers[i].assignWorkplace(player.getProductionHouse().getMachines().get(i / 3 + 4));
				}
			}
			// now we are able to set up offers on the market. Set a price, a
			// housetype, maximum producible and the walls to be used. Check
			// project paper for further explanations
			player1.getSales().addOffer(new Offer(50000, PFHouseType.BUNGALOW, 10,
					new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS, 5)));
			player1.getSales().addOffer(new Offer(60000, PFHouseType.BUNGALOW, 10,
					new Tupel<WallType>(WallType.MASSIVE_PLUS_CONSTUCTION, 5)));
			// ---player 2 offers---
			player2.getSales().addOffer(new Offer(40000, PFHouseType.BUNGALOW, 5,
					new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5)));
			player2.getSales().addOffer(new Offer(50000, PFHouseType.BUNGALOW, 5,
					new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS, 5)));
			player2.getSales().addOffer(new Offer(55000, PFHouseType.BUNGALOW, 4,
					new Tupel<WallType>(WallType.MASSIVE_LIGHT_CONSTRUCTION, 5)));
			player2.getSales().addOffer(new Offer(60000, PFHouseType.BUNGALOW, 4,
					new Tupel<WallType>(WallType.MASSIVE_PLUS_CONSTUCTION, 5)));

			sim.doRoundTrip(player1, player2);
			System.out.println("---------------------------------ROUND 3 begins -------------------------------");
			// do some basic round adjustments, buy resources, extend warehouse,
			// etc.
			Helper.roundHelper(player1);
			Helper.roundHelper(player2);
			for (Enterprise p : players) {
				p.getHR().hire(EmployeeType.ASSEMBLER, 50);
			}
			// a new houstype is available for the price performance player this
			// round! set up an new offer.
			player1.getSales().addOffer(new Offer(60000, PFHouseType.EFFICIENCY_HOUSE, 7,
					new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS, 6)));
			player1.getSales().addOffer(new Offer(60000, PFHouseType.EFFICIENCY_HOUSE, 7,
					new Tupel<WallType>(WallType.MASSIVE_PLUS_CONSTUCTION, 6)));

			// for the second player, set the maximum producible up
			List<Offer> offers2 = player2.getSales().getOffers();
			for (Offer offer : offers2) {
				offer.setProductionLimit(10);
			}
			// player 1 can research a new Housetype
			player1.startResearchProject(PFHouseType.MULTI_FAMILY_HOUSE);

			sim.doRoundTrip(player1, player2);
			System.out.println("---------------------------------ROUND 4 begins -------------------------------");

			// do some basic round adjustments, buy resources, extend warehouse,
			// etc.
			Helper.roundHelper(player1);
			Helper.roundHelper(player2);

			// new Houstype available for player 2!
			// first we need a new Salesman to set up more offers. Otherwise, we
			// would to delete one of our running offers.
			Employee sales = player2.getHR().hire(EmployeeType.SALES);
			sales.assignWorkplace(player2.getSales());

			offers2 = player2.getSales().getOffers();
			for (Offer offer : offers2) {
				offer.setProductionLimit(8);
			}

			// setup a new Offer for this HT
			player2.getSales().addOffer(new Offer(80000, PFHouseType.MULTI_FAMILY_HOUSE, 10,
					new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS, 12)));

			// make no adjustments for player 1!

			List<Pair<String, Integer>> bestlist = sim.doRoundTrip(player1, player2);
			System.out.println("---------------------------------ROUND 5 begins -------------------------------");

			// just run another round without changes to give the
			// diversification startegic the chance to come back.
			Helper.roundHelper(player1);
			Helper.roundHelper(player2);

			sim.doRoundTrip(player1, player2);
			System.out.println("---------------------------------ROUND 5 begins -------------------------------");
			System.out.println(
					"Let the game end here. Take a look at the current scoring points (Player 1=Enterprise 0. Player 2 = Enterprise 1");
			System.out.println("Exp.: 1: 300 would mean player 2 has 300 scoring points");
			for (int i = 0; i < bestlist.size(); i++) {
				System.out.println(bestlist.get(i).k + ": " + bestlist.get(i).v);
			}
			System.out.println("Expected Result: Player 1 (enterprise 0 wins)");
			//the last player in the winners list has the most points
			System.out.println("Actual Result: Player with Enterprise "+ bestlist.get(1).k+" wins.");
			String e = bestlist.get(1).k;
			assertEquals("0",e );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import net.shared.ClientMessage;
import net.shared.ServerMessage;
import sim.abstraction.Pair;
import sim.procurement.ServerResourceMarket;
import sim.procurement.ResourceType;
import sim.production.PFHouseType;
import sim.simulation.sales.Offer;
import sim.simulation.sales.SalesSimulation;

public class ServerSimulation {
	
	private ServerResourceMarket market;

	private SalesSimulation sales;
	
	private HashMap<String, HashMap<PFHouseType, Integer>> sellings;
	private int round;
	
	public ServerSimulation() {
		market = new ServerResourceMarket();
		sales = new SalesSimulation();
		sellings = new HashMap<>();
		round = 0;
	}
	
	public ServerMessage[] simulationStep(ClientMessage[] clntMsgs){
		++round;
		
		//0. setup the response objects
		ServerMessage[] servMsgs = new ServerMessage[clntMsgs.length];
		
		//1. aggregate all the received data for the market price adjustments
		HashMap<ResourceType, Integer> soldResources = new HashMap<>();
		for (ResourceType t : ResourceType.values()) {
			soldResources.put(t, 0);
		}
		
		for (ClientMessage c : clntMsgs) {
			HashMap<ResourceType, Integer> clnt = c.getBoughtResourceAmounts();
			for (ResourceType t : ResourceType.values()) {
				int current = soldResources.get(t);
				current += clnt.get(t);
				soldResources.put(t, current);
			}
		}
		
		//2. pass this values to the market
		market.adjustPrices(soldResources, clntMsgs.length);
		
		//3. aggregate all the received data for the sales simulation
		HashMap<String, List<Offer>> offers = new HashMap<>();
		for (ClientMessage msg : clntMsgs) {
			offers.put(msg.getName(), msg.getNewCatalog());
		}
		
		//4. pass those values to the sales simulation
		sales.simulateSalesMarket(offers);
		
		//5. is the game finished?
		boolean isFinished = isFinished();
		
		//6. some output :D
		System.out.println();
		System.out.println("<-------------------------------->");
		System.out.println("New Prices:");
		for (Entry<ResourceType, Integer> e : market.getCosts().entrySet()) {
			System.out.println("\t" + e.getKey() + "\t\t" + e.getValue());
		}
		System.out.println("<-------------------------------->");
		System.out.println();
		
		//7. build the top ranks
		for (ClientMessage client : clntMsgs) {
			if(!sellings.containsKey(client.getName())){
				sellings.put(client.getName(), new HashMap<>());
				for (PFHouseType pfHouse : PFHouseType.values()) {
					sellings.get(client.getName()).put(pfHouse, 0);
				}
			}
			
			HashMap<PFHouseType, Integer> houseSellings = sellings.get(client.getName());
			for (Entry<PFHouseType, Integer> e : client.getBuildAmounts().entrySet()) {
				int amount = houseSellings.get(e.getKey());
				amount += e.getValue();
				houseSellings.put(e.getKey(), amount);
			}
			
			sellings.put(client.getName(), houseSellings);
		}
		
		HashMap<String, Integer> scores = new HashMap<>();
		for (Entry<String, HashMap<PFHouseType, Integer>> e : sellings.entrySet()) {
			int score = 0;
			for (Entry<PFHouseType, Integer> type : e.getValue().entrySet()) {
				score += type.getKey().getScoreFactor() * type.getValue();
			}
			scores.put(e.getKey(), score);
		}
		
		List<Pair<String, Integer>> topList = new ArrayList<>();
		for (Entry<String, Integer> pair : scores.entrySet())
			topList.add(new Pair<>(pair.getKey(), pair.getValue()));
		
		topList.sort((left, right) -> {
			if(left.v == right.v)
				return 0;
			if(left.v < right.v)
				return -1;
			
			return 1;
		});
		
		//8. build the response objects
		for (int i = 0; i < servMsgs.length; i++) {
			String name = clntMsgs[i].getName();
			servMsgs[i] = new ServerMessage(round, scores.get(name),
					new HashMap<>(market.getCosts()), 
					new ArrayList<>(offers.get(name)), 
					new HashMap<>(offers), 
					isFinished, new ArrayList<>(topList));
		}
		
		return servMsgs;
	}
	
	public boolean isFinished(){
		//when to end the game? when 25 rounds have been played and round 26 is reached
		return round == 25; //started counting with 0 ;)
	}
	
	public ServerMessage getSetupMessage(){
		return new ServerMessage(0, 0,
				market.getCosts(), 
				new ArrayList<Offer>(), 
				new HashMap<String, List<Offer>>(),
				false,
				new ArrayList<>());
	}
}

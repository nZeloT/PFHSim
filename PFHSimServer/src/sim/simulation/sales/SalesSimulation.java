package sim.simulation.sales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sim.Enterprise;


public class SalesSimulation {


	private int[] enterprises = null;
	private HashMap<Integer, List<Offer>> in = new HashMap<>();
	
	private List<GroupOfBuyers> buyerGroups;
	
	public SalesSimulation() {
		buyerGroups = new ArrayList<>();
//		buyerGroups.add(new CheapBuyer());
//		buyerGroups.add(new PricePerformanceBuyer());
		buyerGroups.add(new ExpensiveBuyer());
	}
	
	public void simulateSalesMarket(HashMap<Integer, List<Offer>> enterpriseoffers) {

		//Get LATEST data from enterprises.
		enterprises = new int[enterpriseoffers.size()];
		int i = 0;
		for (Map.Entry<Integer, List<Offer>> entry : enterpriseoffers.entrySet()) {
			enterprises[i] = entry.getKey();
			
			//reset purchase data from last simulation-round.
			List<Offer> tmp = entry.getValue();  
			for (int k = 0; k < tmp.size(); k++) {
				tmp.get(k).setNumberOfPurchases(0);
			}
			i++;
		}
		 
		for (GroupOfBuyers g : buyerGroups) {
			g.sortOffers(enterpriseoffers);
			in = g.registerPurchases(10, 50, 20, enterprises);
		}
		
	}

}

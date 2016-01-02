package sim.simulation.sales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SalesSimulation {


	private int[] enterprises = null;
	private HashMap<Integer, List<Offer>> in = new HashMap<>();
	
	private List<GroupOfBuyers> buyerGroups;
	
	public SalesSimulation() {
		buyerGroups = new ArrayList<>();
		buyerGroups.add(new CheapBuyer());
//		buyerGroups.add(new PricePerformanceBuyer());
		buyerGroups.add(new ExpensiveBuyer());
	}
	
	public void simulateSalesMarket(HashMap<Integer, List<Offer>> enterpriseoffers) {

		//Get LATEST data from enterprises.
		enterprises = new int[enterpriseoffers.size()];

		 
		for (GroupOfBuyers g : buyerGroups) {
			g.sortOffers(enterpriseoffers);
			in = g.registerPurchases(50, 10, 5, enterprises);
		}
		
	}
	
	public HashMap<Integer, List<Offer>> getSalesData() {
		return in;
	}

}

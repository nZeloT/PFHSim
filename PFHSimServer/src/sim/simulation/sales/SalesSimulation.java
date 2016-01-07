package sim.simulation.sales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SalesSimulation {


	private HashMap<String, List<Offer>> in = new HashMap<>();
	
	private List<GroupOfBuyers> buyerGroups;
	
	public SalesSimulation() {
		buyerGroups = new ArrayList<>();
		buyerGroups.add(new CheapBuyer());
//		buyerGroups.add(new PricePerformanceBuyer());
		buyerGroups.add(new ExpensiveBuyer());
	}
	
	public void simulateSalesMarket(HashMap<String, List<Offer>> enterpriseoffers) {

		//Get LATEST data from enterprises.
		String[] names = enterpriseoffers.keySet().toArray(new String[0]);

		 
		for (GroupOfBuyers g : buyerGroups) {
			g.sortOffers(enterpriseoffers);
			in = g.registerPurchases(50, 10, 5, names);
		}
		
	}
	
	public HashMap<String, List<Offer>> getSalesData() {
		return in;
	}

}

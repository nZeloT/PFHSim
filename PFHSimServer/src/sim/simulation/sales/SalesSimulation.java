package sim.simulation.sales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SalesSimulation {


	private HashMap<String, List<Offer>> in = new HashMap<>();
	
	private List<GroupOfBuyers> buyerGroups;
	private ExpensiveBuyer exp;
	
	public SalesSimulation() {
		buyerGroups = new ArrayList<>();
		buyerGroups.add(new CheapBuyer());
		buyerGroups.add(new PricePerformanceBuyer());
		exp = new ExpensiveBuyer();
		buyerGroups.add(exp);
	}
	
	public void simulateSalesMarket(HashMap<String, List<Offer>> enterpriseoffers) {

		//Get LATEST data from enterprises.
		String[] names = enterpriseoffers.keySet().toArray(new String[0]);

		 
		for (GroupOfBuyers g : buyerGroups) {
			g.sortOffers(enterpriseoffers);
			if (g != exp)
				in = g.registerPurchases(50, 10, 5, names);
			else
				in = g.registerPurchases(15, 1, 1, names);
		}
		
	}
	
	public HashMap<String, List<Offer>> getSalesData() {
		return in;
	}

}

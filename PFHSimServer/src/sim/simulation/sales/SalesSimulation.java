package sim.simulation.sales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;


public class SalesSimulation {


	private HashMap<String, List<Offer>> in = new HashMap<>();
	private int roundsctr = 0;
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
		roundsctr++;
		String[] names = enterpriseoffers.keySet().toArray(new String[0]);
		int numOffEnterprises = names.length;
		//Generate the number of offers based on the round, the number of playing enterprises and some random factors
		int baseAmount = numOffEnterprises * ((int)(Math.random()*5)+15); //between 15 and twenty offers, three players = 45 to 60 offers
		int numoffOffers = (int) (baseAmount*(1.0+roundsctr/8.0)); //increase numoffOffers by rounds played --> simulate that the market is growing

		 
		for (GroupOfBuyers g : buyerGroups) {
			g.sortOffers(enterpriseoffers);
			if (g != exp)
				in = g.registerPurchases(numoffOffers, 20, 15, names);
			else //expensive buyer has its own logic
				in = g.registerPurchases(15, 1, 4, names);
		}
		
	}
	
	public HashMap<String, List<Offer>> getSalesData() {
		return in;
	}

}

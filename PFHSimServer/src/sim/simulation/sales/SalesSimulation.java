package sim.simulation.sales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SalesSimulation {


	private int roundsctr = 0;
	private List<GroupOfBuyers> buyerGroups;
	private HashMap<String, List<Offer>> enterpriseoffers = null;
	
	public SalesSimulation() {
		buyerGroups = new ArrayList<>();
		buyerGroups.add(new CheapBuyer());
		buyerGroups.add(new PricePerformanceBuyer());
		buyerGroups.add(new ExpensiveBuyer());
	}
	
	public void simulateSalesMarket(HashMap<String, List<Offer>> enterpriseoffers) {
		this.enterpriseoffers = enterpriseoffers;

		//Get LATEST data from enterprises.
		roundsctr++;
		String[] names = enterpriseoffers.keySet().toArray(new String[0]);
		int numOffEnterprises = names.length;
		//Generate the number of offers based on the round, the number of playing enterprises and some random factors
		int baseAmount = numOffEnterprises * ((int)(Math.random()*5)+15); //between 15 and twenty offers, three players = 45 to 60 offers
		int numoffOffers = (int) (baseAmount*(1.0+roundsctr/8.0)); //increase numoffOffers by rounds played --> simulate that the market is growing

		 
		for (GroupOfBuyers g : buyerGroups) {
			g.sortOffers(enterpriseoffers);
			if (!(g instanceof ExpensiveBuyer))
				g.registerPurchases(numoffOffers, 20, 15, names);
			else //expensive buyer has its own logic
				g.registerPurchases(15, 1, 4, names);
		}
		
	}
	
	public HashMap<String, List<Offer>> getSalesData() {
		return enterpriseoffers;
	}

}

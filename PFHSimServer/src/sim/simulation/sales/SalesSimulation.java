package sim.simulation.sales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sim.Enterprise;


public class SalesSimulation {


	private HashMap<Enterprise, List<Offer>> in = new HashMap<>();
	
//	private Enterprise[] enterprises;
	
	private List<GroupOfBuyer> buyerGroups;
	
	public SalesSimulation() {
		buyerGroups = new ArrayList<>();
		buyerGroups.add(new CheapBuyer());
//		buyerGroups.add(new PricePerformanceBuyer());
//		buyerGroups.add(new ExpensiveBuyer());
	}
	
	public void simulateSalesMarket(Enterprise... enterprises) {

		//Get LATEST data from enterprises.
//		this.enterprises = enterprises;
		for (int j = 0; j < enterprises.length; j++) {
			List<Offer> offers = enterprises[j].getOffers();
			//Reset number of purchases from last round.
			for (int i = 0; i < offers.size(); i++) {
				offers.get(i).setNumberOfPurchases(0);
			}
			in.put(enterprises[j], offers);
		}
		 
		for (GroupOfBuyer g : buyerGroups) {
			g.sortOffers(in);
			in = g.registerPurchases(500, 10, 20, enterprises);
		}
		
	}

}

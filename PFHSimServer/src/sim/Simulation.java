package sim;

import java.util.HashMap;
import java.util.List;

import sim.simulation.sales.Offer;


public class Simulation {


	private HashMap<Enterprise, List<Offer>> in = new HashMap<>();
	
	@SuppressWarnings("unused")
	private Enterprise[] enterprises;
	
	private CheapBuyer cheapbuyer = new CheapBuyer(); 
	private PricePerformanceBuyer priceperformancebuyer = new PricePerformanceBuyer(); 
	private ExpensiveBuyer expensivebuyer = new ExpensiveBuyer(); 

	
	
	public void simulateSalesMarket(Enterprise... enterprises) {

		//Get LATEST data from enterprises.
		this.enterprises = enterprises;
		for (int j = 0; j < enterprises.length; j++) {
			List<Offer> offers = enterprises[j].getOffers();
			//Reset number of purchases from last round.
			for (int i = 0; i < offers.size(); i++) {
				offers.get(i).setNumberOfPurchases(0);
			}
			in.put(enterprises[j], offers);
		}
		 
		
		cheapbuyer.sortOffers(in);
		in = cheapbuyer.registerPurchases(500, 10, 20, enterprises); 
		
		priceperformancebuyer.sortOffers(in); 
		//in = priceperformancebuyer.registerPurchases(500, 10, 20, enterprises);
		
		expensivebuyer.sortOffers(in);
		//in = expensivebuyer.registerPurchases(500, 10, 20, enterprises);
		
	}

}

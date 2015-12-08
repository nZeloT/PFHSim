package sim;

import java.util.HashMap;
import java.util.List;

import sim.simulation.sales.Offer;

public class PricePerformanceBuyer implements GroupOfBuyer {

	@Override
	public void sortOffers(HashMap<Enterprise, List<Offer>> in) {
		
	}

	@Override
	public HashMap<Enterprise, List<Offer>> registerPurchases(int minAmount, int maxAmount, int step, Enterprise[] e) {
		return null;
	}

}

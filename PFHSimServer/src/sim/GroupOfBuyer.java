package sim;

import java.util.HashMap;
import java.util.List;
import sim.simulation.sales.Offer;

public interface GroupOfBuyer {

	public void sortOffers(HashMap<Enterprise, List<Offer>> in);
	
	public HashMap<Enterprise, List<Offer>> registerPurchases(int minAmount, int maxAmount, int step, Enterprise[] e);
	
}

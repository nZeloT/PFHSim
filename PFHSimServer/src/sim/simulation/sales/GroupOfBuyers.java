package sim.simulation.sales;

import java.util.HashMap;
import java.util.List;

public interface GroupOfBuyers {

	public void sortOffers(HashMap<String, List<Offer>> in);
	
	public HashMap<String, List<Offer>> registerPurchases(int minAmount, int maxAmount, int step, String[] e);
	
}

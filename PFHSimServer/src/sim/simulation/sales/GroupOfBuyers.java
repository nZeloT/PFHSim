package sim.simulation.sales;

import java.util.HashMap;
import java.util.List;

public interface GroupOfBuyers {

	public void sortOffers(HashMap<Integer, List<Offer>> in);
	
	public HashMap<Integer, List<Offer>> registerPurchases(int minAmount, int maxAmount, int step, int[] e);
	
}

package sim.simulation.sales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sim.Enterprise;
import sim.production.PFHouseType;


public class PricePerformanceBuyer implements GroupOfBuyers {

	private HashMap<PFHouseType, List<EnterpriseOfferTupel>> sortedOffers = new HashMap<>();
	
	//TODO implement logic, wait for Corrections first
	
	@Override
	public void sortOffers(HashMap<Integer, List<Offer>> in) {
		//same as cheap Offers. Sort for each category ascending by price
		for (Map.Entry<Integer, List<Offer>> entry : in.entrySet()) {

			List<Offer> offer = entry.getValue();
			for (int i = 0; i < offer.size(); i++) {
				List<EnterpriseOfferTupel> tmp = sortedOffers.get(offer.get(i).getHousetype());
				EnterpriseOfferTupel ot = new EnterpriseOfferTupel(entry.getKey(), offer.get(i));

				if (tmp == null) {
					tmp = new ArrayList<>();
					tmp.add(ot);
					sortedOffers.put(offer.get(i).getHousetype(), tmp);
				} else {
					boolean isBiggest = true;
					for (int j = 0; j < tmp.size(); j++) {
						if (ot.offer.getPrice() <= tmp.get(j).offer.getPrice()) {
							isBiggest = false;
							tmp.add(j, ot);
							sortedOffers.put(offer.get(i).getHousetype(), tmp);
							break;
						}
					}
					if (isBiggest) {
						tmp.add(ot);
						sortedOffers.put(offer.get(i).getHousetype(), tmp);
					}
				}

			}
		}
	}

	@Override
	public HashMap<Integer, List<Offer>> registerPurchases(int minAmount, int maxAmount, int step, int[] e) {
		return null;
	}

}

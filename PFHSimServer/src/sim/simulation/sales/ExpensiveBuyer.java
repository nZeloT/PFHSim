package sim.simulation.sales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sim.production.PFHouseType;

public class ExpensiveBuyer implements GroupOfBuyers {

	private HashMap<PFHouseType, List<EnterpriseOfferTupel>> sortedOffers = new HashMap<>();
	private int numberOfOffers = 0;

	@Override
	public void sortOffers(HashMap<Integer, List<Offer>> in) {

		numberOfOffers = 0;
		for (Map.Entry<Integer, List<Offer>> entry : in.entrySet()) {
			
			List<Offer> offer = entry.getValue();
			for (int i = 0; i < offer.size(); i++) {
				numberOfOffers++;
				if (offer.get(i).getHousetype() == PFHouseType.CITY_VILLA) {
					List<EnterpriseOfferTupel> tmp = sortedOffers.get(offer.get(i).getHousetype());
					EnterpriseOfferTupel ot = new EnterpriseOfferTupel(entry.getKey(), offer.get(i));
					ot.setExpensiveBuyerInterest(ot.offer.getQuality(), ot.offer.getPrice());

					if (tmp == null) {
						tmp = new ArrayList<>();
						tmp.add(ot);
						sortedOffers.put(offer.get(i).getHousetype(), tmp);
					} else {
						boolean isWorst = true;
						for (int j = 0; j < tmp.size(); j++) {
							if (ot.expensiveBuyerInterest >= tmp.get(j).expensiveBuyerInterest) {
								isWorst = false;
								tmp.add(j, ot);
								sortedOffers.put(offer.get(i).getHousetype(), tmp);
								break;
							}
						}
						if (isWorst) {
							tmp.add(ot);
							sortedOffers.put(offer.get(i).getHousetype(), tmp);
						}
					}
				}
			}
		}

	}


	@Override
	public HashMap<Integer, List<Offer>> registerPurchases(int minAmount, int maxAmount, int step, int[] e) {
				
		HashMap<Integer, List<Offer>> results = new HashMap<>();
//		PFHouseType houseteypes[] = PFHouseType.values();
		for (int i = 0; i < e.length; i++) {
			results.put(e[i], new ArrayList<Offer>());
		}

		for (int i = 0; i < sortedOffers.size(); i++) {
			List<EnterpriseOfferTupel> tmp = sortedOffers.get(PFHouseType.CITY_VILLA);
			int demand = (int) (((maxAmount - minAmount) / (1.0*tmp.size()) + minAmount)*tmp.size()); 

			double cumulatedQualities = 0;
			for (int j = 0; j < tmp.size(); j++) {
				cumulatedQualities += tmp.get(j).expensiveBuyerInterest;
			}
			
			for (int j = 0; j < tmp.size(); j++) {
				double ratio = (1.0*tmp.get(j).expensiveBuyerInterest)/cumulatedQualities;
				tmp.get(j).offer.setNumberOfPurchases((int) (ratio * demand));
				
				System.out.println("Enterprise: " + tmp.get(j).enterprise + ", Offer: " + tmp.get(j).offer.getWalltype() + ", no. of purchases: " + tmp.get(j).offer.getNumberOfPurchases());
				
				List<Offer> tmp2 = results.get(tmp.get(j).enterprise);
				tmp2.add(tmp.get(j).offer);
				results.put(tmp.get(j).enterprise, tmp2);
			}
			

			
			
		}
		return results;
	}

}

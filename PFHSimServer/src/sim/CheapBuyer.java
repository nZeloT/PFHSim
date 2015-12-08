package sim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sim.production.PFHouseType;
import sim.simulation.sales.Offer;

public class CheapBuyer implements GroupOfBuyer {

	private HashMap<PFHouseType, List<EnterpriseOfferTupel>> sortedOffers = new HashMap<>();
	
	/**
	 * sort offers in the right Housetype category an ascending by Price for purchase Simulation
	 */
		public void sortOffers(HashMap<Enterprise, List<Offer>> in) {
			
			for (Map.Entry<Enterprise, List<Offer>> entry : in.entrySet()) {
				
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

		
		/**
		 * simulate market consumer group looking for the cheapest prices in every category
		 * @param maxAmount maximal Amount of offers to purchase
		 * @param minAmount minimum Amount of offers to purchase
		 * @param step define by how many offers to decrease the purchase amount for the next offer
		 * The results are written in the result list for purchases
		 */
		@Override
		public HashMap<Enterprise, List<Offer>> registerPurchases(int maxAmount,int minAmount, int step, Enterprise[] e){
			HashMap<Enterprise, List<Offer>> results = new HashMap<>();
			PFHouseType houseteypes[] = PFHouseType.values();
			for (int i = 0; i < results.size(); i++) { 
				results.put(e[i], new ArrayList<Offer>());				
			} 
			
			
			for (int i = 0; i < houseteypes.length; i++) {
				List<EnterpriseOfferTupel> offersForOneCat = sortedOffers.get(houseteypes[i]);
				if (offersForOneCat == null) { //make sure a List of offers exist
					continue;
				}
				int currentAmount = maxAmount;
				int c = 0;
				while (currentAmount >= minAmount) {
					EnterpriseOfferTupel tmp = offersForOneCat.get(c);
					if (tmp == null) {
						break;
					}
					
					tmp.offer.setNumberOfPurchases(tmp.offer.getNumberOfPurchases() + currentAmount);
					List<Offer> tmp2 = results.get(tmp.enterprise);
					tmp2.add(tmp.offer);
					results.put(tmp.enterprise, tmp2);
					
					currentAmount -= step;
					c++;
				}
				
			}
			return results;
		}

		
}

package sim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sim.production.PFHouseType;
import sim.simulation.sales.Offer;

public class Simulation {

	private List<Enterprise> enterprises = new ArrayList<Enterprise>();
	private List<List<Offer>> offerList = new ArrayList<List<Offer>>();
	private List<ResultingPurchases> results = new ArrayList<ResultingPurchases>();

	private HashMap<PFHouseType, List<OfferTupel>> htt;
	
	class ResultingPurchases{
		public OfferTupel succOffer;
		public int amountofPurchases;
		
		public ResultingPurchases(OfferTupel offer, int amount) {
			this.succOffer = offer;
			this.amountofPurchases = amount;
		}
	}

	class OfferTupel {
		public Offer offer;
		public Enterprise enterprise;

		public OfferTupel(Offer offer, Enterprise enterprise) {
			this.offer = offer;
			this.enterprise = enterprise;
		}
	}

	public Simulation(List<Enterprise> e) {
		enterprises = e;
		PFHouseType[] allHouseTypes = PFHouseType.values();
		htt = new HashMap<>();

	}
/**
 * sort offers in the right Housetype category an ascending by Price for purchase Simulation
 */
	public void sortOffers() {

		for (int j = 0; j < enterprises.size(); j++) {
			offerList.add(enterprises.get(j).getOffers());
		}

		for (int k = 0; k < offerList.size(); k++) {
			List<Offer> offer = offerList.get(k);
			for (int i = 0; i < offer.size(); i++) {
				List<OfferTupel> tmp = htt.get(offer.get(i).getHousetype());
				OfferTupel ot = new OfferTupel(offer.get(i), enterprises.get(k));

				if (tmp == null) {
					tmp = new ArrayList<>();
					tmp.add(ot);
					htt.put(offer.get(i).getHousetype(), tmp);
				} else {
					boolean isBiggest = true;
					for (int j = 0; j < tmp.size(); j++) {
						if (ot.offer.getPrice() <= tmp.get(j).offer.getPrice()) {
							isBiggest = false;
							tmp.add(j, ot);
							htt.put(offer.get(i).getHousetype(), tmp);
							break;
						}
					}
					if (isBiggest) {
						tmp.add(ot);
						htt.put(offer.get(i).getHousetype(), tmp);
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
	public void buyCheapHouses(int maxAmount,int minAmount, int step){
		PFHouseType houseteypes[] = PFHouseType.values();
		for (int i = 0; i < houseteypes.length; i++) {
			List<Simulation.OfferTupel> offersForOneCat = htt.get(houseteypes[i]);
			if (offersForOneCat == null) { //make sure a List of offers exist
				continue;
			}
			int currentAmount = maxAmount;
			int c = 0;
			while (currentAmount >= minAmount) {
				OfferTupel offer = offersForOneCat.get(c);
				if (offer == null) {
					break;
				}
				results.add(new ResultingPurchases(offer, currentAmount));
				currentAmount -= step;
				c++;
			}
			
		}
	}
}

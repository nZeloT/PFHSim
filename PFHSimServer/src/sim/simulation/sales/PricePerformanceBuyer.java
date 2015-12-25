package sim.simulation.sales;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import sim.abstraction.Tupel;
import sim.production.PFHouseType;
import sim.production.WallType;

public class PricePerformanceBuyer implements GroupOfBuyers {

	private HashMap<PFHouseType, List<EnterpriseOfferTupel>> sortedlightOffers = new HashMap<>();
	private HashMap<PFHouseType, List<EnterpriseOfferTupel>> sortedmassiveOffers = new HashMap<>();
	private final int numberofHTs = (int) (Math.random() * 2) + 3;
	private final int quality = 1;
	// TODO add quality scala
	private final List<PFHouseType> types = Arrays.asList(Arrays.copyOf(PFHouseType.values(), numberofHTs));
	private int numberofOffers = 0;

	/**
	 * Map offers in a HashMap with the right Housetype category and ascending
	 * by Price. Two separate lists to honor each the best wood/brick wall
	 * constructions Ignore offers with bad isolation (--> high running costs,
	 * nothing for PricePerformanceBuyers) Ignore offers with low quality
	 */
	public void sortOffers(HashMap<Integer, List<Offer>> in) {

		for (Map.Entry<Integer, List<Offer>> entry : in.entrySet()) {

			List<Offer> offers = entry.getValue();
			HashMap<PFHouseType, List<EnterpriseOfferTupel>> placeholder = null;
			for (int i = 0; i < offers.size(); i++) {
				// first sort out offers which are not Interesting for the
				// PricePerformace Buyers, therefore check if the offers use
				// Walls with bad isolation
				PFHouseType type = offers.get(i).getHousetype();
				if (!types.contains(type)) {
					continue; // type to expensive for this purchase category
				}
				Tupel<WallType>[] walltypes = offers.get(i).getWalltype();
				boolean isInteresting = true;
				for (int j = 0; j < walltypes.length; j++) {
					if (walltypes[j].type == WallType.LIGHT_WEIGHT_CONSTRUCTION
							|| walltypes[j].type == WallType.MASSIVE_LIGHT_CONSTRUCTION) {
						isInteresting = false;
						break; // offer uses bad walls or low quality, stop
								// check here

					} else { // find out if light or massive wall
						if (walltypes[j].type == WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS) {
							placeholder = sortedlightOffers;
						} else {
							placeholder = sortedmassiveOffers;
						}
					}
				}
				if (!isInteresting) {
					continue; // continue with the next offer
				}
				// count the interesting offers
				numberofOffers += offers.get(i).getProductionLimit();
				List<EnterpriseOfferTupel> tmp = placeholder.get(type);
				EnterpriseOfferTupel ot = new EnterpriseOfferTupel(entry.getKey(), offers.get(i));

				if (tmp == null) {
					tmp = new ArrayList<>();
					tmp.add(ot);
					placeholder.put(offers.get(i).getHousetype(), tmp);
				} else {
					boolean isWorst = true;
					for (int j = 0; j < tmp.size(); j++) {
						if (ot.offer.getPrice() <= tmp.get(j).offer.getPrice()) {
							isWorst = false;
							tmp.add(j, ot);
							placeholder.put(offers.get(i).getHousetype(), tmp);
							break;
						}
					}
					if (isWorst) {
						tmp.add(ot);
						placeholder.put(offers.get(i).getHousetype(), tmp);
					}
				}

			}
		}
	}

	/**
	 * Go over each category. Price Performance Buyers are looking for higher
	 * quality houses (Plus constructions) with good isolation (low running
	 * costs) Split the purchases for brick and wood wall (light/massive plus
	 * constructions). Honor each the best offer and the over ones descending
	 * 
	 * @param minAmount
	 *            lower threshold of all housetypes to be bought (smallest
	 *            amount)
	 * @param maxAmount
	 *            highest amount of houses to be bought
	 * @param step
	 *            amount to decrease for the next best offer
	 * @param the
	 *            enterprises to split
	 */
	@Override
	public HashMap<Integer, List<Offer>> registerPurchases(int minAmount, int maxAmount, int step, int[] e) {
		numberofOffers = (int) (numberofOffers * (Math.random() * 0.2 + 0.7));
		HashMap<Integer, List<Offer>> results = new HashMap<>();
		HashMap<PFHouseType, List<EnterpriseOfferTupel>> placeholder = sortedlightOffers;
		for (int i = 0; i < e.length; i++) {
			results.put(e[i], new ArrayList<Offer>());
		}

		for (int k = 0; k < 2; k++) {
			int current = maxAmount;
			if (k == 1) {
			}
			for (Entry<PFHouseType, List<EnterpriseOfferTupel>> entry : placeholder.entrySet()) {
				List<EnterpriseOfferTupel> succoff = entry.getValue();
				for (int i = 0; i < succoff.size(); i++, current -= step) {
					Offer actual = succoff.get(i).offer;
					int max = actual.getProductionLimit();
					int amount = actual.getNumberOfPurchases();
					if (max == amount) {
						continue; // other buyers already bought all the houses
									// of this offer. Continue with the next
									// offer.
					}
					if (max - amount >= current) { // can build all houses
						actual.setNumberOfPurchases(current);
					} else {
						actual.setNumberOfPurchases(max - amount);
						current += current - (max - amount); // add the open
															// interests for the
															// next player,
															// looks strange but
															// works right ;)
					}
					List<Offer> tmp = results.get(succoff.get(i).enterprise);
					tmp.add(actual);
					results.put(succoff.get(i).enterprise, tmp);
				}
			}
		}
		return results;
	}

}

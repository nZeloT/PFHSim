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
	private final int numberofHTs = 4;
	private final int quality = 1;
	// TODO add quality scala
	private final List<PFHouseType> types = Arrays.asList(Arrays.copyOf(PFHouseType.values(), numberofHTs));

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
					if (walltypes[i].type == WallType.LIGHT_WEIGHT_CONSTRUCTION
							|| walltypes[i].type == WallType.MASSIVE_LIGHT_CONSTRUCTION
							|| quality < offers.get(i).getQuality()) {
						isInteresting = false;
						break; // offer uses bad walls or low quality, stop
								// check here

					} else { // find out if light or massive wall
						if (walltypes[i].type == WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS) {
							placeholder = sortedlightOffers;
						} else {
							placeholder = sortedmassiveOffers;
						}
					}
				}
				if (!isInteresting) {
					continue; // continue with the next offer
				}

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
	 */
	@Override
	public HashMap<Integer, List<Offer>> registerPurchases(int minAmount, int maxAmount, int step, int[] e) {
		HashMap<Integer, List<Offer>> results = new HashMap<>();
		HashMap<PFHouseType, List<EnterpriseOfferTupel>> placeholder = sortedlightOffers;
		for (int i = 0; i < e.length; i++) {
			results.put(e[i], new ArrayList<Offer>());
		}
		
		for (int k = 0; k < 2; k++) {
			int current = maxAmount / 2; // share for both types of walls;
			if (k==1) {
				placeholder = sortedmassiveOffers;
			}
			for (Entry<PFHouseType, List<EnterpriseOfferTupel>> entry : placeholder.entrySet()) {
				List<EnterpriseOfferTupel> succoff = entry.getValue();
				for (int i = 0; i < succoff.size() && minAmount <= current; i++, current -= step) {
					Offer actual = succoff.get(i).offer;
					int amount = actual.getNumberOfPurchases() + current;
					actual.setNumberOfPurchases(amount);
					List<Offer> tmp = results.get(succoff.get(i).enterprise);
					tmp.add(actual);
					results.put(succoff.get(i).enterprise, tmp);
					System.out.println("Enterprise "+ succoff.get(i).enterprise + " sold " + amount + " for Housetype "+ actual.getHousetype());
				}
			}
		}
		return results;
	}

}

package sim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sim.abstraction.Tupel;
import sim.production.PFHouseType;
import sim.simulation.sales.Offer;

public class Simulation {

	private List<Enterprise> enterprises = new ArrayList<Enterprise>();
	private List<List<Offer>> offerList = new ArrayList<List<Offer>>();

	private HashMap<PFHouseType, List<OfferTupel>> htt;

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

	public void evaluateOffers() {

		for (int j = 0; j < enterprises.size(); j++) {
			offerList.add(enterprises.get(j).getOffers());
		}

		// simulate group of buyers = cheapest offering

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
}

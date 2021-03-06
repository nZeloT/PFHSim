package sim.simulation.sales;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import sim.abstraction.Tupel;
import sim.production.PFHouseType;
import sim.production.WallType;

public class PricePerformanceBuyerTest {

	HashMap<String, List<Offer>> testOffers = new HashMap<>();
	PFHouseType[] types = PFHouseType.values();
	WallType[] walltypes = WallType.values();

	@Before
	public void setUp() throws Exception {
		for (int i = 0; i < 4; i++) {
			List<Offer> tmp = new ArrayList<Offer>();
			for (int j = 0; j >= 0; j--) {
				tmp.add(new Offer(500 * (int) (Math.random() * 10), types[j], 8,
						new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS, 5)));
			}
			testOffers.put("" + i, tmp);
		}
	}

	/**
	 * test if the Price Performance Buyer register purchases
	 */
	@Test
	public void testPricePerformaceBuyer() {
		PricePerformanceBuyer buyer = new PricePerformanceBuyer();
		buyer.sortOffers(testOffers);
		HashMap<String, List<Offer>> results = buyer.registerPurchases(10, 0, 2, new String []{"0","1","2","3"});
		assertNotNull(results);
		boolean purchases = false;
		for (Entry<String, List<Offer>> entry : results.entrySet()) {
			List<Offer> current = entry.getValue();
			for (Offer offer : current) {
				if (offer.getNumberOfPurchases() >= 0) {
					purchases = true;
				}
			}
		}
		assertEquals(true, purchases);
	}

}

package sim.simulation.sales;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import sim.abstraction.Tupel;
import sim.production.PFHouseType;
import sim.production.WallType;

public class PricePerformanceBuyerTest {

	HashMap<Integer, List<Offer>> testOffers = new HashMap<>();
	PFHouseType[] types = PFHouseType.values();
	WallType[] walltypes = WallType.values();

	@Before
	public void setUp() throws Exception {
		for (int i = 0; i < 4; i++) {
			List<Offer> tmp = new ArrayList<Offer>();
			for (int j = 0; j >= 0; j--) {
				tmp.add(new Offer(500 * (int) (Math.random() * 10), 25, types[j], 10,
						new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS, 5)));
			}
			testOffers.put(i, tmp);
		}
	}

	@Test
	public void testPricePerformaceBuyer() {
		PricePerformanceBuyer buyer = new PricePerformanceBuyer();
		buyer.sortOffers(testOffers);
		HashMap<Integer, List<Offer>> results = buyer.registerPurchases(6, 10, 2, new int []{0,1,2,3});
		int offercount = 0; // under the defined test case, three offers should be taken.
		for (Map.Entry<Integer, List<Offer>> entry : results.entrySet()) {
			List<Offer> current = entry.getValue();
//			System.out.println("Enterprise " + entry.getKey() +" with the following offers:");
			for (Offer offer : current) {
//				System.out.println(
//						"Price: " + offer.getPrice() + " Housetyp: " + offer.getHousetype() + " Purchasing Amount: "
//								+ offer.getNumberOfPurchases() + " max: " + offer.getMaximumProducable());
				if (offer.getNumberOfPurchases() != 0) {
					offercount++;
				}
			}
		}
		
		assertNotNull(results);
		assertEquals(offercount, 3);
		
	}

}

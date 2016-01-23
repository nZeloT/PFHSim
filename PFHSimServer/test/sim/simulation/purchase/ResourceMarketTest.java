
package sim.simulation.purchase;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import sim.procurement.ServerResourceMarket;
import sim.procurement.ResourceType;

public class ResourceMarketTest {

	private ServerResourceMarket market;

	@Before
	public void setUp() throws Exception {
		market = new ServerResourceMarket();
	}

	@Test
	public void testLowerPrices(){
		HashMap<ResourceType, Integer> amount = new HashMap<>();
		HashMap<ResourceType, Integer> price  = new HashMap<>();

		for (ResourceType t : ResourceType.values()) {
			amount.put(t, 50);
			price.put(t, (int) (t.getBasePrice() * 0.85));
		}

		market.adjustPrices(amount,2);

		for (ResourceType t : ResourceType.values()) {
			assertEquals(price.get(t), market.getCosts().get(t));
		}
	}

	@Test
	public void testIncreasePrices(){
		HashMap<ResourceType, Integer> amount = new HashMap<>();
		HashMap<ResourceType, Integer> price  = new HashMap<>();

		for (ResourceType t : ResourceType.values()) {
			amount.put(t, 300);
			price.put(t, (int) (t.getBasePrice() * 1.15));
		}

		market.adjustPrices(amount,2);

		for (ResourceType t : ResourceType.values()) {
			assertEquals(price.get(t), market.getCosts().get(t));
		}
	}

}

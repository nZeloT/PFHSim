
package sim.procurement;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import sim.TestUtils;

public class ResourceMarketTest {
	
	private ResourceMarket market;
	
	@Before
	public void setUp() throws Exception {
		market = TestUtils.getMarket();
	}
	
	//TODO: what was this test for? didn't got it ...
//	@Test
//	public void testInstance() {
//	
//		HashMap<ResourceType,ResourceListItem>  inventory = new HashMap<ResourceType,ResourceListItem>();
//		inventory.put(ResourceType.WOOD, new ResourceListItem(ResourceType.WOOD));
//		inventory.put(ResourceType.INSULATION, new ResourceListItem(ResourceType.INSULATION));
//		assertEquals(marketinventory.get(ResourceType.INSULATION).getCosts(), inventory.get(ResourceType.INSULATION).getCosts());
//		assertEquals(marketinventory.get(ResourceType.WOOD).get(50)[0].getType(), ResourceType.WOOD);
//	}

	@Test
	public void testSellResources() {
		try {
			Resource[] a = market.buyResources(ResourceType.INSULATION, 1);
			assertEquals(a[0].getType(), ResourceType.INSULATION);
			assertEquals(a.length, 1);
			assertEquals(market.getSoldResources().get(ResourceType.INSULATION).intValue(), 1);
			
			Resource[] b = market.buyResources(ResourceType.INSULATION, 20);
			assertEquals(b[0].getType(), ResourceType.INSULATION);
			assertEquals(b.length, 20);
			
			assertEquals(market.getSoldResources().get(ResourceType.INSULATION).intValue(),21);
		} catch (ResourceMarketException e) {
			e.printStackTrace();
		}
	}
}

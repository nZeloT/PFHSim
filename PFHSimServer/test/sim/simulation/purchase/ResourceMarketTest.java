
package sim.simulation.purchase;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import sim.procurement.Resource;
import sim.procurement.ResourceType;

public class ResourceMarketTest {
	
	private HashMap<ResourceType,ResourceListItem> marketinventory;
	private ResourceMarket market;
	
	@Before
	public void setUp() throws Exception {
		market = new ResourceMarket();
		marketinventory = market.getResources();
	}
	
	

	@Test
	public void testInstance() {
	
		HashMap<ResourceType,ResourceListItem>  inventory = new HashMap<ResourceType,ResourceListItem>();
		inventory.put(ResourceType.WOOD, new ResourceListItem(10, ResourceType.WOOD));
		inventory.put(ResourceType.INSULATION, new ResourceListItem(10, ResourceType.INSULATION));
		assertEquals(marketinventory.get(ResourceType.INSULATION).getCosts(), inventory.get(ResourceType.INSULATION).getCosts());
		assertEquals(marketinventory.get(ResourceType.WOOD).get(50)[0].getType(), ResourceType.WOOD);
	}

	@Test
	public void testSellResources() {
		try {
			Resource[] a = market.sellResources(ResourceType.INSULATION, 1);
			assertEquals(a[0].getType(), ResourceType.INSULATION);
			assertEquals(a.length, 1);
			assertEquals(marketinventory.get(ResourceType.INSULATION).getAmountofSoldItems(),1);
			
			Resource[] b = market.sellResources(ResourceType.INSULATION, 20);
			assertEquals(b[0].getType(), ResourceType.INSULATION);
			assertEquals(b.length, 20);
			
			assertEquals(marketinventory.get(ResourceType.INSULATION).getAmountofSoldItems(),21);
		} catch (ResourceMarketException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLowerPrices(){
		try {
			ResourceType[] types = ResourceType.values();
			int[] newprices = new int[types.length];
			for (int i = 0; i < types.length; i++) {
				market.sellResources(types[i], 100);
				newprices[i] = (int) (marketinventory.get(types[i]).getCosts() * 0.85);
			}
			market.adjustPrices();
			for (int i = 0; i < types.length; i++) {
				assertEquals(marketinventory.get(types[i]).getCosts(), newprices[i]);
			}
		} catch (ResourceMarketException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testincreasePrices(){
		try {
			ResourceType[] types = ResourceType.values();
			int[] newprices = new int[types.length];
			for (int i = 0; i < types.length; i++) {
				market.sellResources(types[i], 300);
				newprices[i] = (int) (marketinventory.get(types[i]).getCosts() * 1.15);
			}
			market.adjustPrices();
			for (int i = 0; i < types.length; i++) {
				assertEquals(marketinventory.get(types[i]).getCosts(), newprices[i]);
			}
		} catch (ResourceMarketException e) {
			e.printStackTrace();
		}
	}
	
}

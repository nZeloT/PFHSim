package sim.simulation.purchase;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import sim.procurement.Resource;
import sim.procurement.ResourceType;

public class ResourceListTest {
	
	private ResourceListItem resource;
	
	@Before
	public void setUp() throws Exception {
		resource = new ResourceListItem(10, ResourceType.INSULATION);
	}


	@Test
	public void testGet() {
		Resource[] fromMarket = resource.get(30);
		assertEquals(fromMarket.length, 30);
		
		resource.get(55);
		assertEquals(resource.getAmountofSoldItems(),85);
	}

	

}

package sim.simulation.sales;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import sim.abstraction.Tupel;
import sim.production.PFHouseType;
import sim.production.WallType;

public class CheapBuyerTest {

	HashMap<String, List<Offer>> enterpriseoffer = new HashMap<>();

	@Before
	public void setUp() throws Exception {
		
		//expensive buyer test 
		List<Offer> tmp = Arrays.asList((new Offer(100000, PFHouseType.BUNGALOW, 100, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5))));
		enterpriseoffer.put("A", tmp);
		tmp = Arrays.asList((new Offer(30000, PFHouseType.BUNGALOW, 100, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5))));
		enterpriseoffer.put("B", tmp);
	}
 
	@Test 
	public void testSortAndReturn() {
 
		SalesSimulation s = new SalesSimulation(); 
		
		s.simulateSalesMarket(enterpriseoffer);
		HashMap<String, List<Offer>> sales = s.getSalesData();
		System.out.println(sales.get("B").size());
		assertTrue(sales.get("B").get(0).getNumberOfPurchases() > sales.get("A").get(0).getNumberOfPurchases());
		
	}
}

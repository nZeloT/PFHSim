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
import sim.simulation.sales.Offer;
import sim.simulation.sales.SalesSimulation;

public class ExpensiveBuyerTest {

	HashMap<String, List<Offer>> enterpriseoffer = new HashMap<>();

	@Before
	public void setUp() throws Exception {
		
		//expensive buyer test 
		List<Offer> tmp = Arrays.asList((new Offer(1500000, PFHouseType.CITY_VILLA, 100, new Tupel<WallType>(WallType.PANORAMA_WALL, 8))));
		enterpriseoffer.put("A", tmp);
		tmp = Arrays.asList((new Offer(500000, PFHouseType.CITY_VILLA, 100, new Tupel<WallType>(WallType.PANORAMA_WALL, 3), new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5))));
		enterpriseoffer.put("B", tmp);
		tmp = Arrays.asList((new Offer(1000000, PFHouseType.TRENDHOUSE, 100, new Tupel<WallType>(WallType.PANORAMA_WALL, 3), new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5))));
		enterpriseoffer.put("C", tmp);
		tmp = Arrays.asList((new Offer(1000000, PFHouseType.TRENDHOUSE, 100, new Tupel<WallType>(WallType.PANORAMA_WALL, 7), new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS, 1))));
		enterpriseoffer.put("D", tmp);
	}
 
	@Test
	public void testSortAndReturn() {
 
		SalesSimulation s = new SalesSimulation(); 
		
		s.simulateSalesMarket(enterpriseoffer);
		HashMap<String, List<Offer>> sales = s.getSalesData();
		assertTrue(sales.get("B").get(0).getNumberOfPurchases() > sales.get("A").get(0).getNumberOfPurchases());
		assertTrue(sales.get("D").get(0).getNumberOfPurchases() > sales.get("C").get(0).getNumberOfPurchases());
		
	}

}

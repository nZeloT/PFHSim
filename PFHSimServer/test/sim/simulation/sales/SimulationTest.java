package sim.simulation.sales;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import sim.abstraction.Tupel;
import sim.production.PFHouseType;
import sim.production.WallType;

public class SimulationTest {

	HashMap<Integer, List<Offer>> enterpriseoffer = new HashMap<>();

	@Before
	public void setUp() throws Exception {

		List<Offer> tmp = Arrays.asList(
				(new Offer(5000, PFHouseType.BLOCK_HOUSE, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5))),
				(new Offer(7000, PFHouseType.BLOCK_HOUSE, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5))),
				(new Offer(5000, PFHouseType.BUNGALOW, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5))),
				(new Offer(7000, PFHouseType.BUNGALOW, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5))));
		enterpriseoffer.put(0, tmp);
		tmp = Arrays.asList(
				(new Offer(6000, PFHouseType.BLOCK_HOUSE, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5))),
				(new Offer(8000, PFHouseType.BLOCK_HOUSE, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5))),
				(new Offer(6000, PFHouseType.BUNGALOW, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5))),
				(new Offer(8000, PFHouseType.BUNGALOW, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5))));
		enterpriseoffer.put(1, tmp);
	}
 
	@Test
	public void testSortAndReturn() {
 
		SalesSimulation s = new SalesSimulation(); 

		s.simulateSalesMarket(enterpriseoffer);

	}

}

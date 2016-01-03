package sim.simulation.sales;

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

public class SimulationTest {

	HashMap<String, List<Offer>> enterpriseoffer = new HashMap<>();

	@Before
	public void setUp() throws Exception {
/*
 		//Cheap buyer test
		List<Offer> tmp = Arrays.asList(
				(new Offer(5000, 1, PFHouseType.BLOCK_HOUSE, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5))),
				(new Offer(7000, 1, PFHouseType.BLOCK_HOUSE, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5))),
				(new Offer(5000, 1, PFHouseType.BUNGALOW, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5))),
				(new Offer(7000, 1, PFHouseType.BUNGALOW, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5))));
		enterpriseoffer.put(0, tmp);
		tmp = Arrays.asList(
				(new Offer(6000, 1, PFHouseType.BLOCK_HOUSE, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5))),
				(new Offer(8000, 1, PFHouseType.BLOCK_HOUSE, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5))),
				(new Offer(6000, 1, PFHouseType.BUNGALOW, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5))),
				(new Offer(8000, 1, PFHouseType.BUNGALOW, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5))));
		enterpriseoffer.put(1, tmp);*/
		
		//expensive buyer test 
		List<Offer> tmp = Arrays.asList((new Offer(1500000, 1, PFHouseType.CITY_VILLA, 100, new Tupel<WallType>(WallType.PANORAMA_WALL, 8))));
		enterpriseoffer.put("A", tmp);
		tmp = Arrays.asList((new Offer(500000, 1, PFHouseType.CITY_VILLA, 100, new Tupel<WallType>(WallType.PANORAMA_WALL, 3), new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5))));
		enterpriseoffer.put("B", tmp);
	}
 
	@Test
	public void testSortAndReturn() {
 
		SalesSimulation s = new SalesSimulation(); 

		s.simulateSalesMarket(enterpriseoffer);

	}

}

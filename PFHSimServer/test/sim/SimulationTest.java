package sim;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import sim.abstraction.Tupel;
import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.production.PFHouseType;
import sim.production.WallType;
import sim.simulation.sales.Offer;

public class SimulationTest {

	@Test
	public void test() {

		Enterprise e = TestUtils.initializeEnterprise();
		Enterprise e2 = TestUtils.initializeEnterprise();
		

		
		e.setOffers(new Offer(5000, PFHouseType.BLOCK_HOUSE, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5)));
		e.setOffers(new Offer(7000, PFHouseType.BLOCK_HOUSE, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5)));
		e.setOffers(new Offer(5000, PFHouseType.BUNGALOW, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5)));
		e.setOffers(new Offer(7000, PFHouseType.BUNGALOW, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5)));
		
		e2.setOffers(new Offer(6000, PFHouseType.BLOCK_HOUSE, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5)));
		e2.setOffers(new Offer(8000, PFHouseType.BLOCK_HOUSE, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5)));
		e2.setOffers(new Offer(6000, PFHouseType.BUNGALOW, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5)));
		e2.setOffers(new Offer(8000, PFHouseType.BUNGALOW, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5)));
		
		
		Simulation s = new Simulation(Arrays.asList(e, e2));
		s.evaluateOffers();
		
		
		
	}

}

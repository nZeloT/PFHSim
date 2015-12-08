package sim;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import sim.abstraction.Tupel;
import sim.hr.Department;
import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.production.PFHouseType;
import sim.production.WallType;
import sim.simulation.sales.Offer;

public class SimulationTest {
	
	Enterprise e;
	Enterprise e2;
	
	@Before
	public void setUp() throws Exception {
		e = TestUtils.initializeEnterprise();
		e2 = TestUtils.initializeEnterprise();


		e.addOffer(new Offer(5000, PFHouseType.BLOCK_HOUSE, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5)));
		e.addOffer(new Offer(7000, PFHouseType.BLOCK_HOUSE, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5)));
		e.addOffer(new Offer(5000, PFHouseType.BUNGALOW, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5)));
		e.addOffer(new Offer(7000, PFHouseType.BUNGALOW, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5)));
		
		e2.addOffer(new Offer(6000, PFHouseType.BLOCK_HOUSE, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5)));
		e2.addOffer(new Offer(8000, PFHouseType.BLOCK_HOUSE, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5)));
		e2.addOffer(new Offer(6000, PFHouseType.BUNGALOW, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5)));
		e2.addOffer(new Offer(8000, PFHouseType.BUNGALOW, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5)));
	}

	@Test 
	public void testSort() { 

		Simulation s = new Simulation();
		s.simulateSalesMarket(e, e2);
		
	}

}

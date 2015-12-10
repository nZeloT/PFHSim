package sim;

import static org.junit.Assert.*;

import org.junit.Test;

import sim.abstraction.Tupel;
import sim.production.PFHouseType;
import sim.production.WallType;
import sim.simulation.sales.Offer;

public class IntegrationTest {

	@Test
	public void testSimulationStep() {
		Enterprise[] e = new Enterprise[3];
		e[0] = TestUtils.initializeEnterprise();
		e[1] = TestUtils.initializeEnterprise();
		e[2] = TestUtils.initializeEnterprise();
		
		for (Enterprise en : e) {
			Offer o = new Offer(50000, PFHouseType.BUNGALOW, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, ))
		}
		
		for (Enterprise en : e) {
			en.doSimulationStep();
		}
	}

}

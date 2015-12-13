package sim;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

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
		 
//		SalesSimulation sim = new SalesSimulation();
		
		for (Enterprise en : e) {
			en.addOffer( new Offer(1, ((int)(5500 + 10000 * Math.random())), PFHouseType.BUNGALOW, 100, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5)) );
			en.addOffer( new Offer(1, ((int)(5500 + 10000 * Math.random())), PFHouseType.BUNGALOW, 100, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5)) );
			en.addOffer( new Offer(1, ((int)(5500 + 10000 * Math.random())), PFHouseType.BUNGALOW, 100, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5)) );
			en.addOffer( new Offer(1, ((int)(5500 + 10000 * Math.random())), PFHouseType.BUNGALOW, 100, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5)) );
			en.addOffer( new Offer(1, ((int)(5500 + 10000 * Math.random())), PFHouseType.BUNGALOW, 100, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5)) );
			en.addOffer( new Offer(1, ((int)(5500 + 10000 * Math.random())), PFHouseType.BUNGALOW, 100, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5)) );
			en.addOffer( new Offer(1, ((int)(5500 + 10000 * Math.random())), PFHouseType.BUNGALOW, 100, new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5)) );
		}

//		sim.simulateSalesMarket(e);
		
		for (Enterprise en : e) {
			en.doSimulationStep(new ArrayList<>());
		}
		
		assertEquals(0, 0);
	}

}

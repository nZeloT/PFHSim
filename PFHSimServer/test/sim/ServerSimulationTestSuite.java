package sim;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import sim.simulation.purchase.ResourceMarketTest;
import sim.simulation.sales.SimulationTest;

@RunWith(Suite.class)
@SuiteClasses({
	ResourceMarketTest.class,
	SimulationTest.class
})
public class ServerSimulationTestSuite {

}

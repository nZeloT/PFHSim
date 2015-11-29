package sim;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import sim.production.TestMachine;
import sim.simulation.purchase.ResourceListTest;
import sim.simulation.purchase.ResourceMarketTest;
import sim.warehouse.TestWarehouse;

@RunWith(Suite.class)
@SuiteClasses({
	TestMachine.class,
	TestWarehouse.class,
	ResourceListTest.class,
	ResourceMarketTest.class
})
public class SimulationTestSuite {

}

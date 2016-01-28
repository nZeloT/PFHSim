package sim;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import sim.simulation.purchase.ResourceMarketTest;
import sim.simulation.sales.CheapBuyerTest;
import sim.simulation.sales.ExpensiveBuyerTest;
import sim.simulation.sales.PricePerformanceBuyerTest;

@RunWith(Suite.class)
@SuiteClasses({
	ResourceMarketTest.class,
	ExpensiveBuyerTest.class,
	CheapBuyerTest.class,
	PricePerformanceBuyerTest.class
})
public class ServerSimulationTestSuite {

}

package sim;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import sim.hr.EmployeeTest;
import sim.hr.HRTest;
import sim.production.TestMachine;
import sim.research.dev.UpgradeTest;
import sim.simulation.purchase.ResourceListTest;
import sim.simulation.purchase.ResourceMarketTest;
import sim.warehouse.TestWarehouse;

@RunWith(Suite.class)
@SuiteClasses({
	EmployeeTest.class,
	HRTest.class,
	TestWarehouse.class,
	TestMachine.class,
	ResourceListTest.class,
	ResourceMarketTest.class,
	EnterpriseTest.class,
	UpgradeTest.class
})
public class SimulationTestSuite {

}

package sim;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import sim.hr.EmployeeTest;
import sim.hr.HRTest;
import sim.procurement.ResourceMarketTest;
import sim.production.TestMachine;
import sim.research.dev.UpgradeTest;
import sim.warehouse.TestWarehouse;

@RunWith(Suite.class)
@SuiteClasses({
	EmployeeTest.class,
	HRTest.class,
	TestWarehouse.class,
	TestMachine.class,
	ResourceMarketTest.class,
	EnterpriseTest.class,
	UpgradeTest.class,
	IntegrationTest.class
})
public class ClientSimulationTestSuite {

}

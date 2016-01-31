import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import sim.EnterpriseTest;
import sim.hr.EmployeeTest;
import sim.hr.HRTest;
import sim.procurement.ResourceMarketTest;
import sim.production.PFHouseTypeTest;
import sim.production.TestMachine;
import sim.research.dev.UpgradeTest;
import sim.simulation.sales.CheapBuyerTest;
import sim.simulation.sales.ExpensiveBuyerTest;
import sim.simulation.sales.PricePerformanceBuyerTest;
import sim.warehouse.TestWarehouse;

@RunWith(Suite.class)
@SuiteClasses({ ScenarioTest1.class,
				ScenarioTest2.class,
				EmployeeTest.class,
				HRTest.class,
				TestWarehouse.class,
				TestMachine.class,
				ResourceMarketTest.class,
				sim.simulation.purchase.ResourceMarketTest.class,
				PFHouseTypeTest.class,
				EnterpriseTest.class,
				UpgradeTest.class,
				ResourceMarketTest.class,
				ExpensiveBuyerTest.class,
				CheapBuyerTest.class,
				PricePerformanceBuyerTest.class})
public class TestSuiteAllTests {

}

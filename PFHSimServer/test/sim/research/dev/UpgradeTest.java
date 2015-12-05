package sim.research.dev;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import sim.Enterprise;
import sim.TestUtils;
import sim.hr.Department;
import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.production.Machine;
import sim.production.PFHouseType;
import sim.warehouse.Warehouse;

public class UpgradeTest {

	private UpgradeProcessor processor;
	private Enterprise e;

	@Before
	public void setUp() throws Exception {
		e = TestUtils.initializeEnterprise();
		processor = e.getUpgradeProcessor();
	}

	@Test
	public void testUpgradeSetup() {
		//1. start by sending an employee on training
		Employee emp = e.getHR().hire(EmployeeType.HR);
		Department d = new Department(EmployeeType.HR);
		emp.assignWorkplace(d);
		assertEquals(true, processor.startEmployeeTraining(emp));
		assertEquals(false, d.assignEmployee(emp));
		assertEquals(false, processor.startEmployeeTraining(emp));

		//2. try to upgrade a machine
		Machine m = e.getProductionHouse().getMachines().get(0);
		assertEquals(true, processor.startMachineUpgrade(m, e.getHR()));
		assertEquals(0, m.getEmployeeCount());
		assertEquals(false, processor.startMachineUpgrade(m, e.getHR()));

		//3. try to extend the warehouse
		Warehouse w = e.getWarehouse();
		assertEquals(true, processor.startWarehouseExtension(w));
		assertEquals(false, processor.startWarehouseExtension(w));

		//4. try to start a research project
		Employee arch = e.getHR().hire(EmployeeType.ARCHITECT);
		Department d2 = new Department(EmployeeType.ARCHITECT);
		assertEquals(true, processor.startResearchProject(PFHouseType.BLOCK_HOUSE, arch));
		assertEquals(false, arch.assignWorkplace(d2));
		assertEquals(false, processor.startResearchProject(PFHouseType.CITY_VILLA, arch));
	}

	@Test
	public void testUpgradeResult(){
		//1. start by sending an employee on training
		Employee emp = e.getHR().hire(EmployeeType.HR);
		processor.startEmployeeTraining(emp);

		//2. try to upgrade a machine
		Machine m = e.getProductionHouse().getMachines().get(0);
		processor.startMachineUpgrade(m, e.getHR());

		//3. try to extend the warehouse
		Warehouse w = e.getWarehouse();
		processor.startWarehouseExtension(w);

		//4. try to start a research project
		Employee arch = e.getHR().hire(EmployeeType.ARCHITECT);
		processor.startResearchProject(PFHouseType.BLOCK_HOUSE, arch);
		
		for (int i = 0; i < 100; i++) {
			processor.processUpgrades(e);
		}
		
		//check if the expected upgrades are in place
		assertEquals(2, emp.getSkill());
		assertNull(emp.getWork());
		assertEquals(1, emp.getVisitedTrainings());
		assertEquals(25+7, m.getPerformance());
		assertEquals(3, m.getEmployeeCount());
		assertEquals(1, w.getUpgrades());
		assertEquals(1, e.getResearchedHouseTypes().size());
		assertNull(arch.getWork());
	}

}

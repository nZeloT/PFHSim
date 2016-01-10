package sim.research.dev;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import sim.Enterprise;
import sim.EnterpriseException;
import sim.TestUtils;
import sim.hr.Department;
import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.production.Machine;
import sim.production.PFHouseType;

public class UpgradeTest {

	private Enterprise e;

	@Before
	public void setUp() throws Exception {
		e = TestUtils.initializeEnterprise();
	}

	@Test
	public void testUpgradeSetup() {
		//1. start by sending an employee on training
		Employee emp = e.getHR().hire(EmployeeType.HR);
		Department d = new Department(EmployeeType.HR);
		emp.assignWorkplace(d);
		try{ e.startEmployeeTraining(emp); }catch(EnterpriseException e){fail();}
		assertEquals(false, d.assignEmployee(emp));

		boolean thrown = false;
		try{ e.startEmployeeTraining(emp); }catch(EnterpriseException e){thrown = true;}
		assertEquals(true, thrown);

		//2. try to upgrade a machine
		Machine m = e.getProductionHouse().getMachines().get(0);
		try{ e.startMachineUpgrade(m); }catch(EnterpriseException e){ fail(); }
		assertEquals(0, m.getEmployeeCount());

		thrown = false;
		try{ e.startMachineUpgrade(m);}catch(EnterpriseException e){thrown = true;}
		assertEquals(true, thrown);

		//3. try to extend the warehouse
		try{ e.startWarehouseExtension(); }catch(EnterpriseException e){ fail(); }

		//4. try to start a research project
		Employee arch = e.getHR().hire(EmployeeType.ARCHITECT);
		Department d2 = new Department(EmployeeType.ARCHITECT);

		try{ e.startResearchProject(PFHouseType.BLOCK_HOUSE); }catch(EnterpriseException e){ fail(); }
		assertEquals(false, arch.assignWorkplace(d2));

		thrown = false;
		try{ e.startResearchProject(PFHouseType.CITY_VILLA); }catch(EnterpriseException e){ thrown = true; }
		assertEquals(true, thrown);
	}

	@Test
	public void testUpgradeResult(){
		Employee emp = e.getHR().hire(EmployeeType.HR);
		Employee arch = e.getHR().hire(EmployeeType.ARCHITECT);
		Machine m = e.getProductionHouse().getMachines().get(0);
		
		try{
			//1. start by sending an employee on training
			e.startEmployeeTraining(emp);

			//2. try to upgrade a machine
			e.startMachineUpgrade(m);

			//3. try to extend the warehouse
			e.startWarehouseExtension();

			//4. try to start a research project
			e.startResearchProject(PFHouseType.BLOCK_HOUSE);

		}catch(EnterpriseException e){
			fail();
		}

		for (int i = 0; i < 100; i++) {
			e.doSimulationStep(new ArrayList<>());
		}

		//check if the expected upgrades are in place
		assertEquals(2, emp.getSkill());
		assertNull(emp.getWork());
		assertEquals(1, emp.getVisitedTrainings());
		assertEquals(25+7, m.getPerformance());
		assertEquals(3, m.getEmployeeCount());
		assertEquals(1, e.getWarehouse().getUpgrades());
		assertEquals(1, e.getResearchedHouseTypes().size());
		assertNull(arch.getWork());
	}

}

package sim.hr;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EmployeeTest {

	private Employee e;

	private Department d;

	@Before
	public void setUp() throws Exception {
		e = new Employee(EmployeeType.HR);
		d = new Department(EmployeeType.HR);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTraining() {
		int trainings = e.getVisitedTrainings();
		e.visitedTraining();

		assertEquals(trainings+1, e.getVisitedTrainings());

		int maxTrainings = e.getType().getPossibleUpgrades();
		for (int i = 0; i < maxTrainings; i++) {
			e.visitedTraining();
		}

		assertEquals(maxTrainings, e.getVisitedTrainings());
	}

	@Test
	public void testAssignWorkplace() {
		assertEquals(e.assignWorkplace(d), true);

		//reassignment
		assertEquals(e.assignWorkplace(d), true);
	}

	@Test
	public void testUnassignWorkplace() {
		e.assignWorkplace(d);

		assertEquals(e.unassignWorkplace(), true);
	}

	@Test
	public void testCanDoTraining() {

		assertEquals(true, e.canDoTraining());

		e.assignWorkplace(d);

		assertEquals(true, e.canDoTraining());
	}

	@Test
	public void testIsAssigned() {
		assertEquals(e.isAssigned(), false);
		e.assignWorkplace(d);
		assertEquals(e.isAssigned(), true);
		e.unassignWorkplace();
		assertEquals(e.isAssigned(), false);
	}

	@Test
	public void testCostCalulation(){
		assertEquals(e.getType().getBaseCost(), e.getCosts());

		e.visitedTraining();

		assertEquals(e.getType().getBaseCost()+e.getType().getUpgradeCostInc(), e.getCosts());
	}

	@Test
	public void testSkillCalculation(){
		assertEquals(1, e.getSkill());

		e.visitedTraining();

		assertEquals(1+e.getType().getUpgradeSkillInc(), e.getSkill());	
	}

}

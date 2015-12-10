package sim.hr;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HRTest {

	private HR hr;

	@Before
	public void setUp() throws Exception {
		hr = new HR();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHire() {
		Employee hrEmp = hr.hire(EmployeeType.HR);

		assertNotNull(hrEmp);

		hrEmp.assignWorkplace(hr);

		Employee fire = null;
		for (int i = 0; i < 50; i++) {
			assertNotNull(fire = hr.hire(EmployeeType.STORE_KEEPER));
		}

		assertNull(hr.hire(EmployeeType.STORE_KEEPER));
		
		hr.fire(fire);

		//hire a new HR guy
		Employee hrEmp2 = hr.hire(EmployeeType.HR);
		assertNotNull(hrEmp2);

		hrEmp2.assignWorkplace(hr);

		//try to hire one more guy
		assertNotNull(hr.hire(EmployeeType.STORE_KEEPER));
	}

	@Test
	public void testFire() {		
		Employee hrEmp = hr.hire(EmployeeType.HR);

		hrEmp.assignWorkplace(hr);

		Employee[] e = new Employee[50];
		Department d = new Department(EmployeeType.STORE_KEEPER);

		for (int i = 0; i < 50; i++) {
			e[i] = hr.hire(EmployeeType.STORE_KEEPER);
			e[i].assignWorkplace(d);
		}

		//1. try to fire the HR guy
		assertEquals(false, hr.fire(hrEmp));

		//2. fire some store_keepers
		assertEquals(true, hr.fire(e[0]));
		e[0] = null;
		assertEquals(true, hr.fire(e[1]));
		e[1] = null;
		assertEquals(48, d.getEmployeeCount());

		//3. try to fire the hr guy again
		assertEquals(false, hr.fire(hrEmp));

		//4. hire some more guys again
		//leave e[0] empty to be able to hire a HR guy :D
		e[1] = hr.hire(EmployeeType.STORE_KEEPER);
		e[1].assignWorkplace(d);

		//5. hire one more hr guy
		Employee hrEmp2 = hr.hire(EmployeeType.HR);
		hrEmp2.assignWorkplace(hr);

		//6. hire one more guy
		Employee more = hr.hire(EmployeeType.ARCHITECT);
		assertNotNull(more);

		//7. try to fire one hr guy
		assertEquals(false, hr.fire(hrEmp2));

		//8. fire the one more guy
		assertEquals(true, hr.fire(more));

		//9. fire the second hr guy
		assertEquals(true, hr.fire(hrEmp2));

		//4. fire the remaining guys
		for (Employee emp : e) {
			if(emp != null)
				assertEquals(true, hr.fire(emp));
		}

		//5. lastly fire the HR guy
		assertEquals(true, hr.fire(hrEmp));
	}

	@Test
	public void testGetUnassignedEmployees() {
		hireSomeGuys();
		
		assertNotNull(hr.getUnassignedEmployee(EmployeeType.STORE_KEEPER));
		assertNotNull(hr.getUnassignedEmployees(EmployeeType.STORE_KEEPER, 40));
		assertNotNull(hr.getUnassignedEmployees(EmployeeType.STORE_KEEPER, 49));
		assertNull(hr.getUnassignedEmployees(EmployeeType.STORE_KEEPER, 50));
		
	}

	@Test
	public void testGetOverallEmployeeCount() {
		hireSomeGuys();
		
		assertEquals(50, hr.getOverallEmployeeCount());
	}

	@Test
	public void testGetOverallEmployeeCosts() {
		hireSomeGuys();
		
		assertEquals(1000+49*700, hr.getOverallEmployeeCosts());
		assertEquals(1000, hr.getOverallEmployeeCosts(EmployeeType.HR));
		assertEquals(49*700, hr.getOverallEmployeeCosts(EmployeeType.STORE_KEEPER));
	}
	
	private void hireSomeGuys(){
		Employee hrEmp = hr.hire(EmployeeType.HR);

		hrEmp.assignWorkplace(hr);

		for (int i = 0; i < 49; i++) {
			hr.hire(EmployeeType.STORE_KEEPER);
		}
	}

}

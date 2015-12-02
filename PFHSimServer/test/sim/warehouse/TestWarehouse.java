package sim.warehouse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.hr.HR;
import sim.procurement.Resource;
import sim.procurement.ResourceType;
import sim.production.Wall;
import sim.production.WallType;

public class TestWarehouse {
	
	private Warehouse w;
	
	private Wall[] walls;
	private Resource[] resources;

	@Before
	public void setUp() throws Exception {
		HR empMgr = new HR();
		Employee hrGuy = empMgr.hire(EmployeeType.HR);
		hrGuy.assignWorkplace(empMgr);
		
		w = new Warehouse(50, 100, empMgr.hire(EmployeeType.STORE_KEEPER, 3));
		
		walls = new Wall[5];
		walls[0] = new Wall(WallType.ECO, 20);
		walls[1] = new Wall(WallType.NORMAL, 30);
		walls[2] = new Wall(WallType.NORMAL, 40);
		walls[3] = new Wall(WallType.ECO, 50);
		walls[4] = new Wall(WallType.ECO, 60);
		
		resources = new Resource[7];
		resources[0] = new Resource(20, ResourceType.WOOD);
		resources[1] = new Resource(20, ResourceType.INSULATION);
		resources[2] = new Resource(20, ResourceType.INSULATION);
		resources[3] = new Resource(20, ResourceType.INSULATION);
		resources[4] = new Resource(20, ResourceType.WOOD);
		resources[5] = new Resource(20, ResourceType.WOOD);
		resources[6] = new Resource(20, ResourceType.WOOD);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testStoreWall() {
		Wall wall = new Wall(WallType.ECO, 20);
		assertEquals(w.storeWall(wall), true);
	}
	
	@Test
	public void testStoreMultipleWall(){
		assertEquals(w.storeWall(walls), true);
	}

	@Test
	public void testStoreResource() {
		Resource res = new Resource(20, ResourceType.WOOD);
		assertEquals(w.storeResource(res), true);
	}
	
	@Test
	public void testStoreMultipleResources(){
		assertEquals(w.storeResource(resources), true);
	}

	@Test
	public void testRemoveWall() {
		Wall wall = new Wall(WallType.ECO, 30);
		
		w.storeWall(wall);
		
		assertEquals(w.removeWall(WallType.ECO), wall);
	}

	@Test
	public void testRemoveWalls() {
		w.storeWall(this.walls);
		Wall[] walls = w.removeWalls(WallType.ECO, 2);
		
		assertNotNull(walls);
		assertEquals(walls.length, 2);
	}

	@Test
	public void testRemoveResource() {
		Resource res = new Resource(200, ResourceType.INSULATION);
		
		w.storeResource(res);
		
		assertEquals(w.removeResource(ResourceType.INSULATION), res);
	}

	@Test
	public void testRemoveResources() {
		w.storeResource(this.resources);
		Resource[] res = w.removeResource(ResourceType.INSULATION, 2);
		
		assertNotNull(res);
		assertNotNull(res[0]);
		assertNotNull(res[1]);
		assertEquals(res.length, 2);
	}

	@Test
	public void testIsInStorageWall() {
		w.storeWall(this.walls);
		
		assertEquals(w.isInStorage(WallType.NORMAL, 2), true);
	}

	@Test
	public void testIsInStorageResource() {
		w.storeResource(this.resources);
		
		assertEquals(w.isInStorage(ResourceType.INSULATION, 3), true);
	}

	@Test
	public void testCapacity() {
		w.setCapacity(100);
		assertEquals(w.getCapacity(), 100);
	}

}

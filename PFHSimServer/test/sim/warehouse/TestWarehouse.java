package sim.warehouse;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
		w = new Warehouse(50, 100);
		
		walls = new Wall[5];
		walls[0] = new Wall(WallType.ECO);
		walls[1] = new Wall(WallType.NORMAL);
		walls[2] = new Wall(WallType.NORMAL);
		walls[3] = new Wall(WallType.ECO);
		walls[4] = new Wall(WallType.ECO);
		
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
		Wall wall = new Wall(WallType.ECO);
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
		Wall wall = new Wall(WallType.ECO);
		
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
		
	}

	@Test
	public void testIsInStorageResource() {
	}

	@Test
	public void testCapacity() {
		w.setCapacity(100);
		assertEquals(w.getCapacity(), 100);
	}

}

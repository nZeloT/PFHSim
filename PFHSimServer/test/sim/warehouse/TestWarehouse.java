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

	@Before
	public void setUp() throws Exception {
		w = new Warehouse(50, 100);
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
		Wall w1 = new Wall(WallType.ECO);
		Wall w2 = new Wall(WallType.ECO);
		Wall w3 = new Wall(WallType.ECO);
		
		assertEquals(w.storeWall(w1, w2, w3), true);
	}

	@Test
	public void testStoreResource() {
		Resource res = new Resource(20, ResourceType.WOOD);
		assertEquals(w.storeResource(res), true);
	}
	
	@Test
	public void testStoreMultipleResources(){
		Resource r1 = new Resource(20, ResourceType.WOOD);
		Resource r2 = new Resource(20, ResourceType.WOOD);
		Resource r3 = new Resource(20, ResourceType.WOOD);
		
		assertEquals(w.storeResource(r1, r2, r3), true);
	}

	@Test
	public void testRemoveWall() {
		Wall wall = new Wall(WallType.ECO);
		
		w.storeWall(wall);
		
		assertEquals(w.removeWall(WallType.ECO), wall);
	}

	@Test
	public void testRemoveWalls() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveResourceResourceType() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveResourceResourceTypeInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsInStorageWallType() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsInStorageWallTypeInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsInStorageResourceType() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsInStorageResourceTypeInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testCapacity() {
		w.setCapacity(100);
		assertEquals(w.getCapacity(), 100);
	}

}

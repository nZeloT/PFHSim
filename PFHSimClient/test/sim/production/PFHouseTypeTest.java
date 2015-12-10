package sim.production;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.*;

import sim.procurement.ResourceType;

public class PFHouseTypeTest {

	@Test
	public void testWallTypeObjects() {
		
		PFHouseType wt = PFHouseType.BUNGALOW;
		ResourceType[] rt = wt.getRequiredWallTypes()[0].getRequiredResourceTypes();
		Arrays.asList(rt).forEach((tmp) -> {System.out.println(tmp);});
		assertNotNull(rt);
		
	}
	
}

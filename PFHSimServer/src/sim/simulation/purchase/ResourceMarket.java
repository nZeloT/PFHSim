package sim.simulation.purchase;

/**
 * ResourceMarket a singleton because only one market exists
 * and needs to be known to every enterprise
 */
public class ResourceMarket {
	
	private static ResourceMarket instance = new ResourceMarket();
	
	public static ResourceMarket get(){
		return instance;
	}
	
	private ResourceMarket(){}
	
	
	/**
	 * TODO:
	 * 	-> generate resource objects with a price
	 *  -> sell those resource objects to an enterprise
	 */
}

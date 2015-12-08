package sim;

import sim.simulation.sales.Offer;

class EnterpriseOfferTupel {
	public Offer offer;
	public Enterprise enterprise;

	public EnterpriseOfferTupel(Enterprise enterprise, Offer offer) {
		this.offer = offer;
		this.enterprise = enterprise;
	}
}
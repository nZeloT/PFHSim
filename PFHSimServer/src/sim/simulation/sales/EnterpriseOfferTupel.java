package sim.simulation.sales;

import sim.Enterprise;

class EnterpriseOfferTupel {
	public Offer offer;
	public Enterprise enterprise;

	public EnterpriseOfferTupel(Enterprise enterprise, Offer offer) {
		this.offer = offer;
		this.enterprise = enterprise;
	}
}
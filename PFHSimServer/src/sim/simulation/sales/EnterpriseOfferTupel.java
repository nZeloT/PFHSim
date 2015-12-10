package sim.simulation.sales;

import sim.Enterprise;

class EnterpriseOfferTupel {
	public Offer offer;
	public int enterprise;

	public EnterpriseOfferTupel(int enterprise, Offer offer) {
		this.offer = offer;
		this.enterprise = enterprise;
	}
}
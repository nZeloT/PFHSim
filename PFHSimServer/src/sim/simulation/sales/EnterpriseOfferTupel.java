package sim.simulation.sales;

class EnterpriseOfferTupel {
	public Offer offer;
	public int enterprise;
	public double expensiveBuyerInterest;

	public EnterpriseOfferTupel(int enterprise, Offer offer) {
		this.offer = offer;
		this.enterprise = enterprise;
		this.expensiveBuyerInterest = 0.0;
	}
	
	public void setExpensiveBuyerInterest(int quality, int price) {
		expensiveBuyerInterest = (quality-(1.0/100000)*price);
		if (expensiveBuyerInterest < 0)
			expensiveBuyerInterest = 0;
	}
	
}
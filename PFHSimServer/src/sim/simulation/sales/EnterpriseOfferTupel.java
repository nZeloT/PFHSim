package sim.simulation.sales;

class EnterpriseOfferTupel {
	public Offer offer;
	public String enterprise;
	public double expensiveBuyerInterest;

	public EnterpriseOfferTupel(String enterprise, Offer offer) {
		this.offer = offer;
		this.enterprise = enterprise;
		this.expensiveBuyerInterest = 0.0;
	}
	
	public void setExpensiveBuyerInterest(int quality, int price) {
		expensiveBuyerInterest = (quality-(1.0/40000)*price);
		if (expensiveBuyerInterest < 0)
			expensiveBuyerInterest = 1;
	}
	
}
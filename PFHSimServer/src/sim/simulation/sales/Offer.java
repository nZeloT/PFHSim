package sim.simulation.sales;

import sim.production.PFHouseType;

public class Offer {
	
	private int price;
	private PFHouseType housetype;

	public Offer(PFHouseType housetype) {
		this.housetype = housetype;
	}
	
	public void setPrice(int price){
		this.price = price;
	}

	public PFHouseType getHousetype() {
		return housetype;
	}

	public void setHousetype(PFHouseType housetype) {
		this.housetype = housetype;
	}

	public int getPrice() {
		return price;
	}
	


}

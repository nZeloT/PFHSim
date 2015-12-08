package sim.simulation.sales;

import sim.abstraction.Tupel;
import sim.production.PFHouseType;
import sim.production.WallType;

public class Offer {
	
	private int price;
	private PFHouseType housetype;
	private Tupel<WallType>[] walltype;
	private int numberOfPurchases;

	@SafeVarargs
	public Offer(int price, PFHouseType housetype, Tupel<WallType>... walltype) {
		this.price = price;
		this.housetype = housetype;
		this.walltype = walltype;
		numberOfPurchases = 0;
	} 

	public void setPrice(int price){
		this.price = price;
	}

	public Tupel<WallType>[] getWalltype() {
		return walltype;
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
	
	public void setNumberOfPurchases(int numberOfPurchases) {
		this.numberOfPurchases = numberOfPurchases;
	}
	public int getNumberOfPurchases() {
		return numberOfPurchases;
	}
	


}

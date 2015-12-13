package sim.simulation.sales;

import sim.abstraction.Tupel;
import sim.production.PFHouseType;
import sim.production.WallType;

public class Offer {

	private int price;
	private int quality;
	private PFHouseType housetype;
	private Tupel<WallType>[] walltype;
	private int numberOfPurchases;
	private int maximumProducable;

	@SafeVarargs
	public Offer(int price, int qualityCoefficient, PFHouseType housetype,int max, Tupel<WallType>... walltype) {
		this.price = price;
		this.housetype = housetype;
		this.walltype = walltype;
		numberOfPurchases = 0;
		maximumProducable = max;
		quality = 0;
		for (int i = 0; i < walltype.length; i++) {
			quality += qualityCoefficient * walltype[i].type.getQualityFactor() * walltype[i].count;
		}
	}
	
	public int getAmountofProducable(){
		return maximumProducable;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	public void setPrice(int price) {
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

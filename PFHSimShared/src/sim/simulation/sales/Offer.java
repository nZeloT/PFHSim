package sim.simulation.sales;

import java.io.Serializable;

import sim.abstraction.Tupel;
import sim.production.PFHouseType;
import sim.production.WallType;

public class Offer implements Serializable {
	private static final long serialVersionUID = 1718456673834939980L;

	private int price;
	private int quality; 
	private PFHouseType housetype;
	private Tupel<WallType>[] walltype;
	private int numberOfPurchases;
	private int productionLimit;

	@SafeVarargs
	public Offer(int price, int qualityCoefficient, PFHouseType housetype, int max, Tupel<WallType>... walltype) {
		this.price = price;
		this.housetype = housetype;
		this.walltype = walltype;
		numberOfPurchases = 0;
		productionLimit = max;
		quality = housetype.getBaseQuality();
		for (int i = 0; i < walltype.length; i++) {
			quality += walltype[i].type.getQualityFactor() * walltype[i].count;
		}
	}

	public void setWalltypes(Tupel<WallType>[] walltype) {

				this.walltype = walltype;
	}

	public int getVariableCost() {
		return 0;
	}

	public int getFixCost() {
		return 0;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality + housetype.getBaseQuality();
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

	public int getProductionLimit() {
		return productionLimit;
	}
	
	public void setProductionLimit(int productionLimit) {
		this.productionLimit = productionLimit;
	}

}

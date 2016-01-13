package ui.sections;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import sim.production.PFHouseType;

public class reducedOffer {
	
	private final SimpleStringProperty housetype;
	private final SimpleIntegerProperty price;
	private final SimpleIntegerProperty sold; 
	
	
	 
	public reducedOffer(PFHouseType type, int price, int sold) {
		housetype = new SimpleStringProperty(type.name());
		this.price = new SimpleIntegerProperty(price);
		this.sold = new SimpleIntegerProperty(sold);
	}
	
	public String getHousetype() {
		return housetype.get();
	}



	public int getPrice() {
		return price.get();
	}



	public int getSold() {
		return sold.get();
	}
	
	
}

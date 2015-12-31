package sim.sales;

import java.util.ArrayList;
import java.util.List;

import sim.abstraction.Tupel;
import sim.hr.Department;
import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.production.WallType;
import sim.simulation.sales.Offer;

public class Sales extends Department {
	
	private static final int OFFERS_PER_SALES = 5;
	
	private List<Offer> offers;
	
	public Sales() {
		super(EmployeeType.SALES);
		offers = new ArrayList<>();
	}
	
	public boolean addOffer(Offer o){
		if(getNumberOfAllowedOffers() - offers.size() > 1){
			offers.add(o);
			return true;
		}
		
		return false;
	}
	
	public Offer removeOffer(int idx){
		return offers.remove(idx);
	}
	
	public void removeOffer(Offer o){
		offers.remove(o);
	}
	
	public int getOfferCount(){
		return offers.size();
	}
	
	public int getNumberOfAllowedOffers(){
		return getEmployeeCount() * OFFERS_PER_SALES;
	}
	
	public void resetOffers(){
		for (int k = 0; k < offers.size(); k++) {
			offers.get(k).setNumberOfPurchases(0);
		}
	}
	
	public void setOfferQuality() {
		for (Offer offer : offers) {
			Tupel<WallType>[] tmp = offer.getWalltype();
			int quality = 0;
			for (Tupel<WallType> tupel : tmp) {
				quality += tupel.count * tupel.type.getQualityFactor();
			}
			offer.setQuality(quality);
		}
	}
	
	public List<Offer> getOffers() {
		return offers;
	}
	
	@Override
	protected boolean unassignEmployee(Employee e, boolean calledFromEmployeeObject) {
		if(getNumberOfAllowedOffers() - OFFERS_PER_SALES - offers.size() > 0)
			return super.unassignEmployee(e, calledFromEmployeeObject);
		else
			return false;
	}
}

package sim.procurement;

import sim.ExceptionCategorie;
import sim.bank.BankAccount;
import sim.bank.BankException;
import sim.hr.Department;
import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.hr.HRException;
import sim.warehouse.Warehouse;
import sim.warehouse.WarehouseException;

/**
 * make sure we have at least one procurement guy to enable buying resources :P
 */
public class Procurement extends Department {
	
	private ResourceMarket market;

	public Procurement(ResourceMarket market) {
		super(EmployeeType.PROCUREMENT);
		this.market = market;
	}
	
	public void buyResources(ResourceType type, int amount, Warehouse w, BankAccount b) throws HRException, WarehouseException, BankException, ResourceMarketException{
		if (getEmployeeCount() == 0) // this should never happen;
			// just for safety
			throw new HRException(this, "Procurement has no employees assigned; cannot buy on market.",
					ExceptionCategorie.PROGRAMMING_ERROR);

		int price = amount * market.getPrice(type);

		if (!w.isStoreable(type, amount))
			throw new WarehouseException(this, "Could not store the requested amount of resources in the Warehouse!",
					ExceptionCategorie.ERROR);

		// the following should now work without exception
		b.charge(price); //terminates with exception when we can not charge the bank
		Resource[] resources = market.buyResources(type, amount);
		w.storeResource(resources); // we already checked that we can
		// store the required amount
	}
	
	public ResourceMarket getMarket() {
		return market;
	}
	
	@Override
	protected boolean unassignEmployee(Employee e, boolean calledFromEmployeeObject) {
		if(getEmployeeCount() - 1 > 0)
			return super.unassignEmployee(e, calledFromEmployeeObject);
		else
			return false;
	}

}

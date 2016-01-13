package sim.warehouse;

import sim.ExceptionCategorie;
import sim.procurement.ResourceType;

public class MissingResourceException extends WarehouseException {
	private static final long serialVersionUID = -6995798678094322041L;
	
	private ResourceType type;
	private int amount;

	public MissingResourceException(Object src, ResourceType type, int amount, ExceptionCategorie cat) {
		super(src, "Missing " + amount + " " + type + "!", cat);
		this.type = type;
		this.amount = amount;
	}
	
	public ResourceType getType() {
		return type;
	}
	
	public int getAmount() {
		return amount;
	}

}

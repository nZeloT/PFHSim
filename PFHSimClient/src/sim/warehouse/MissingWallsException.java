package sim.warehouse;

import sim.ExceptionCategorie;
import sim.production.WallType;

public class MissingWallsException extends WarehouseException {
	private static final long serialVersionUID = 7329810699877166608L;
	
	private WallType type;
	private int amount;

	public MissingWallsException(Object src, WallType type, int amount, ExceptionCategorie cat) {
		super(src, "Missing " + amount + " " + type + "!", cat);
		this.type = type;
		this.amount = amount;
	}
	
	public WallType getType() {
		return type;
	}
	
	public int getAmount() {
		return amount;
	}

}

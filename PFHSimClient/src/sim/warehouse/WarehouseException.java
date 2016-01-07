package sim.warehouse;

import sim.EnterpriseException;
import sim.ExceptionCategorie;

public class WarehouseException extends EnterpriseException {
	private static final long serialVersionUID = 1233726351486584931L;

	public WarehouseException(Object src, String msg, ExceptionCategorie cat) {
		super(src, msg, cat);
	}
	
}

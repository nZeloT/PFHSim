package sim.procurement;

import sim.EnterpriseException;
import sim.ExceptionCategorie;

public class ResourceMarketException extends EnterpriseException {
	private static final long serialVersionUID = -3095493539602165712L;

	public ResourceMarketException(Object src, String string, ExceptionCategorie cat) {
		super(src, string, cat);
	}

}

package sim.research.dev;

import sim.EnterpriseException;
import sim.ExceptionCategorie;

public class UpgradeException extends EnterpriseException {
	private static final long serialVersionUID = -671333568820670904L;

	public UpgradeException(Object src, String string, ExceptionCategorie cat) {
		super(src, string, cat);
	}

}

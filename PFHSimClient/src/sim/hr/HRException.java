package sim.hr;

import sim.EnterpriseException;
import sim.ExceptionCategorie;

public class HRException extends EnterpriseException {
	private static final long serialVersionUID = 2988452356166919760L;

	public HRException(Object src, String string, ExceptionCategorie cat) {
		super(src, string, cat);
	}
}

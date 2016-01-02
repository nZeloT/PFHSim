package sim.production;

import sim.EnterpriseException;
import sim.ExceptionCategorie;

public class MachineException extends EnterpriseException {
	private static final long serialVersionUID = 5694862651123857879L;
	
	public MachineException(Machine src, String msg, ExceptionCategorie cat) {
		super(src, msg, cat);
	}
	
}

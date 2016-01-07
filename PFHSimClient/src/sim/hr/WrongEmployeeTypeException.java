package sim.hr;

import sim.EnterpriseException;
import sim.ExceptionCategorie;

public class WrongEmployeeTypeException extends EnterpriseException {
	private static final long serialVersionUID = -238680970952443028L;

	public WrongEmployeeTypeException(Object src) {
		super(src, "Tried to assign wrong employee type!", ExceptionCategorie.PROGRAMMING_ERROR);
	}
	
}

package sim.bank;

import sim.EnterpriseException;
import sim.ExceptionCategorie;

public class BankException extends EnterpriseException {
	private static final long serialVersionUID = 9131289827511403881L;

	public BankException(Object src, String string, ExceptionCategorie cat) {
		super(src, string, cat);
	}
}

package sim.hr;

public class WrongEmployeeTypeException extends Exception {
	
	public WrongEmployeeTypeException() {
		super("Tried to assign wrong employee type!");
	}
	
}

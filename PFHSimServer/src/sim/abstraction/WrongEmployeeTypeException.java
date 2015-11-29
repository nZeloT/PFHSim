package sim.abstraction;

public class WrongEmployeeTypeException extends Exception {
	
	public WrongEmployeeTypeException() {
		super("Tried to assign wrong employee type!");
	}
	
}

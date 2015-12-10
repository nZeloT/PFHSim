package sim.production;

public class MachineException extends Exception {
	private static final long serialVersionUID = 5694862651123857879L;
	
	private Machine source;
	
	public MachineException(Machine src, String msg) {
		super(msg);
		this.source = src;
	}
	
	public Machine getSourceMachine() {
		return source;
	}
	
}

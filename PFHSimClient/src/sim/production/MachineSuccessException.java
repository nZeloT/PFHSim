package sim.production;

import sim.ExceptionCategorie;

public class MachineSuccessException extends MachineException {
	private static final long serialVersionUID = -6061488422480447926L;

	private WallType t;
	private int prod;
	
	public MachineSuccessException(Machine src, WallType t, int prod, ExceptionCategorie cat) {
		super(src, "Machine " + src.getId() + " produced " + prod + " " + t, cat);
		this.t = t;
		this.prod = prod;
	}
	
	public WallType getType() {
		return t;
	}

	public int getProduction() {
		return prod;
	}
}

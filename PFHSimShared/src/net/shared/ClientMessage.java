package net.shared;

import java.io.Serializable;
import java.util.List;

import sim.simulation.sales.Offer;

public class ClientMessage implements Serializable {
	private static final long serialVersionUID = -6022602168989208770L;

	private final int[] boughtResourceAmounts;
	private final List<Offer> newCatalog;
	
	public ClientMessage(int[] boughtResourceAmounts, List<Offer> newCatalog) {
		super();
		this.boughtResourceAmounts = boughtResourceAmounts;
		this.newCatalog = newCatalog;
	}

	public int[] getBoughtResourceAmounts() {
		return boughtResourceAmounts;
	}

	public List<Offer> getNewCatalog() {
		return newCatalog;
	}
}

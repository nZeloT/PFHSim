package net.shared;

import java.util.HashMap;
import java.util.List;

import sim.procurement.ResourceType;
import sim.simulation.sales.Offer;

public class ClientMessage implements Message {
	private static final long serialVersionUID = -6022602168989208770L;

	private final HashMap<ResourceType, Integer> boughtResourceAmounts;
	private final List<Offer> newCatalog;
	
	public ClientMessage(HashMap<ResourceType, Integer> boughtResourceAmounts, List<Offer> newCatalog) {
		super();
		this.boughtResourceAmounts = boughtResourceAmounts;
		this.newCatalog = newCatalog;
	}

	public HashMap<ResourceType, Integer> getBoughtResourceAmounts() {
		return boughtResourceAmounts;
	}

	public List<Offer> getNewCatalog() {
		return newCatalog;
	}
}

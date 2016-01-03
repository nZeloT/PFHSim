package net.shared;

import java.util.HashMap;
import java.util.List;

import sim.procurement.ResourceType;
import sim.simulation.sales.Offer;

public class ClientMessage implements Message {
	private static final long serialVersionUID = -6022602168989208770L;

	private final String name;
	private final HashMap<ResourceType, Integer> boughtResourceAmounts;
	private final List<Offer> newCatalog;
	private final boolean cashOnLimit;
	
	public ClientMessage(String name, HashMap<ResourceType, Integer> boughtResourceAmounts, 
			List<Offer> newCatalog, boolean cashOnLimit) {
		super();
		this.name = name;
		this.boughtResourceAmounts = boughtResourceAmounts;
		this.newCatalog = newCatalog;
		this.cashOnLimit = cashOnLimit;
	}

	public HashMap<ResourceType, Integer> getBoughtResourceAmounts() {
		return boughtResourceAmounts;
	}

	public List<Offer> getNewCatalog() {
		return newCatalog;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isCashOnLimit() {
		return cashOnLimit;
	}
}

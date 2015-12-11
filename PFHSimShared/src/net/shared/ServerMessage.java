package net.shared;

import java.util.HashMap;
import java.util.List;

import sim.procurement.ResourceType;
import sim.simulation.sales.Offer;

public class ServerMessage implements Message{
	private static final long serialVersionUID = 5729214100566288796L;
	
	private final HashMap<ResourceType, Integer> newResourcePrices;
	private final List<Offer> soldOfferAmounts;
	private final boolean gameEnded;
	private final List<Offer> marketResearch;
	
	public ServerMessage(HashMap<ResourceType, Integer> newResourcePrices, List<Offer> soldOfferAmounts, boolean gameEnded,
			List<Offer> marketResearch) {
		super();
		this.newResourcePrices = newResourcePrices;
		this.soldOfferAmounts = soldOfferAmounts;
		this.gameEnded = gameEnded;
		this.marketResearch = marketResearch;
	}

	public HashMap<ResourceType, Integer> getNewResourcePrices() {
		return newResourcePrices;
	}

	public List<Offer> getSoldOfferAmounts() {
		return soldOfferAmounts;
	}

	public boolean hasGameEnded() {
		return gameEnded;
	}

	public List<Offer> getMarketResearch() {
		return marketResearch;
	}
	
}

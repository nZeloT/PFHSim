package net.shared;

import java.io.Serializable;
import java.util.List;

import sim.simulation.sales.Offer;

public class ServerMessage implements Serializable{
	private static final long serialVersionUID = 5729214100566288796L;
	
	private final int[] newResourcePrices;
	private final int[] soldOfferAmounts;
	private final boolean gameEnded;
	private final List<Offer> marketResearch;
	
	public ServerMessage(int[] newResourcePrices, int[] soldOfferAmounts, boolean gameEnded,
			List<Offer> marketResearch) {
		super();
		this.newResourcePrices = newResourcePrices;
		this.soldOfferAmounts = soldOfferAmounts;
		this.gameEnded = gameEnded;
		this.marketResearch = marketResearch;
	}

	public int[] getNewResourcePrices() {
		return newResourcePrices;
	}

	public int[] getSoldOfferAmounts() {
		return soldOfferAmounts;
	}

	public boolean hasGameEnded() {
		return gameEnded;
	}

	public List<Offer> getMarketResearch() {
		return marketResearch;
	}
	
}

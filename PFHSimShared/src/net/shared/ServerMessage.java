package net.shared;

import java.util.HashMap;
import java.util.List;

import sim.abstraction.Pair;
import sim.procurement.ResourceType;
import sim.simulation.sales.Offer;

public class ServerMessage implements Message{
	private static final long serialVersionUID = 5729214100566288796L;
	
	private final int round;
	private final int score;
	
	private final HashMap<ResourceType, Integer> newResourcePrices;
	private final List<Offer> soldOfferAmounts;
	private final HashMap<String, List<Offer>> marketResearch;
	
	private final boolean gameEnded;
	private final List<Pair<String, Integer>> topList;
	
	public ServerMessage(int round, int score, HashMap<ResourceType, Integer> newResourcePrices, List<Offer> soldOfferAmounts,
			HashMap<String, List<Offer>> marketResearch, boolean gameEnded, List<Pair<String, Integer>> topList) {
		super();
		this.round = round;
		this.score = score;
		this.newResourcePrices = newResourcePrices;
		this.soldOfferAmounts = soldOfferAmounts;
		this.marketResearch = marketResearch;
		this.gameEnded = gameEnded;
		this.topList = topList;
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

	public HashMap<String, List<Offer>> getMarketResearch() {
		return marketResearch;
	}
	
	public int getRound() {
		return round;
	}
	
	public int getScore() {
		return score;
	}
	
	public List<Pair<String, Integer>> getTopList() {
		return topList;
	}
	
}

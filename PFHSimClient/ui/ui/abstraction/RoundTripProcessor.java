package ui.abstraction;

import java.util.HashMap;
import java.util.List;

import sim.EnterpriseException;
import sim.abstraction.Pair;
import sim.simulation.sales.Offer;

@FunctionalInterface
public interface RoundTripProcessor {
	
	public Triple<Boolean, Integer, Integer> doRoundTrip(List<EnterpriseException> msgStore, 
			List<Pair<String, Integer>> topList, HashMap<String, List<Offer>> marketResearch);
}

package org.example.model;

import java.util.Map;

public class AdvancedModel extends BasicModel {
	private final DumbModel dumbModel;

	public AdvancedModel(PlayerList players, Player self, Card[] known, Card[] owned) {
		super(players, self, known, owned);
		dumbModel = new DumbModel(players, known);
	}

	@Override
	protected void handleAssertions() {
		super.handleAssertions();
		while (dumbModel.hasNext()) {
			Card known = dumbModel.next();
			for (Map<Card, Knowledge> score : scorecard.values()) {
				Knowledge prior = score.getOrDefault(known, Knowledge.NO_HAS);
				switch (prior) {
					case KNOWN:
					case HAS:
						break;
					case MIGHT_HAVE:
						throw new IllegalStateException("Dumb Model knows more than Basic Model");
					case NO_HAS:
						score.put(known, Knowledge.KNOWN);
						break;
				}
			}
		}
	}

	@Override
	public void addQuery(Query query) {
		super.addQuery(query);
		dumbModel.addQuery(query);
	}
}

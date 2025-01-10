package com.cluemodeler.model;

public interface Strategy {
	static Strategy buildStrategy(Strategy.T strategy, ImmutableScorecard card, PlayerList plist, Player self) {
		return switch (strategy) {
			case AGGRESSIVE -> new AggressiveStrategy(card, plist, self);
		};
	}

	enum T {
		AGGRESSIVE
	}

	/**
	 * Generates a question to ask
	 * @return a guess containing the weapon, person, and location
	 */
	Guess generateQuestion();
}

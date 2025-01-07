package com.cluemodeler.model;

public interface Strategy {
	static Strategy buildStrategy(Strategy.T strategy, ImmutableScorecard card) {
		return switch (strategy) {
			case AGGRESSIVE -> new AggressiveStrategy(card);
			case BALANCED -> new BalancedStrategy(card);
		};
	}

	enum T {
		AGGRESSIVE, BALANCED
	}

	/**
	 * Generates a question to ask
	 * @return a guess containing the weapon, person, and location
	 */
	Guess generateQuestion();
}

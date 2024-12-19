package org.example.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This model runs a basic model, only without knowing what cards the player holds
 * This can be used to estimate what other players have learned from common knowledge
 * This class can be iterated over as a stream of cards that everyone should know aren't in the envelope
 */
class DumbModel extends BasicModel implements Iterator<Card> {
	private final Queue<Card> knownCards;

	public DumbModel(PlayerList players, Card[] known) {
		super(players, known);
		knownCards = new LinkedList<>();
	}

	@Override
	protected void handleAssertions() {
		while (!unhandledAssertions.isEmpty()) {
			Assertion art = unhandledAssertions.remove();
			if (art instanceof PlayerHasAssertion) {
				knownCards.add(((PlayerHasAssertion) art).card);
			}
			art.handle();
		}
	}

	public boolean hasNext() {
		return !knownCards.isEmpty();
	}

	public Card next() {
		return knownCards.remove();
	}
}

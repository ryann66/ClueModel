package org.example.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This model operates without knowing the cards that self has
 */
class DumbModel extends BasicModel implements Iterator<Card> {
	private Queue<Card> knownCards;

	public DumbModel(PlayerList players, Card[] known) {
		super(players, known);
		knownCards = new LinkedList<>();
	}

	public boolean hasNext() {

	}

	public Card next() {

	}
}

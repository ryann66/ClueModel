package org.example.model;

/**
 * Record class for representing questions asked
 * @param asker the player that asked the question
 * @param answered the player that answered the question, or null if unanswered
 * @param cards the cards asked in the question
 */
public record Query(Player asker, Player answered, Card[] cards) {
	public Query {
		if (asker == null) throw new IllegalArgumentException("Asker must be non-null");
		if (cards.length != 3) throw new IllegalArgumentException("Must have 3 cards");
		int sum = 0;
		for (Card c : cards) {
			sum += switch (c.type) {
				case WEAPON -> 1;
				case PERSON -> 2;
				case LOCATION -> 4;
			};
		}
		if (sum != 7) throw new IllegalArgumentException("Must have a weapon, person, and location card");
	}
}

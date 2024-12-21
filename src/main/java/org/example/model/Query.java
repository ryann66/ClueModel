package org.example.model;

/**
 * Record class for representing questions asked
 * @param asker the player that asked the question
 * @param answered the player that answered the question, or null if unanswered
 * @param cards the cards asked in the question
 * @param answer the card that was shown, if known (was this my own query?)
 */
public record Query(Player asker, Player answered, Card[] cards, Card answer) {
	public Query {
		if (asker == null) throw new IllegalArgumentException("Asker must be non-null");
		if (cards == null) throw  new IllegalArgumentException("Cards must not be null");
		if (cards.length != 3) throw new IllegalArgumentException("Must have 3 cards");
		int sum = 0;
		// only check if answer is contained in questions if it's not null
		boolean anscon = answer == null;
		for (Card c : cards) {
			sum += switch (c.type) {
				case WEAPON -> 1;
				case PERSON -> 2;
				case LOCATION -> 4;
			};
			// if answer is not yet found, check if this card is answer
			if (!anscon) {
				anscon = c == answer;
			}
		}
		if (sum != 7) throw new IllegalArgumentException("Must have a weapon, person, and location card");
		if (answer != null && answered == null) throw new IllegalArgumentException("Queries with an answer must have an answerer");
		if (!anscon) throw new IllegalArgumentException("Answer card not in question");
	}
}

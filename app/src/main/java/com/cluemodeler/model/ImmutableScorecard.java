package com.cluemodeler.model;

import java.util.Map;

/**
 * Immutable view at the scorecard
 */
public interface ImmutableScorecard {
	/**
	 * Gets the knowledge associated with player p and card c
	 * @param p the player to get knowledge for
	 * @param c the card to get knowledge for
	 * @return what we know about if player p is holding card c
	 * 	if no knowledge is known, returns Card.MIGHT_KNOW()
	 */
	Knowledge get(Player p, Card c);

	/**
	 * Gets an immutable view of the scorecard for a single player
	 * @param p the player to get a scorecard for
	 * @return an immutable view at the scorecard for a single player
	 */
	ImmutablePlayerScorecard get(Player p);

	/**
	 * Immutable view of a single player's scorecard
	 */
	interface ImmutablePlayerScorecard extends Iterable<Map.Entry<Card, Knowledge>> {
		/**
		 * Gets the knowledge about the player this scorecard is made for and the given card
		 * @param c the card to get knowledge about
		 * @return the knowledge about the player this scorecard is made for
		 * 	if no knowledge is known, returns Card.MIGHT_KNOW()
		 */
		Knowledge get(Card c);
	}
}

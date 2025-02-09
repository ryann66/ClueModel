package com.cluemodeler.model;

import java.util.List;
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
	 * Compiles a list of all weapons that are unaccounted for
	 * @return a list of all possible murder weapons
	 */
	List<Card> missingWeapons();

	/**
	 * Compiles a list of all people that are unaccounted for
	 * @return a list of all possible murderers
	 */
	List<Card> missingPeople();

	/**
	 * Compiles a list of all locations that are unaccounted for
	 * @return a list of all possible murder locations
	 */
	List<Card> missingLocations();

	/**
	 * Fast test for if the answer is known with perfect confidence
	 * @return true if the answer is known, else false
	 */
	boolean confidentGuess();

	/**
	 * Creates a possible guess for the answer
	 * @return some possible guess
	 */
	Guess guess();

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

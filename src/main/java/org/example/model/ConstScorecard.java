package org.example.model;

import java.util.Map;

/**
 * Const wrapper for scorecards
 * Special meaning for integer values in scorecard
 *  - negative numbers mean that the player does not have the card
 *  - 0 means that the player must have the card
 *  - positive numbers mean that the player might have the card
 *  	Each positive number represents a group; the player must have
 *  	at least one card in the group
 */
public class ConstScorecard {
	protected int[][] scorecard;

	/**
	 * Builds a comprehensive scorecard of everything the player knows
	 * Special meaning for integer values in scorecard
	 *  - negative numbers mean that the player does not have the card
	 *  - 0 means that the player must have the card
	 *  - positive numbers mean that the player might have the card
	 *  	Each positive number represents a group; the player must have
	 *  	at least one card in the group
	 *
	 * @return a comprehensive scorecard
	 */
	public Map<Player, Map<Card.Value, Integer>> getScorecard() {
		// TODO
	}

	/**
	 * Builds a simple scorecard for what the player knows
	 * Consists of one column that is either card found (true) or
	 * card not found (false)
	 * @return a simple one column scorecard where true represents
	 * 		that a card has been found/eliminated and false represents
	 * 		that the card could still be in the envelope
	 */
	public Map<Card.Value, Boolean> getSimpleScorecard() {
		// TODO
	}
}

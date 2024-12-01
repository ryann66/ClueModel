package org.example.model;

import java.util.Map;

public class Scorecard extends ConstScorecard {
	/**
	 * Returns a modifiable reference to the full scorecard
	 * Changes to the map will reflect out to the public scorecard
	 * Special meaning for integer values in scorecard
	 *  - negative numbers mean that the player does not have the card
	 *  - 0 means that the player must have the card
	 *  - positive numbers mean that the player might have the card
	 *  	Each positive number represents a group; the player must have
	 *  	at least one card in the group
	 *
	 * @return a comprehensive scorecard
	 */
	@Override
	public Map<Player, Map<Card.Value, Integer>> getScorecard() {
		return scorecard;
	}
}

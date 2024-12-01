package org.example.model;

import java.util.Collections;
import java.util.HashMap;
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
	protected Map<Player, Map<Card.Value, Integer>> scorecard;

	/**
	 * Returns an unmodifiable view of the model's full scorecard
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
		return Collections.unmodifiableMap(scorecard);
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
		Map<Card.Value, Boolean> retmap = new HashMap<>(Card.NUM_CARDS);
		for (Card.Value v : Card.Value.values()) retmap.put(v, false);

		for (Map<Card.Value, Integer> map : scorecard.values()) {
			for (Map.Entry<Card.Value, Integer> kv : map.entrySet()) {
				if (kv.getValue() == 0) retmap.put(kv.getKey(), true);
			}
		}

		return retmap;
	}
}

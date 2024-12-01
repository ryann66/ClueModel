package org.example.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Interface for classes that model the scorecard
 */
public abstract class Model {
	protected Map<Player, Map<Card.Value, Integer>> scorecard;

	protected Model(PlayerList players, Player self, Card[] known) {
		// fill scorecard with maps
		scorecard = new HashMap<>(players.getPlayerCount());
		Iterator<Player> iter = players.iterator();
		while (iter.hasNext()) scorecard.put(iter.next(), new HashMap<>());

		HashMap<Card.Value, Integer> selfcards = new HashMap<>(Card.NUM_CARDS);

		// block all cards
		for (Card.Value v : Card.Value.values()) selfcards.put(v, -1);
		// add known cards
		for (Card c : known) selfcards.put(c.value, 0);

		// replace self map in scorecard
		scorecard.put(self, selfcards);
	}

	/**
	 * Get an unmodifiable view to what the model knows about the game
	 * Special meaning for integer values in scorecard
	 *  - negative numbers mean that the player does not have the card
	 *  - 0 means that the player must have the card
	 *  - positive numbers mean that the player might have the card
	 *  	Each positive number represents a group; the player must have
	 *  	at least one card in the group
	 * @return a const wrapper to the scorecard
	 */
	public final Map<Player, Map<Card.Value, Integer>> getScorecard() {
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
	public final Map<Card.Value, Boolean> getSimpleScorecard() {
		Map<Card.Value, Boolean> retmap = new HashMap<>(Card.NUM_CARDS);
		for (Card.Value v : Card.Value.values()) retmap.put(v, false);

		for (Map<Card.Value, Integer> map : scorecard.values()) {
			for (Map.Entry<Card.Value, Integer> kv : map.entrySet()) {
				if (kv.getValue() == 0) retmap.put(kv.getKey(), true);
			}
		}

		return retmap;
	}

	/**
	 * Process the given query and add its knowledge to the model
	 * @param query the query to process
	 */
	public abstract void addQuery(Query query);
}

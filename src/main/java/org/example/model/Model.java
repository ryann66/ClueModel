package org.example.model;

import java.util.*;

/**
 * Basic starter class for models to share scoreboard usage
 */
public abstract class Model {
	/**
	 * Abstract idea that something is known
	 */
	public enum Knowledge {
		HAS, NO_HAS, MIGHT_HAVE;

		final Set<Group> groups;

		Knowledge() {
			groups = new HashSet<>();
		}

		public Set<Group> getGroups() {
			return Collections.unmodifiableSet(groups);
		}
	}

	public static class Group {
		private static int nid = 0;

		public final int id;
		private Set<Knowledge> contents;

		private Group() {
			id = nid++;
		}
	}

	protected Map<Player, Map<Card.Value, Knowledge>> scorecard;

	protected Model(PlayerList players, Player self, Card[] known) {
		if (players == null || self == null || known == null)
			throw new IllegalArgumentException("Unexpected null argument");

		// fill scorecard with maps
		scorecard = new HashMap<>(players.getPlayerCount());
		Iterator<Player> iter = players.iterator();
		while (iter.hasNext()) scorecard.put(iter.next(), new HashMap<>());

		HashMap<Card.Value, Knowledge> selfcards = new HashMap<>(Card.NUM_CARDS);

		// block all cards
		for (Card.Value v : Card.Value.values()) selfcards.put(v, Knowledge.NO_HAS);
		// add known cards
		for (Card c : known) selfcards.put(c.value, Knowledge.HAS);

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
	/*
	public final Map<Player, Map<Card.Value, Integer>> getScorecard() {
		return Collections.unmodifiableMap(scorecard);
	}*/

	/**
	 * Builds a simple scorecard for what the player knows
	 * Consists of one column that is either card found (true) or
	 * card not found (false)
	 * @return a simple one column scorecard where true represents
	 * 		that a card has been found/eliminated and false represents
	 * 		that the card could still be in the envelope
	 */
	/*
	public final Map<Card.Value, Boolean> getSimpleScorecard() {
		Map<Card.Value, Boolean> retmap = new HashMap<>(Card.NUM_CARDS);
		for (Card.Value v : Card.Value.values()) retmap.put(v, false);

		for (Map<Card.Value, Integer> map : scorecard.values()) {
			for (Map.Entry<Card.Value, Integer> kv : map.entrySet()) {
				if (kv.getValue() == 0) retmap.put(kv.getKey(), true);
			}
		}

		return retmap;
	}*/

	/**
	 * Process the given query and add its knowledge to the model
	 * @param query the query to process
	 */
	public abstract void addQuery(Query query);
}

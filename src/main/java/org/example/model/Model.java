package org.example.model;

import java.util.*;

/**
 * Basic starter class for models to share scoreboard usage
 */
public abstract class Model {
	/**
	 * Abstract idea that something is known about a player's cards
	 */
	protected enum Knowledge {
		// Player has the card
		HAS(null),

		// Player does not have the card
		NO_HAS(null),

		// Player has one of the cards in the set
		MIGHT_HAVE(new HashSet<>()),

		// Player doesn't have the card but knows where it is
		KNOWN(null);

		final Set<Group> groups;

		Knowledge(HashSet<Group> hs) {
			groups = hs;
		}
	}

	protected static class Group {
		private static int nid = 0;

		final int id;
		final Map<Card, Knowledge> contents = new HashMap<>(3);

		protected Group() {
			id = nid++;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Group group = (Group) o;
			return id == group.id;
		}

		@Override
		public int hashCode() {
			return Objects.hash(id);
		}
	}

	protected Map<Player, Map<Card, Knowledge>> scorecard;

	protected Model(PlayerList players, Player self, Card[] known, Card[] owned) {
		if (players == null || self == null || known == null || owned == null)
			throw new IllegalArgumentException("Unexpected null argument");

		// fill scorecard with maps
		scorecard = new HashMap<>(players.getPlayerCount());
		Iterator<Player> iter = players.iterator();
		while (iter.hasNext()) scorecard.put(iter.next(), new HashMap<>());

		Map<Card, Knowledge> selfcards = new HashMap<>(Card.NUM_CARDS);

		// block all cards
		for (Card c : Card.values()) selfcards.put(c, Knowledge.NO_HAS);
		// add owned cards
		for (Card c : owned) selfcards.put(c, Knowledge.HAS);

		// replace self map in scorecard
		scorecard.put(self, selfcards);

		// add common cards
		for (Map<Card, Knowledge> player : scorecard.values()) {
			for (Card k : known) player.put(k, Knowledge.KNOWN);
		}
	}

	/**
	 * Builds a simple scorecard for what the player knows
	 * Consists of one column that is either card found (true) or
	 * card not found (false)
	 * @return a simple one column scorecard where true represents
	 * 		that a card has been found/eliminated and false represents
	 * 		that the card could still be in the envelope
	 */
	public final Map<Card, Boolean> getSimpleScorecard() {
		Map<Card, Boolean> retmap = new HashMap<>(Card.NUM_CARDS);
		for (Card c : Card.values()) retmap.put(c, false);

		for (Map<Card, Knowledge> map : scorecard.values()) {
			for (Map.Entry<Card, Knowledge> kv : map.entrySet()) {
				if (kv.getValue() == Knowledge.HAS) retmap.put(kv.getKey(), true);
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

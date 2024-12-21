package org.example.model;

import java.util.*;

/**
 * Basic starter class for models to share scoreboard usage
 */
abstract class AbstractModel implements Model {
	protected Map<Player, Map<Card, Knowledge>> scorecard;

	protected AbstractModel(PlayerList players, Player self, Card[] known, Card[] owned) {
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
	 * Builds an AbstractModel that only knows the common knowledge about the game
	 * @param players the list of players in the game
	 * @param known the array of cards that are known to everyone
	 */
	protected AbstractModel(PlayerList players, Card[] known) {
		if (players == null || known == null)
			throw new IllegalArgumentException("Unexpected null argument");

		// fill scorecard with maps
		scorecard = new HashMap<>(players.getPlayerCount());
		Iterator<Player> iter = players.iterator();
		while (iter.hasNext()) scorecard.put(iter.next(), new HashMap<>());

		// add common cards
		for (Map<Card, Knowledge> player : scorecard.values()) {
			for (Card k : known) player.put(k, Knowledge.KNOWN);
		}
	}

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

	public Map<Player, Map<Card, Knowledge>> getFullScorecard() {
		Map<Player, Map<Card, Knowledge>> retmap = new HashMap<>(scorecard.size());
		for (Map.Entry<Player, Map<Card, Knowledge>> entry : scorecard.entrySet()) {
			retmap.put(entry.getKey(), Collections.unmodifiableMap(entry.getValue()));
		}
		return retmap;
	}
}

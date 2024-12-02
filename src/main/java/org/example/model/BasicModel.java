package org.example.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Basic modeler that fills out a scorecard based purely off known information
 */
public final class BasicModel extends Model {
	private final PlayerList players;
	private final Player self;

	private final Queue<Assertion> unhandledAssertions = new LinkedList<>();

	public BasicModel(PlayerList players, Player self, Card[] known) {
		super(players, self, known);
		this.players = players;
		this.self = self;
	}

	@Override
	public void addQuery(Query query) {
		if (query.cards() == null || query.cards().length != 3)
			throw new IllegalArgumentException("Bad query cards");
		if (query.asker() == null) throw new IllegalArgumentException("Null asker");

		if (query.asker() == self) {
			if ((query.answered() == null) != (query.answer() == null))
				// our questions must either be fully answered or unanswered
				throw new IllegalArgumentException("Inconsistent query");

			if (query.answered() != null) {
				// we know that the player has the card
				unhandledAssertions.add(new PlayerHasAssertion(query.answered(), query.answer()));
			}
		} else if (query.answer() != null && query.answered() != self) {
			// if it wasn't our question; we know the answer, we must have answered it
			throw new IllegalArgumentException("How do we know the answer?");
		} else {
			// we know that the player who answered must have one of the three cards
			unhandledAssertions.add(new PlayerHasOneOfAssertion(query.answered(), query.cards()));
		}

		// we know that everyone between asker and answerer does not have the card
		// if there was no answerer, then we know that everyone but the asker does not have the card
		Player answered = query.answered();
		if (answered == null) answered = query.asker();
		Iterator<Player> iter = players.iterator(query.asker(), answered);
		while (iter.hasNext()) {
			unhandledAssertions.add(new PlayerHasNoneOfAssertion(iter.next(), query.cards()));
		}

		// handle all our assertions (and subsequently triggered assertions
		handleAssertions();
	}

	/**
	 * Handles all the assertions we know about the game state
	 * Also handles all assertions spawned by handling assertions
	 */
	private void handleAssertions() {
		while (!unhandledAssertions.isEmpty()) {
			unhandledAssertions.remove().handle();
		}
	}

	/**
	 * A statement that we now know to be true about the state of the game
	 * These assertions, when handled, will change the scorecard to reflect what
	 * we know about the cards of each player
	 */
	private interface Assertion {
		/**
		 * Implement any changes caused by this assertion to the scorecard
		 * This might spawn new assertions that will be added to unhandledAssertions
		 */
		void handle();
	}

	/**
	 * This assertion represents the idea that we know a certain player has a certain card
	 */
	private class PlayerHasAssertion implements Assertion {
		Player player;
		Card card;

		public PlayerHasAssertion(Player p, Card c) {
			player = p;
			card = c;
		}

		public void handle() {
			// TODO
		}
	}

	/**
	 * This assertion represents the idea that we know a player has one of a set of cards
	 */
	private class PlayerHasOneOfAssertion implements Assertion {
		Player player;
		Card[] cards;

		public PlayerHasOneOfAssertion(Player p, Card[] c) {
			player = p;
			cards = c;
		}

		public void handle() {
			// TODO
		}
	}

	/**
	 * This assertion represents the idea that we know a player has none of the given cards
	 */
	private class PlayerHasNoneOfAssertion implements Assertion {
		Player player;
		Card[] cards;

		public PlayerHasNoneOfAssertion(Player p, Card[] c) {
			player = p;
			cards = c;
		}

		public void handle() {
			// TODO
		}
	}
}

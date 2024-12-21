package org.example.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Basic modeler that fills out a scorecard based purely off known information
 */
public class BasicModel extends AbstractModel {
	protected final PlayerList players;

	protected final Queue<Assertion> unhandledAssertions = new LinkedList<>();

	public BasicModel(PlayerList players, Player self, Card[] known, Card[] owned) {
		super(players, self, known, owned);
		this.players = players;
	}

	@Override
	public void addQuery(Query query) {
		if (query.answered() != null) {
			if (query.answer() != null) {
				// we know that the player has the card
				unhandledAssertions.add(new PlayerHasAssertion(query.answered(), query.answer()));
			} else {
				// we know that the player who answered must have one of the three cards
				unhandledAssertions.add(new PlayerHasOneOfAssertion(query.answered(), query.cards()));
			}
		}

		// we know that everyone between asker and answerer does not have the card
		// if there was no answerer, then we know that everyone but the asker does not have the card
		Player answered = query.answered();
		if (answered == null) answered = query.asker();
		Iterator<Player> iter = players.iterator(query.asker(), answered);
		// consume the asker out of the iteration
		iter.next();
		while (iter.hasNext()) {
			Player p = iter.next();
			for (Card c : query.cards()) {
				unhandledAssertions.add(new PlayerDoesNotHaveAssertion(p, c));
			}
		}

		// handle all our assertions (and subsequently triggered assertions
		handleAssertions();
	}

	/**
	 * Handles all the assertions we know about the game state
	 * Also handles all assertions spawned by handling assertions
	 */
	protected void handleAssertions() {
		while (!unhandledAssertions.isEmpty()) {
			unhandledAssertions.remove().handle();
		}
	}

	/**
	 * A statement that we now know to be true about the state of the game
	 * These assertions, when handled, will change the scorecard to reflect what
	 * we know about the cards of each player
	 */
	protected interface Assertion {
		/**
		 * Implement any changes caused by this assertion to the scorecard
		 * This might spawn new assertions that will be added to unhandledAssertions
		 */
		void handle();
	}

	/**
	 * This assertion represents the idea that we know a certain player has a certain card
	 */
	protected class PlayerHasAssertion implements Assertion {
		Player player;
		Card card;

		public PlayerHasAssertion(Player p, Card c) {
			player = p;
			card = c;
		}

		public void handle() {
			Map<Card, Knowledge> playercard = scorecard.get(player);
			Knowledge prior = playercard.getOrDefault(card, Knowledge.MIGHT_HAVE);

			// already known
			if (prior == Knowledge.HAS) return;

			// problem has occurred
			if (prior == Knowledge.NO_HAS || prior == Knowledge.KNOWN)
				throw new IllegalStateException("PlayerHasAssertion: Card " + card.toString() +
						" already marked as not had by player " + player.toString());

			// delete groups that contain this card
			// they no longer have meaning because we know this exists
			Group[] groups = new Group[prior.groups.size()];
			prior.groups.toArray(groups);
			for (Group g : groups) {
				for (Knowledge k : g.contents.values()) {
					k.groups.remove(g);
				}
			}

			// set as must have in scorecard
			playercard.put(card, Knowledge.HAS);

			// assert that no other player has this card
			Iterator<Player> iter = players.iterator(player, player);
			while (iter.hasNext())
				unhandledAssertions.add(new PlayerDoesNotHaveAssertion(iter.next(), card));
		}
	}

	protected class PlayerDoesNotHaveAssertion implements Assertion {
		Player player;
		Card card;

		public PlayerDoesNotHaveAssertion(Player p, Card c) {
			player = p;
			card = c;
		}

		public void handle() {
			Map<Card, Knowledge> playercard = scorecard.get(player);
			Knowledge prior = playercard.getOrDefault(card, Knowledge.MIGHT_HAVE);

			// already known
			if (prior == Knowledge.NO_HAS || prior == Knowledge.KNOWN) return;

			// problem has occurred
			if (prior == Knowledge.HAS)
				throw new IllegalStateException("PlayerDoesNotHaveAssertion: Card " + card.toString() +
						" already marked as had by player " + player.toString());

			// delete this card from groups
			for (Group g : prior.groups) {
				g.contents.remove(card);

				if (g.contents.size() == 1) {
					// if the group size is now one, we know that the player has to have the last
					// card remaining in the group
					Card value = g.contents.keySet().iterator().next();
					unhandledAssertions.add(new PlayerHasAssertion(player, value));
				}
				if (g.contents.isEmpty()) {
					// player must have one of empty group?
					throw new IllegalStateException("PlayerDoesNotHaveAssertion: player must have at least card from empty set");
				}
			}

			// set this card as not had
			playercard.put(card, Knowledge.NO_HAS);
		}
	}

	/**
	 * This assertion represents the idea that we know a player has one of a set of cards
	 */
	protected class PlayerHasOneOfAssertion implements Assertion {
		Player player;
		Card[] cards;

		public PlayerHasOneOfAssertion(Player p, Card[] c) {
			player = p;
			cards = c;
		}

		public void handle() {
			Map<Card, Knowledge> playercard = scorecard.get(player);

			Knowledge[] prior = new Knowledge[3];
			int mighthavecount = 0;
			for (int i = 0; i < prior.length; i++) {
				prior[i] = playercard.get(cards[i]);
				if (prior[i] == Knowledge.HAS) return;
				if (prior[i] == null || prior[i] == Knowledge.MIGHT_HAVE) mighthavecount++;
			}

			if (mighthavecount == 1) {
				// Find the 1 card they might have and assert that they must have it
				// because we know that they don't have the other two cards
				for (int i = 0; i < prior.length; i++) {
					if (prior[i] == null || prior[i] == Knowledge.MIGHT_HAVE) {
						unhandledAssertions.add(new PlayerHasAssertion(player, cards[i]));
						return;
					}
				}
				throw new IllegalStateException("Bad logic");
			}

			if (mighthavecount == 0)
				throw new IllegalStateException("PlayerHasOneOfAssertion: player " + player.toString() +
						" does not have any of " + cards[0].toString() + ", " +
						cards[1].toString() + ", or " + cards[2].toString());

			// Add all might haves into a group
			Group g = new Group();
			for (int i = 0; i < prior.length; i++) {
				if (prior[i] == null) {
					prior[i] = Knowledge.MIGHT_HAVE;
					playercard.put(cards[i], prior[i]);
				}

				if (prior[i] == Knowledge.MIGHT_HAVE) {
					prior[i].groups.add(g);
					g.contents.put(cards[i], prior[i]);
				}
			}
		}
	}
}

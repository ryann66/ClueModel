package org.example.model;

import java.util.*;

/**
 * Basic modeler that fills out a scorecard based purely off known information
 */
public class BasicModel extends AbstractModel {
	protected final PlayerList players;

	protected final Queue<Assertion> unhandledAssertions = new LinkedList<>();
	private final Set<Player> modifiedPlayers;

	public BasicModel(PlayerList players, Player self, Card[] known, Card[] owned) {
		super(players, self, known, owned);
		this.players = players;
		this.modifiedPlayers = new HashSet<>(players.getPlayerCount());
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
		do {
			while (!unhandledAssertions.isEmpty()) {
				unhandledAssertions.remove().handle();
			}
			for (Player mod : modifiedPlayers) checkFiniteCards(mod);
			modifiedPlayers.clear();
		} while (!unhandledAssertions.isEmpty());
	}

	/**
	 * Returns true if every element in constr shares a bit with at least one of the elements in rep
	 * @param constr the array of elements to check
	 * @param rep the array of bits to check for
	 * @return true if every element in constr shares a bit with an element of rep
	 */
	private static boolean everyElementedRepresented(int[] constr, int[] rep) {
		int chk = 0;
		for (int j : rep) chk |= j;
		for (int k : constr) {
			if ((k & chk) == 0) return false;
		}
		return true;
	}

	/**
	 * Returns true if any combination of n elements from the array represent satisfies all constraints in the array
	 * constraints
	 * Skips the first skip elements from the array represent
	 * This method is recursive, when invoking, prev should be a new int array of length n
	 * and skip should be set to 0
	 * @param constraints the list of constraints that need to be satisfied
	 * @param represent the list of possible elements to represent
	 * @param n the number of elements in a valid combination
	 * @param skip the number of elements from the beginning of represent to skip when building a set to represent
	 * @param prev the array of elements already selected
	 * @return if any combination of n elements from represent satisfy all constraints
	 */
	private static boolean anyCombinationRepresented(int[] constraints, int[] represent, int n, int[] prev, int skip) {
		// check every combination of n items
		// if represents, return true
		// if exhausted combinations, return false

		if (n == 0) return everyElementedRepresented(constraints, prev);
		if (skip + n > represent.length) return false;

		// try adding the first element
		prev[n - 1] = represent[skip];
		if (anyCombinationRepresented(constraints, represent, n - 1, prev, skip + 1)) return true;

		// try skipping the first element
		return anyCombinationRepresented(constraints, represent, n, prev, skip + 1);
	}

	/**
	 * Checks to see if any knowledge can be gained by reasoning against the total number of cards the player is holding
	 * May generate assertions if it learns information
	 */
	protected void checkFiniteCards(Player p) {
		// Idea: if a player has 4 cards, we know 3 of them, and he must have one out of two different sets
		// then we know that the player must have one out of the intersection of these two sets
		// we can thus shrink/merge the sets and, if the size goes to 1, we can say he has the card
		// Finally, if we know all the cards in the players hand, then we can say that he doesn't have any others

		// check each player
		Map<Card, Knowledge> card = scorecard.get(p);

		// Find the number of constraints, possible cards and known cards
		Set<Group> constraints = new HashSet<>();
		Set<Card> possible = new HashSet<>(Card.NUM_CARDS);
		int numunknown = p.numCards();
		for (Card c : Card.values()) {
			Knowledge knowl = card.get(c);
			if (knowl != null) {
				switch (knowl) {
					case HAS -> {
						numunknown--;
						if (numunknown == 0) {
							// nothing more to learn
							return;
						}
					}
					case MIGHT_HAVE -> {
						// add constraints
						possible.add(c);
						constraints.addAll(knowl.groups);
					}
				}
			}
		}

		// check heuristics
		if (numunknown >= constraints.size()) {
			// we can satisfy every constraint with its own card, so no knowledge can possibly be gained
			return;
		}

		// check for independent constraints
		// if a constraint has no overlap with another constraint, we won't be able to resolve it, so we can
		// remove the constraint and improve the runtime
		Iterator<Group> coniter = constraints.iterator();
		outer: while (coniter.hasNext()) {
			Group con = coniter.next();
			for (Knowledge k : con.contents.values()) {
				// if one of the cards is in 2 groups, we assume it to be non-independent
				// not perfect, but good enough for a heuristic
				if (k.groups.size() != 1) continue outer;
			}

			// set aside a card to satisfy this constraint
			numunknown--;

			// remove this constraint from consideration
			coniter.remove();

			// remove the cards in this constraint as possibilities
			possible.removeAll(con.contents.keySet());
		}

		// if there aren't any unknown cards left, and we still need to satisfy constraints, it must be impossible
		if (numunknown < 0 || (numunknown == 0 && !constraints.isEmpty()))
			throw new IllegalStateException("Impossible to satisfy constraints");

		// convert cards and constraints to bit-stuffed int form
		// in this form, each card has is assigned a bit index in an int
		// constraints are formed by having multiple bits set, each bit representing a card that could satisfy the constraint
		// cards are formed by having only that cards bit set
		int[] constraintsArr = new int[constraints.size()];
		int[] cardsArr = new int[possible.size()];
		{
			int i = 0;
			for (Card c : possible) {
				cardsArr[i++] = 1 << c.ordinal();
			}
			i = 0;
			for (Group g : constraints) {
				for (Card c : g.contents.keySet()) {
					constraintsArr[i] |= 1 << c.ordinal();
				}
				i++;
			}
		}

		// do a preliminary check on all the cards to see if we can satisfy the constraints
		if (!anyCombinationRepresented(constraintsArr, cardsArr, cardsArr.length, new int[cardsArr.length], 0)) {
			throw new IllegalStateException("Impossible to satisfy constraints!");
		}

		// set up an array with all cards except the first one, which we save
		int skip = cardsArr[0];
		int[] partCardsArr = new int[cardsArr.length - 1];
		System.arraycopy(cardsArr, 1, partCardsArr, 0, cardsArr.length - 1);

		int[] holder = new int[partCardsArr.length];

		// check each card in sequence to see if it is essential to satisfy the constraints
		// we do this by removing the card from the sequence and testing if we can still satisfy constraints

		// we start by checking the first card
		if (!anyCombinationRepresented(constraintsArr, partCardsArr, partCardsArr.length, holder, 0)) {
			// get the card represented by the int
			for (Card c : possible) {
				if (1 << c.ordinal() == skip) {
					unhandledAssertions.add(new PlayerHasAssertion(p, c));
					break;
				}
			}
			// return, we'll come back to recheck player after handling assertions
			return;
		}

		// check the rest of the cards
		for (int i = 0; i < partCardsArr.length; i++) {
			// swap
			int tmp = partCardsArr[i];
			partCardsArr[i] = skip;
			skip = tmp;

			// check
			if (!anyCombinationRepresented(constraintsArr, partCardsArr, partCardsArr.length, holder, 0)) {
				// get the card represented by the int
				for (Card c : possible) {
					if (1 << c.ordinal() == skip) {
						unhandledAssertions.add(new PlayerHasAssertion(p, c));
						break;
					}
				}
				// return, we'll come back to recheck player after handling assertions
				return;
			}
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
			modifiedPlayers.add(player);

			// assert that no other player has this card
			Iterator<Player> iter = players.iterator(player, player);
			while (iter.hasNext())
				unhandledAssertions.add(new PlayerDoesNotHaveAssertion(iter.next(), card));

			// check to see if this player has max cards in hand
			// if so, assert they can't have any more cards
			// todo
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
			modifiedPlayers.add(player);
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

				// todo: refactor knowledge to record instead of enum to allow instantiation and separate
				// 	sets of groups for each card
				if (prior[i] == Knowledge.MIGHT_HAVE) {
					prior[i].groups.add(g);
					g.contents.put(cards[i], prior[i]);
				}
			}
			modifiedPlayers.add(player);
		}
	}
}

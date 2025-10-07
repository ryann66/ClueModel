package com.cluemodeler.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

interface Scorecard extends ImmutableScorecard {

	/**
	 * Marks the scorecard for the given player and card with knowledge k
	 * @param p the player to mark
	 * @param c the card to mark
	 * @param k the knowledge to be marked
	 */
	void mark(Player p, Card c, Knowledge k);

	/**
	 * Gets a mutable view of the scorecard for a given player
	 * @param p the player to get a scorecard for
	 * @return a mutable view of the scorecard for a given player
	 */
	@Override
	PlayerScorecard get(Player p);

	/**
	 * Commits all changes to the scorecard since the last commit/restore
	 */
	void commit();

    default void verifyState(PlayerList players) {
        // set of cards that might be in the envelope
        Set<Card> murderable = new HashSet<>(Arrays.asList(Card.values()));
        for (Card c : Card.values()) {
            int cnt = 0;
            for (Player p : players) {
                Knowledge k = get(p, c);

                // if it is found, remove from murder suspects
                if (k.t == Knowledge.T.HAS || k.t == Knowledge.T.KNOWN) murderable.remove(c);

                // count number of players with card
                if (k.t == Knowledge.T.HAS) cnt++;
            }

            if (cnt > 1) throw new IllegalStateException("Multiple cardholders: " + c.toString());
        }

        // verify the murderable set still has at least on in each category
        int mask = 0;
        for (Card c : murderable) {
            mask |= switch (c.type) {
                case WEAPON -> 1;
                case PERSON -> 2;
                case LOCATION -> 4;
            };
        }
        if (mask != 7) throw new IllegalStateException("All cards in a category are known!");
    }

	/**
	 * A mutable view of a player's scorecard
	 */
	interface PlayerScorecard extends ImmutablePlayerScorecard {
		/**
		 * Marks the player's scorecard for the given card with the given knowledge
		 * @param c the card to mark
		 * @param k the knowledge to mark
		 */
		void mark(Card c, Knowledge k);
	}
}

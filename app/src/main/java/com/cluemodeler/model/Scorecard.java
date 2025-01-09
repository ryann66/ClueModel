package com.cluemodeler.model;

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

	/**
	 * Rejects all the changes to the scorecard since the last commit/restore
	 */
	void restore();

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

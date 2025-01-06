package org.example.model;

interface Scorecard extends ImmutableScorecard {
	void mark(Player p, Card c, Knowledge k);

	Knowledge get(Player p, Card c);

	PlayerScorecard get(Player p);

	interface PlayerScorecard extends ImmutablePlayerScorecard {
		void mark(Card c, Knowledge k);

		public Knowledge get(Card c);
	}
}

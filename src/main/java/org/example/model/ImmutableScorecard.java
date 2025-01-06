package org.example.model;

public interface ImmutableScorecard {
	Knowledge get(Player p, Card c);

	ImmutablePlayerScorecard get(Player p);

	interface ImmutablePlayerScorecard {
		public Knowledge get(Player p, Card c);
	}
}

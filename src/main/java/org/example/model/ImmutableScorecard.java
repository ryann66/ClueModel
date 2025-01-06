package org.example.model;

import java.util.Map;

public interface ImmutableScorecard {
	Knowledge get(Player p, Card c);

	ImmutablePlayerScorecard get(Player p);

	interface ImmutablePlayerScorecard extends Iterable<Map.Entry<Card, Knowledge>> {
		Knowledge get(Card c);
	}
}

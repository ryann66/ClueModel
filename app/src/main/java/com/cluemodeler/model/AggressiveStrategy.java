package com.cluemodeler.model;

import java.util.Iterator;

public class AggressiveStrategy extends AbstractStrategy {
	AggressiveStrategy(ImmutableScorecard card, PlayerList plist, Player self) {
		super(card, plist, self);
	}

	@Override
	public Guess generateQuestion() {
		// if the given card is blocked
		boolean[] blocked = new boolean[Card.NUM_CARDS];
		// Card.ordinal() -> depth
		// depth is the minimum number of people that will allow a question
		// containing this card to pass by them
		int[] depth = new int[Card.NUM_CARDS];
		// the number of people who know this card is not involved in homicide
		int[] known = new int[Card.NUM_CARDS];

		Iterator<Player> piter = plist.iterator(self, self);
		piter.forEachRemaining((Player p) -> {
			ImmutableScorecard.ImmutablePlayerScorecard pcard = card.get(p);
			for (Card c : Card.values()) {
				if (pcard.get(c).t != Knowledge.T.HAS) {
					if (!blocked[c.ordinal()]){
						depth[c.ordinal()]++;
					}
					if (pcard.get(c).t == Knowledge.T.KNOWN) {
						known[c.ordinal()]++;
					}
				} else {
					blocked[c.ordinal()] = true;
				}
			}
		});

		// add in our own knowledge
		ImmutableScorecard.ImmutablePlayerScorecard pcard = card.get(self);
		for (Card c : Card.values()) {
			if (pcard.get(c).t == Knowledge.T.KNOWN) {
				known[c.ordinal()]++;
			}
		}

		// sort out all cards that have a depth of (num players - 1)
		// and have a known of 0 (not common cards)
		// select any 3 of these cards
		Card[] cards = new Card[Card.Type.values().length];
		for (Card c : Card.values()) {
			if (depth[c.ordinal()] == plist.getPlayerCount() - 1 && known[c.ordinal()] == 0) {
				cards[c.type.ordinal()] = c;
			}
		}

		return new Guess(cards[Card.Type.WEAPON.ordinal()], cards[Card.Type.PERSON.ordinal()], cards[Card.Type.LOCATION.ordinal()]);
	}
}

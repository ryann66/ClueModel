package org.example.model;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Array based implementation of Scorecard
 */
class ArrayScorecard implements Scorecard {
	int idx = 0;

	Player[] parr;

	// Knowledge[PlayerID][Card Ordinal]
	Knowledge[][] karr;

	ArrayScorecard(PlayerList players) {
		parr = new Player[players.getPlayerCount()];
		karr = new Knowledge[parr.length][Card.NUM_CARDS];
	}

	@Override
	public void mark(Player p, Card c, Knowledge k) {
		int i = getPlayerIndex(p);
		karr[i][c.ordinal()] = k;
	}

	@Override
	public Knowledge get(Player p, Card c) {
		return karr[getPlayerIndex(p)][c.ordinal()];
	}

	@Override
	public PlayerScorecard get(Player p) {
		return new ArrayPlayerScorecard(getPlayerIndex(p));
	}

	private int getPlayerIndex(Player p) {
		if (parr[idx].equals(p)) return idx;
		for (int i = idx + 1; i != idx; i++) {
			if (i == parr.length) i = 0;
			if (parr[i].equals(p)) {
				// cache index
				idx = i;
				return idx;
			}
		}
		throw new NoSuchElementException();
	}

	/**
	 * Inner player card view accessor for ArrayScorecard
	 */
	private class ArrayPlayerScorecard implements PlayerScorecard {
		int pidx;

		ArrayPlayerScorecard(int playerIdx) {
			this.pidx = playerIdx;
		}

		@Override
		public Knowledge get(Card c) {
			return null;
		}

		@Override
		public void mark(Card c, Knowledge k) {

		}

		@Override
		public Iterator<Map.Entry<Card, Knowledge>> iterator() {
			return new CardIterator(pidx);
		}
	}

	class CardIterator implements Iterator<Map.Entry<Card, Knowledge>> {
		final int pidx;
		int cidx = 0;

		CardIterator(int playerIdx) {
			pidx = playerIdx;
		}

		@Override
		public boolean hasNext() {
			return cidx < Card.NUM_CARDS;
		}

		@Override
		public Map.Entry<Card, Knowledge> next() {
			Card c = Card.values()[cidx++];
			return new AbstractMap.SimpleEntry<>(c, karr[pidx][cidx]);
		}
	}
}

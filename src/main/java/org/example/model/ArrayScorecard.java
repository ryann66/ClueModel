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
		int i = 0;
		for (Player p : players) {
			parr[i++] = p;
		}
	}

	@Override
	public void mark(Player p, Card c, Knowledge k) {
		int i = getPlayerIndex(p);
		karr[i][c.ordinal()] = k;
	}

	@Override
	public Knowledge get(Player p, Card c) {
		Knowledge ret = karr[getPlayerIndex(p)][c.ordinal()];
		if (ret == null) return Knowledge.MIGHT_HAVE_DEFAULT;
		return ret;
	}

	@Override
	public PlayerScorecard get(Player p) {
		return new ArrayPlayerScorecard(getPlayerIndex(p));
	}

	@Override
	public boolean confidentGuess() {
		Card weapon = null, person = null, location = null;
		for (Card c : Card.values()) {
			boolean known = false;
			for (int i = 0; i < parr.length; i++) {
				Knowledge k = karr[i][c.ordinal()];
				if (k != null && (k.t == Knowledge.T.HAS || k.t == Knowledge.T.KNOWN)) {
					known = true;
					break;
				}
			}
			if (!known) {
				switch (c.type) {
					case WEAPON -> {
						if (weapon != null) return false;
						weapon = c;
					}
					case PERSON -> {
						if (person != null) return false;
						person = c;
					}
					case LOCATION -> {
						if (location != null) return false;
						location = c;
					}
				}
			}
		}
		return true;
	}

	@Override
	public Guess guess() {
		Card weapon = null, person = null, location = null;
		for (Card c : Card.values()) {
			boolean known = false;
			for (int i = 0; i < parr.length; i++) {
				Knowledge k = karr[i][c.ordinal()];
				if (k != null && (k.t == Knowledge.T.HAS || k.t == Knowledge.T.KNOWN)) {
					known = true;
					break;
				}
			}
			if (!known) {
				switch (c.type) {
					case WEAPON -> {
						if (weapon == null) weapon = c;
					}
					case PERSON -> {
						if (person == null) person = c;
					}
					case LOCATION -> {
						if (location == null) location = c;
					}
				}
			}
		}
		return new Guess(weapon, person, location);
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
			Knowledge ret = karr[pidx][c.ordinal()];
			if (ret == null) return Knowledge.MIGHT_HAVE_DEFAULT;
			return ret;
		}

		@Override
		public void mark(Card c, Knowledge k) {
			karr[pidx][c.ordinal()] = k;
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
			if (cidx == Card.NUM_CARDS) throw new NoSuchElementException();
			Card c = Card.values()[cidx];
			Knowledge k = karr[pidx][cidx++];
			if (k == null) k = Knowledge.MIGHT_HAVE();
			return new AbstractMap.SimpleEntry<>(c, k);
		}
	}
}

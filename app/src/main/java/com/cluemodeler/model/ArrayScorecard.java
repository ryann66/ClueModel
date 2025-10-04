package com.cluemodeler.model;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Array based implementation of Scorecard
 */
class ArrayScorecard implements Scorecard {
	int idx = 0;

	Player[] parr;

	// Knowledge[PlayerID][Card Ordinal]
	Knowledge[][] karr;

	// array of knowledge converted to int using the following scheme
	// -1 => null
	// -2 => NO_HAS
	// -3 => HAS
	// -4 => KNOWN
	// 0+n => MIGHT_HAVE
	// 	where n is the number of groups to pull from bkpmighthave
	//	thus a value of 1 indicates that this field is MIGHT_HAVE with 1 group that can be generated
	//	by removing the first array from bpkmighthave
	int[][] bkpkarr;
	Queue<Card[]> bkpmighthave;

    boolean dirty;

	ArrayScorecard(PlayerList players) {
		parr = new Player[players.getPlayerCount()];
		karr = new Knowledge[parr.length][Card.NUM_CARDS];
		{
			int i = 0;
			for (Player p : players) {
				parr[i++] = p;
			}
		}
		bkpkarr = new int[parr.length][Card.NUM_CARDS];
		bkpmighthave = new LinkedList<>();
		for (int[] arr : bkpkarr) {
			Arrays.fill(arr, -1);
		}

        commit();
	}

    @Override
    public boolean isDirty() {
        return dirty;
    }

	@Override
	public void mark(Player p, Card c, Knowledge k) {
		int i = getPlayerIndex(p);

        // do not update if overwriting a known marker with a no has
        if (karr[i][c.ordinal()] == null || karr[i][c.ordinal()].t != Knowledge.T.KNOWN || k.t != Knowledge.T.NO_HAS) {
		    karr[i][c.ordinal()] = k;
            dirty = true;
        }
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
	public void commit() {
        if (!isDirty()) return;
		// overwrite backup
		bkpmighthave.clear();
		for (int i = 0; i < karr.length; i++) {
			for (int j = 0; j < karr[i].length; j++) {
				if (karr[i][j] == null) bkpkarr[i][j] = 0;
				else {
					bkpkarr[i][j] = switch (karr[i][j].t) {
						case MIGHT_HAVE -> {
							// add to bkpmighthave
							for (Group g : karr[i][j].groups) {
								bkpmighthave.add(g.contents.keySet().toArray(new Card[0]));
							}
							yield karr[i][j].groups.size();
						}
						case NO_HAS -> -2;
						case HAS -> -3;
						case KNOWN -> -4;
					};
				}
			}
		}

        dirty = false;
	}

	@Override
	public void restore() {
        if (!isDirty()) return;
		// overwrite karr with correct type data
		for (int i = 0; i < karr.length; i++) {
			for (int j = 0; j < karr[i].length; j++) {
				karr[i][j] = switch (bkpkarr[i][j]) {
					case -1 -> null;
					case -2 -> Knowledge.NO_HAS();
					case -3 -> Knowledge.HAS();
					case -4 -> Knowledge.KNOWN();
					case 0 -> Knowledge.MIGHT_HAVE_DEFAULT;
					default -> {
						Knowledge knowledge = Knowledge.MIGHT_HAVE();
						for (int k = 0; k < bkpkarr[i][j]; k++) {
							Group group = new Group();
							Card[] cards = bkpmighthave.remove();
							for (Card c : cards) {
								group.contents.put(c, null);
							}
							knowledge.groups.add(group);
						}
						yield knowledge;
					}
				};
			}

            dirty = false;
		}

		// stitch group knowledge back together
		for (Knowledge[] pckarr : karr) {
			for (Knowledge knowledge : pckarr) {
				if (knowledge.t != Knowledge.T.MIGHT_HAVE) continue;
				for (Group g : knowledge.groups) {
					for (Map.Entry<Card, Knowledge> entry : g.contents.entrySet()) {
						entry.setValue(pckarr[entry.getKey().ordinal()]);
					}
				}
			}
		}
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
            dirty = true;
		}

		@Override
		public @NotNull Iterator<Map.Entry<Card, Knowledge>> iterator() {
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

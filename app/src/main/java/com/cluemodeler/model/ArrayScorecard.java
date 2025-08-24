package com.cluemodeler.model;

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
		// todo: rewrite to indicate we know if nobody holds the card
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

	@Override
	public void commit() {
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
	}

	@Override
	public void restore() {
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

	public List<Card> missingWeapons() {
		List<Card> ret = new ArrayList<>(Card.WEAPONS.size());
		for (Card c : Card.WEAPONS) {
			int cidx = c.ordinal();
			for (Knowledge[] knowledges : karr) {
				if (knowledges[cidx] != null &&
						(knowledges[cidx].t == Knowledge.T.KNOWN || knowledges[cidx].t == Knowledge.T.HAS)) {
					ret.add(c);
					break;
				}
			}
		}
		return ret;
	}

	public List<Card> missingPeople() {
		List<Card> ret = new ArrayList<>(Card.PEOPLE.size());
		for (Card c : Card.PEOPLE) {
			int cidx = c.ordinal();
			for (Knowledge[] knowledges : karr) {
				if (knowledges[cidx] != null &&
						(knowledges[cidx].t == Knowledge.T.KNOWN || knowledges[cidx].t == Knowledge.T.HAS)) {
					ret.add(c);
					break;
				}
			}
		}
		return ret;
	}

	public List<Card> missingLocations() {
		List<Card> ret = new ArrayList<>(Card.LOCATIONS.size());
		for (Card c : Card.LOCATIONS) {
			int cidx = c.ordinal();
			for (Knowledge[] knowledges : karr) {
				if (knowledges[cidx] != null &&
						(knowledges[cidx].t == Knowledge.T.KNOWN || knowledges[cidx].t == Knowledge.T.HAS)) {
					ret.add(c);
					break;
				}
			}
		}
		return ret;
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

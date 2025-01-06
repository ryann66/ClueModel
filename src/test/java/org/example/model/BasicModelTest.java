package org.example.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BasicModelTest {
	private static final String[] playerNames = new String[]{"Self", "Alice", "Bob", "Charles", "Dan", "Eric"};

	private static Player[] makePlayers(int np) {
		int nc = Card.NUM_CARDS / np;
		Player[] parr = new Player[np];
		for (int i = 0; i < np; i++) {
			parr[i] = new Player(playerNames[i], nc);
		}
		return parr;
	}

	@Test
	@DisplayName("Create Model Test")
	public void CreateModelTest() {
		Player[] parr = makePlayers(3);
		PlayerList plist = new PlayerList(parr);
		Card[] common = new Card[]{Card.WEAPONS.get(0), Card.PEOPLE.get(0)};
		Card[] mine = new Card[]{Card.LOCATIONS.get(0), Card.WEAPONS.get(1), Card.PEOPLE.get(1), Card.LOCATIONS.get(1)};

		BasicModel bm = new BasicModel(plist, parr[0], common, mine);

		ScorecardTester st = new ScorecardTester(bm);

		Set<Card> notmine = new HashSet<>(Card.NUM_CARDS);
		notmine.addAll(Card.WEAPONS);
		notmine.addAll(Card.PEOPLE);
		notmine.addAll(Card.LOCATIONS);

		for (Card c : mine) {
			st.checkOwns(parr[0], c);
			notmine.remove(c);
		}
		for (Card c : common) {
			for (Player p : bm.players) st.checkKnown(p, c);
			notmine.remove(c);
		}

		for (Card c : notmine) {
			st.checkNoHas(parr[0], c);
		}

		st.checkAllElseNull();


		// redo test with 6 players
		parr = makePlayers(6);
		plist = new PlayerList(parr);
		common = new Card[0];
		mine = new Card[]{Card.WEAPONS.get(0), Card.PEOPLE.get(0), Card.LOCATIONS.get(0)};

		bm = new BasicModel(plist, parr[0], common, mine);

		st = new ScorecardTester(bm);

		notmine = new HashSet<>(Card.NUM_CARDS);
		notmine.addAll(Card.WEAPONS);
		notmine.addAll(Card.PEOPLE);
		notmine.addAll(Card.LOCATIONS);

		for (Card c : mine) {
			st.checkOwns(parr[0], c);
			notmine.remove(c);
		}

		for (Card c : notmine) {
			st.checkNoHas(parr[0], c);
		}

		st.checkAllElseNull();
	}

	@Test
	@DisplayName("Ask Test")
	public void AskTest() {
		// general setup
		Player[] parr = makePlayers(6);
		PlayerList plist = new PlayerList(parr);
		Card[] common = new Card[0];
		Card[] mine = new Card[]{Card.WEAPONS.get(0), Card.PEOPLE.get(0), Card.LOCATIONS.get(0)};
		BasicModel bm = new BasicModel(plist, parr[0], common, mine);
		ScorecardTester st = new ScorecardTester(bm);

		


	}

	@Test
	@DisplayName("Add Test")
	public void AddTest() {

	}




	public static class ScorecardTester {
		AbstractModel model;
		Map<Player, Map<Card, Boolean>> hasTouched;

		public ScorecardTester(AbstractModel m) {
			model = m;
			hasTouched = new HashMap<>(m.scorecard.size());
			for (Player p : m.scorecard.keySet()) {
				HashMap<Card, Boolean> map = new HashMap<>(Card.NUM_CARDS);
				hasTouched.put(p, map);
				for (Card c : Card.values()) {
					map.put(c, false);
				}
			}
		}

		private void check(Player p, Card c, Knowledge.T t) {
			Knowledge k = model.scorecard.get(p).get(c);
			assertNotNull(k);
			assertEquals(k.t, t);
			hasTouched.get(p).put(c, true);
		}

		public void checkNull(Player p, Card c) {
			assertNull(model.scorecard.get(p).get(c));
			hasTouched.get(p).put(c, true);
		}

		public void checkHas(Player p, Card c) {
			check(p, c, Knowledge.T.HAS);
		}

		public void checkNoHas(Player p, Card c) {
			check(p, c, Knowledge.T.NO_HAS);
		}

		public void checkMightHave(Player p, Card c) {
			check(p, c, Knowledge.T.MIGHT_HAVE);
		}

		public void checkKnown(Player p, Card c) {
			check(p, c, Knowledge.T.KNOWN);
		}

		public void checkMustNotHave(Player p, Card c) {
			Knowledge k = model.scorecard.get(p).get(c);
			if (k != null) assertTrue(k.t == Knowledge.T.NO_HAS || k.t == Knowledge.T.KNOWN);
			hasTouched.get(p).put(c, true);
		}

		public void checkOwns(Player p, Card c) {
			for (Player p2 : model.scorecard.keySet()) {
				if (p2.equals(p)) checkHas(p, c);
				else checkMustNotHave(p2, c);
			}
		}

		/**
		 * Assert that everything except the elements that have been explicitly checked is null
		 */
		public void checkAllElseNull() {
			for (Map.Entry<Player, Map<Card, Boolean>> e : hasTouched.entrySet()) {
				Player p = e.getKey();
				for (Map.Entry<Card, Boolean> e2 : e.getValue().entrySet()) {
					Card c = e2.getKey();
					boolean touched = e2.getValue();
					if (!touched) {
						assertNull(model.scorecard.get(p).get(c), p.toString() + ", " + c.toString());
					}
				}
			}
		}
	}
}
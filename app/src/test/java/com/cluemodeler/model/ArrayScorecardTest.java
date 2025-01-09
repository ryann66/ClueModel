package com.cluemodeler.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayScorecardTest {
	@Test
	@DisplayName("Mark Test")
	public void MarkTest() {
		Player[] parr = BasicModelTest.makePlayers(6);
		PlayerList plist = new PlayerList(parr);
		ArrayScorecard sc = new ArrayScorecard(plist);

		Knowledge k = Knowledge.MIGHT_HAVE();
		sc.mark(parr[0], BasicModelTest.weapons[0], k);
		assertSame(k, sc.get(parr[0], BasicModelTest.weapons[0]));
	}

	@Test
	@DisplayName("Unknown Get Test")
	public void UnknownGetTest() {
		Player[] parr = BasicModelTest.makePlayers(6);
		PlayerList plist = new PlayerList(parr);
		ArrayScorecard sc = new ArrayScorecard(plist);

		assertSame(Knowledge.MIGHT_HAVE_DEFAULT, sc.get(parr[0], BasicModelTest.weapons[0]));
	}

	@Test
	@DisplayName("Commit test")
	public void CommitTest() {
		Player[] parr = BasicModelTest.makePlayers(6);
		PlayerList plist = new PlayerList(parr);
		ArrayScorecard asc = new ArrayScorecard(plist);

		asc.mark(parr[0], Card.values()[0], Knowledge.KNOWN());
		asc.mark(parr[0], Card.values()[1], Knowledge.NO_HAS());
		asc.mark(parr[1], Card.values()[1], Knowledge.HAS());
		asc.mark(parr[2], Card.values()[2], Knowledge.MIGHT_HAVE_DEFAULT);

		Knowledge k1 = Knowledge.MIGHT_HAVE();
		Knowledge k2 = Knowledge.MIGHT_HAVE();
		asc.mark(parr[3], Card.values()[3], k1);
		asc.mark(parr[3], Card.values()[4], k2);

		Group g = new Group();
		g.contents.put(Card.values()[3], k1);
		g.contents.put(Card.values()[4], k2);

		asc.commit();

		assertEquals(-4, asc.bkpkarr[0][0]);
		assertEquals(-2, asc.bkpkarr[0][1]);
		assertEquals(-3, asc.bkpkarr[1][1]);
		assertEquals(-1, asc.bkpkarr[4][0]);
		assertEquals(0, asc.bkpkarr[2][2]);

		assertEquals(1, asc.bkpkarr[3][3]);
		assertEquals(1, asc.bkpkarr[3][4]);
		assertEquals(2, asc.bkpmighthave.size());
		for (Card[] c : asc.bkpmighthave) {
			assertEquals(2, c.length);
			assertNotSame(c[0], c[1]);
			assertTrue(c[0] == Card.values()[3] || c[0] == Card.values()[4]);
			assertTrue(c[1] == Card.values()[3] || c[1] == Card.values()[4]);
		}
	}

	@Test
	@DisplayName("Restore Original Test")
	public void RestoreOriginalTest() {
		Player[] parr = BasicModelTest.makePlayers(6);
		PlayerList plist = new PlayerList(parr);
		ArrayScorecard asc = new ArrayScorecard(plist);

		asc.mark(parr[0], Card.values()[0], Knowledge.KNOWN());
		asc.mark(parr[0], Card.values()[1], Knowledge.NO_HAS());
		asc.mark(parr[1], Card.values()[1], Knowledge.HAS());
		asc.mark(parr[2], Card.values()[2], Knowledge.MIGHT_HAVE_DEFAULT);

		Knowledge k1 = Knowledge.MIGHT_HAVE();
		Knowledge k2 = Knowledge.MIGHT_HAVE();
		asc.mark(parr[3], Card.values()[3], k1);
		asc.mark(parr[3], Card.values()[4], k2);

		Group g = new Group();
		g.contents.put(Card.values()[3], k1);
		g.contents.put(Card.values()[4], k2);

		asc.restore();

		// check reset of bkp
		assertEquals(0,  asc.bkpmighthave.size());
		for (int[] arr : asc.bkpkarr) {
			for (int i : arr) {
				assertEquals(-1, i);
			}
		}

		// check reset of actual stuff
		for (Knowledge[] arr : asc.karr) {
			for (Knowledge k : arr) {
				assertSame(null, k);
			}
		}
	}

	@Test
	@DisplayName("Restore Simple Test")
	public void RestoreSimpleTest() {
		Player[] parr = BasicModelTest.makePlayers(6);
		PlayerList plist = new PlayerList(parr);
		ArrayScorecard asc = new ArrayScorecard(plist);

		asc.mark(parr[0], Card.values()[0], Knowledge.KNOWN());
		asc.mark(parr[0], Card.values()[1], Knowledge.NO_HAS());
		asc.mark(parr[1], Card.values()[1], Knowledge.HAS());
		asc.mark(parr[2], Card.values()[2], Knowledge.MIGHT_HAVE_DEFAULT);

		asc.commit();

		asc.mark(parr[0], Card.values()[0], Knowledge.NO_HAS());
		asc.mark(parr[0], Card.values()[1], Knowledge.HAS());
		asc.mark(parr[1], Card.values()[1], Knowledge.MIGHT_HAVE_DEFAULT);
		asc.mark(parr[2], Card.values()[2], Knowledge.KNOWN());

		asc.restore();

		assertEquals(Knowledge.T.KNOWN, asc.get(parr[0], Card.values()[0]).t);
		assertEquals(Knowledge.T.NO_HAS, asc.get(parr[0], Card.values()[1]).t);
		assertEquals(Knowledge.T.HAS, asc.get(parr[1], Card.values()[1]).t);
		assertEquals(Knowledge.T.MIGHT_HAVE, asc.get(parr[2], Card.values()[2]).t);
	}

	@Test
	@DisplayName("Restore Complex Test")
	public void RestoreComplexTest() {
		Player[] parr = BasicModelTest.makePlayers(6);
		PlayerList plist = new PlayerList(parr);
		ArrayScorecard asc = new ArrayScorecard(plist);

		Knowledge k1 = Knowledge.MIGHT_HAVE();
		Knowledge k2 = Knowledge.MIGHT_HAVE();
		asc.mark(parr[0], Card.values()[0], k1);
		asc.mark(parr[0], Card.values()[1], k2);

		{
			Group g = new Group();
			g.contents.put(Card.values()[3], k1);
			g.contents.put(Card.values()[4], k2);
		}

		asc.commit();

		asc.mark(parr[0], Card.values()[0], Knowledge.NO_HAS());
		asc.mark(parr[0], Card.values()[1], Knowledge.NO_HAS());

		asc.restore();

		k1 = asc.get(parr[0], Card.values()[0]);
		k2 = asc.get(parr[0], Card.values()[1]);
		assertEquals(Knowledge.T.MIGHT_HAVE, k1.t);
		assertEquals(Knowledge.T.MIGHT_HAVE, k2.t);

		assertNotNull(k1.groups);
		assertEquals(1, k1.groups.size());
		assertNotNull(k2.groups);
		assertEquals(2, k1.groups.size());

		Group g1 = k1.groups.iterator().next();
		Group g2 = k2.groups.iterator().next();
		assertEquals(g1.id, g2.id);

		assertNotNull(g1.contents.get(Card.values()[0]));
		assertNotNull(g1.contents.get(Card.values()[1]));

		assertSame(g1.contents.get(Card.values()[0]), g2.contents.get(Card.values()[0]));
		assertSame(g1.contents.get(Card.values()[1]), g2.contents.get(Card.values()[1]));
	}
}
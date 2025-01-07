package org.example.model;

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
}
package com.cluemodeler.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AggressiveStrategyTest {
	@Test
	@DisplayName("Aggressive Strategy Test")
	public void AggressiveStrategyTest() {
		Player[] parr = BasicModelTest.makePlayers(6);
		PlayerList plist = new PlayerList(parr);
		ArrayScorecard sc = new ArrayScorecard(plist);
		AggressiveStrategy agstr = new AggressiveStrategy(sc, plist, parr[0]);

		int remwep = Card.WEAPONS.size();
		int rempep = Card.PEOPLE.size();
		int remloc = Card.LOCATIONS.size();

		BasicModelTest.ScorecardTester st = new BasicModelTest.ScorecardTester(new ScorecardContainer(sc), plist);

		while (!sc.confidentGuess()) {
			// ask for a guess
			Guess g = agstr.generateQuestion();

			// assert that we don't know any of the components of the guess
			st.checkUnknown(g.weapon());
			st.checkUnknown(g.person());
			st.checkUnknown(g.location());

			// mark off any of the results (as long as there is more than one card remaining) as had
			if (remwep > 1) {
				remwep--;
				sc.mark(parr[1], g.weapon(), Knowledge.HAS());
			} else if (rempep > 1) {
				rempep--;
				sc.mark(parr[1], g.person(), Knowledge.HAS());
			} else if (remloc > 1) {
				remloc--;
				sc.mark(parr[1], g.location(), Knowledge.HAS());
			} else {
				fail();
			}
		}
	}

	static class ScorecardContainer extends AbstractModel {
		ScorecardContainer(Scorecard card) {
			super(new PlayerList(BasicModelTest.makePlayers(3)), BasicModelTest.makePlayers(1)[0], new Card[]{}, new Card[]{});
			this.scorecard = card;
		}

		@Override
		public void addQuery(Query query) {
			throw new UnsupportedOperationException("Scorecard Container is not a model!");
		}
	}
}

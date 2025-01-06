package org.example.model;

import java.util.*;
import org.example.model.Scorecard.PlayerScorecard;

/**
 * Basic starter class for models to share scoreboard usage
 */
abstract class AbstractModel implements Model {
	protected Scorecard scorecard;
	protected final Player self;

	protected AbstractModel(PlayerList players, Player self, Card[] known, Card[] owned) {
		if (players == null || self == null || known == null || owned == null)
			throw new IllegalArgumentException("Unexpected null argument");

		try {
			self = players.get(self.name());
		} catch (NoSuchElementException ignored) {
			throw new IllegalArgumentException("Self is not a player!");
		}

		this.self = self;

		// fill scorecard with maps
		scorecard = new ArrayScorecard(players);

		// block self owned cards for all players
		for (Player p : players) {
			if (p == self) continue;
			PlayerScorecard ps = scorecard.get(p);
			for (Card c : owned) {
				ps.mark(c, Knowledge.NO_HAS());
			}
		}

		PlayerScorecard selfcards = scorecard.get(self);
		// block all cards for self
		for (Card c : Card.values()) selfcards.mark(c, Knowledge.NO_HAS());
		// add owned cards as had
		for (Card c : owned) selfcards.mark(c, Knowledge.HAS());

		// add common cards
		for (Player p : players) {
			PlayerScorecard ps = scorecard.get(p);
			for (Card c : known) {
				ps.mark(c, Knowledge.KNOWN());
			}
		}
	}

	public final ImmutableScorecard.ImmutablePlayerScorecard getSimpleScorecard() {
		return scorecard.get(self);
	}

	public ImmutableScorecard getFullScorecard() {
		return scorecard;
	}
}

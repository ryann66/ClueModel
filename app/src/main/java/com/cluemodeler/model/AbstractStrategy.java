package com.cluemodeler.model;

abstract class AbstractStrategy implements Strategy {
	protected ImmutableScorecard card;
	protected PlayerList plist;
	protected Player self;

	AbstractStrategy(ImmutableScorecard card, PlayerList plist, Player self) {
		this.card = card;
		this.plist = plist;
		this.self = self;
	}
}

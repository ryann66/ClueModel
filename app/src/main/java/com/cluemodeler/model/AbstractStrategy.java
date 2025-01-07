package com.cluemodeler.model;

abstract class AbstractStrategy implements Strategy {
	protected ImmutableScorecard card;

	AbstractStrategy(ImmutableScorecard card) {
		this.card = card;
	}
}

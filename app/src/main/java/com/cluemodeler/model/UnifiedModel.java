package com.cluemodeler.model;

/**
 * Unified class that correctly sets up a model, scorecard, and strategy to work together
 * It is suggested but not required to use this class instead of piecemeal components
 */
public class UnifiedModel implements Model, ImmutableScorecard, Strategy {
	final Model model;
	final ImmutableScorecard card;
	final Strategy strategy;

	public UnifiedModel(PlayerList players, Player self, Card[] known, Card[] owned, Strategy.T strategy) {
		this.model = new BasicModel(players, self, known, owned);
		this.card = model.getFullScorecard();
		this.strategy = Strategy.buildStrategy(strategy, card);
	}

	@Override
	public Knowledge get(Player p, Card c) {
		return card.get(p, c);
	}

	@Override
	public ImmutablePlayerScorecard get(Player p) {
		return card.get(p);
	}

	@Override
	public boolean confidentGuess() {
		return card.confidentGuess();
	}

	@Override
	public Guess guess() {
		return card.guess();
	}

	@Override
	public ImmutablePlayerScorecard getSimpleScorecard() {
		return model.getSimpleScorecard();
	}

	@Override
	public ImmutableScorecard getFullScorecard() {
		return card;
	}

	@Override
	public void addQuery(Query query) {
		model.addQuery(query);
	}

	@Override
	public Guess generateQuestion() {
		return strategy.generateQuestion();
	}
}

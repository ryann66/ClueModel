package org.example.model;

public class BasicModel extends Model {
	private Player self;

	public BasicModel(PlayerList players, Player self, Card[] known) {
		super(players, self, known);
		this.self = self;
	}

	@Override
	public void addQuery(Query query) {
		if (query.cards() == null || query.cards().length != 3)
			throw new IllegalArgumentException("Bad query cards");
		if (query.asker() == null) throw new IllegalArgumentException("Null asker");
		// if we asked, then it should either be unanswered and no card, or answered with a card
		if (query.asker() == self && (query.answered() == null) == (query.answer() == null)) {
			// our own question
		}
		// if we did not ask, then we expect no answer
		else if (query.answer() == null) {
			// somebody else's question
		}
		throw new IllegalArgumentException("Inconsistent query params");
	}
}

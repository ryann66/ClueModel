package com.cluemodeler.model;

public interface Model {

	/**
	 * Builds a simple scorecard for what the player knows
	 * Consists of one column that is either card found (true) or
	 * card not found (false)
	 * @return a simple one column scorecard where true represents
	 * 		that a card has been found/eliminated and false represents
	 * 		that the card could still be in the envelope
	 */
	ImmutableScorecard.ImmutablePlayerScorecard getSimpleScorecard();

	ImmutableScorecard getFullScorecard();

	/**
	 * Process the given query and add its knowledge to the model
	 * @param query the query to process
	 */
	void addQuery(Query query);
}

package org.example.model;

/**
 * Interface for classes that model the scorecard
 */
public abstract class Model {
	/**
	 * Get what the model knows about the game
	 * @return a const wrapper to the scorecard
	 */
	public abstract ConstScorecard getScorecard();

	/**
	 * Process the given query and add its knowledge to the model
	 * @param query the query to process
	 */
	public abstract void addQuery(Query query);
}

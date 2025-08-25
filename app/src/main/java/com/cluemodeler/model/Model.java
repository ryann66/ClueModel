package com.cluemodeler.model;

public interface Model {

	ImmutableScorecard getFullScorecard();

	/**
	 * Process the given query and add its knowledge to the model
	 * @param query the query to process
	 */
	void addQuery(Query query);
}

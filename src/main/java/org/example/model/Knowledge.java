package org.example.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Abstract idea that something is known about a player's cards
 */
public enum Knowledge {
	// Player has the card
	HAS(null),

	// Player does not have the card
	NO_HAS(null),

	// Player has one of the cards in the set
	MIGHT_HAVE(new HashSet<>()),

	// Player doesn't have the card but knows where it is
	KNOWN(null);

	// package-private to protect against external mutation
	final Set<Group> groups;

	Knowledge(HashSet<Group> hs) {
		groups = hs;
	}
}

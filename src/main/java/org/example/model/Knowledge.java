package org.example.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Abstract idea that something is known about a player's cards
 */
public class Knowledge {
	private static final Knowledge HAS_DEFAULT = new Knowledge(T.HAS),
	NO_HAS_DEFAULT = new Knowledge(T.NO_HAS), KNOWN_DEFAULT = new Knowledge(T.KNOWN);

	public enum T {
		// Player has the card
		HAS,

		// Player does not have the card
		NO_HAS,

		// Player has one of the cards in the set
		MIGHT_HAVE,

		// Player doesn't have the card but knows where it is
		KNOWN;
	}

	// package-private to protect against external mutation
	final Set<Group> groups;

	public final T t;

	private Knowledge(T t) {
		this.t = t;

		if (t == T.MIGHT_HAVE) this.groups = new HashSet<>();
		else this.groups = null;
	}

	public static Knowledge HAS() {
		return HAS_DEFAULT;
	}

	public static Knowledge NO_HAS() {
		return NO_HAS_DEFAULT;
	}

	public static Knowledge MIGHT_HAVE() {
		return new Knowledge(T.MIGHT_HAVE);
	}

	public static Knowledge KNOWN() {
		return KNOWN_DEFAULT;
	}
}

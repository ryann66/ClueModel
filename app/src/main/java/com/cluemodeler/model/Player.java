package com.cluemodeler.model;

import org.jetbrains.annotations.NotNull;

/**
 * Record for players
 * @param name the name of the player
 * @param numCards the number of cards the player is holding
 */
public record Player(String name, int numCards) {
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Player player = (Player) o;
		return name.equalsIgnoreCase(player.name) && numCards == player.numCards();
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	public @NotNull String toString() {
		return name;
	}
}

package com.cluemodeler.model;

import org.jetbrains.annotations.NotNull;

/**
 * Record for players
 * name: the name of the player
 * numCards: the number of cards the player is holding
 */
public class Player {
    private final String name;
    private final int numCards;

    public Player(String name, int numCards) {
        this.name = name;
        this.numCards = numCards;
    }

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

    public String name() {
        return name;
    }

    public int numCards() {
        return numCards;
    }
}

package org.example.model;

/**
 * Record for players
 * @param name the name of the player
 */
public record Player(String name) {
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Player player = (Player) o;
		return name.equalsIgnoreCase(player.name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}

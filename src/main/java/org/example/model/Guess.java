package org.example.model;

public record Guess(Card weapon, Card person, Card location) {
	public Guess {
		if (weapon == null || weapon.type != Card.Type.WEAPON) throw new IllegalArgumentException();
		if (person == null || person.type != Card.Type.PERSON) throw new IllegalArgumentException();
		if (location == null || location.type != Card.Type.LOCATION) throw new IllegalArgumentException();
	}
}

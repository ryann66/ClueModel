package com.cluemodeler.model;

import java.util.Objects;

public final class Guess {
    private final Card weapon;
    private final Card person;
    private final Card location;

    public Guess(Card weapon, Card person, Card location) {
        if (weapon == null || weapon.type != Card.Type.WEAPON) throw new IllegalArgumentException();
        if (person == null || person.type != Card.Type.PERSON) throw new IllegalArgumentException();
        if (location == null || location.type != Card.Type.LOCATION) throw new IllegalArgumentException();
        this.weapon = weapon;
        this.person = person;
        this.location = location;
    }

    public Card weapon() {
        return weapon;
    }

    public Card person() {
        return person;
    }

    public Card location() {
        return location;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Guess) obj;
        return Objects.equals(this.weapon, that.weapon) &&
                Objects.equals(this.person, that.person) &&
                Objects.equals(this.location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weapon, person, location);
    }

    @Override
    public String toString() {
        return "Guess[" +
                "weapon=" + weapon + ", " +
                "person=" + person + ", " +
                "location=" + location + ']';
    }

}

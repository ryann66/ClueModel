package com.cluemodeler.model;

import java.util.Objects;

/**
 * Record class for representing questions asked
 */
public final class Query {
    private final Player asker;
    private final Player answered;
    private final Card[] cards;
    private final Card answer;

    public Query(Player asker, Player answered, Card[] cards, Card answer) {
        if (asker == null) throw new IllegalArgumentException("Asker must be non-null");
        if (cards == null) throw new IllegalArgumentException("Cards must not be null");
        if (cards.length != 3) throw new IllegalArgumentException("Must have 3 cards");
        if (asker.equals(answered)) throw new IllegalArgumentException("Cannot answer your own question");
        int sum = 0;
        // only check if answer is contained in questions if it's not null
        boolean anscon = answer == null;
        for (Card c : cards) {
            sum += switch (c.type) {
                case WEAPON -> 1;
                case PERSON -> 2;
                case LOCATION -> 4;
            };
            // if answer is not yet found, check if this card is answer
            if (!anscon) {
                anscon = c == answer;
            }
        }
        if (sum != 7) throw new IllegalArgumentException("Must have a weapon, person, and location card");
        if (answer != null && answered == null)
            throw new IllegalArgumentException("Queries with an answer must have an answerer");
        if (!anscon) throw new IllegalArgumentException("Answer card not in question");
        this.asker = asker;
        this.answered = answered;
        this.cards = cards;
        this.answer = answer;
    }

    public Player asker() {
        return asker;
    }

    public Player answered() {
        return answered;
    }

    public Card[] cards() {
        return cards;
    }

    public Card answer() {
        return answer;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Query) obj;
        return Objects.equals(this.asker, that.asker) &&
                Objects.equals(this.answered, that.answered) &&
                Objects.equals(this.cards, that.cards) &&
                Objects.equals(this.answer, that.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(asker, answered, cards, answer);
    }

    @Override
    public String toString() {
        return "Query[" +
                "asker=" + asker + ", " +
                "answered=" + answered + ", " +
                "cards=" + cards + ", " +
                "answer=" + answer + ']';
    }

}

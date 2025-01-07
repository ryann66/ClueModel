package com.cluemodeler.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Immutable record-like class for representing cards
 * Non-abstracting
 */
public enum Card {
	ROPE(Type.WEAPON), CANDLESTICK(Type.WEAPON), LEAD_PIPE(Type.WEAPON), PISTOL(Type.WEAPON), DAGGER(Type.WEAPON), WRENCH(Type.WEAPON),

	GREEN(Type.PERSON), MUSTARD(Type.PERSON), PEACOCK(Type.PERSON), WHITE(Type.PERSON), PLUM(Type.PERSON), SCARLET(Type.PERSON),

	COURTYARD(Type.LOCATION), GARAGE(Type.LOCATION), GAME_ROOM(Type.LOCATION), BEDROOM(Type.LOCATION), BATHROOM(Type.LOCATION),
	OFFICE(Type.LOCATION), KITCHEN(Type.LOCATION), DINING_ROOM(Type.LOCATION), LIVING_ROOM(Type.LOCATION);

	public static final int NUM_CARDS;

	public static final int MAX_CARD_STRING_LENGTH;

	public static final List<Card> WEAPONS, PEOPLE, LOCATIONS;

	// initialize constants
	static {
		NUM_CARDS = Card.values().length;

		int maxlen = 0;
		for (Card v : Card.values()) maxlen = Math.max(v.toString().length(), maxlen);
		MAX_CARD_STRING_LENGTH = maxlen;

		WEAPONS = new ArrayList<>(6);
		PEOPLE = new ArrayList<>(6);
		LOCATIONS = new ArrayList<>(9);

		for (Card v : Card.values()) {
			(switch (v.type) {
				case WEAPON -> WEAPONS;
				case PERSON -> PEOPLE;
				case LOCATION -> LOCATIONS;
			}).add(v);
		}
	}

	public final Type type;

	/**
	 * The type of card this is
	 */
	public enum Type {
		WEAPON, PERSON, LOCATION
	}

	Card(Type t) {
		this.type = t;
	}

	public String toString() {
		return switch (this) {
			case ROPE -> "Rope";
			case CANDLESTICK -> "Candlestick";
			case LEAD_PIPE -> "Lead Pipe";
			case PISTOL -> "Pistol";
			case DAGGER -> "Dagger";
			case WRENCH -> "Wrench";
			case GREEN -> "Mr. Green";
			case MUSTARD -> "Col. Mustard";
			case PEACOCK -> "Mrs. Peacock";
			case WHITE -> "Mrs. White";
			case PLUM -> "Prof. Plum";
			case SCARLET -> "Miss Scarlet";
			case COURTYARD -> "Courtyard";
			case GARAGE -> "Garage";
			case GAME_ROOM -> "Game Room";
			case BEDROOM -> "Bedroom";
			case BATHROOM -> "Bathroom";
			case OFFICE -> "Office";
			case KITCHEN -> "Kitchen";
			case DINING_ROOM -> "Dining Room";
			case LIVING_ROOM -> "Living Room";
		};
	}

	/**
	 * Gets the value of a card, given the string name of the card
	 * Partial names are accepted
	 * @param cardstr the string to get the value for
	 * @throws NoSuchElementException if a value could not be derived from cardstr
	 * @return the value represented by cardstr
	 */
	public static Card toCard(String cardstr) {
		cardstr = cardstr.toLowerCase();
		if (cardstr.contains("rope")) return ROPE;
		else if (cardstr.contains("candle")) return CANDLESTICK;
		else if (cardstr.contains("pipe")) return LEAD_PIPE;
		else if (cardstr.contains("pistol")) return PISTOL;
		else if (cardstr.contains("dagger")) return DAGGER;
		else if (cardstr.contains("wrench")) return WRENCH;
		else if (cardstr.contains("green")) return GREEN;
		else if (cardstr.contains("mustard")) return MUSTARD;
		else if (cardstr.contains("yellow")) return MUSTARD;
		else if (cardstr.contains("peacock")) return PEACOCK;
		else if (cardstr.contains("blue")) return PEACOCK;
		else if (cardstr.contains("white")) return WHITE;
		else if (cardstr.contains("plum")) return PLUM;
		else if (cardstr.contains("purple")) return PLUM;
		else if (cardstr.contains("scarlet")) return SCARLET;
		else if (cardstr.contains("red")) return SCARLET;
		else if (cardstr.contains("court")) return COURTYARD;
		else if (cardstr.contains("yard")) return COURTYARD;
		else if (cardstr.contains("garage")) return GARAGE;
		else if (cardstr.contains("game")) return GAME_ROOM;
		else if (cardstr.contains("bed")) return BEDROOM;
		else if (cardstr.contains("bath")) return BATHROOM;
		else if (cardstr.contains("office")) return OFFICE;
		else if (cardstr.contains("kitchen")) return KITCHEN;
		else if (cardstr.contains("dining")) return DINING_ROOM;
		else if (cardstr.contains("living")) return LIVING_ROOM;
		else throw new NoSuchElementException("Unknown card");
	}
}

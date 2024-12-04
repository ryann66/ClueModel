package org.example.model;

import java.util.NoSuchElementException;

/**
 * Immutable record-like class for representing cards
 * Non-abstracting
 */
public class Card {
	public static final int NUM_CARDS = 21;

	/**
	 * The type of card this is
	 */
	public enum Type {
		WEAPON, PERSON, LOCATION
	}

	/**
	 * The value of the card
	 */
	public enum Value {
		ROPE, CANDLESTICK, LEAD_PIPE, PISTOL, DAGGER, WRENCH,
		GREEN, MUSTARD, PEACOCK, WHITE, PLUM, SCARLET,
		COURTYARD, GARAGE, GAME_ROOM, BEDROOM, BATHROOM, OFFICE, KITCHEN, DINING_ROOM, LIVING_ROOM
	}

	/**
	 * Constructs a card from a string
	 * Partial names are accepted
	 * @param cardstr the string to make a card from
	 */
	public Card(String cardstr) {
		this(toValue(cardstr));
	}

	/**
	 * Constructs a card from the value enum
	 * @param cardvalue the value to assign the card
	 */
	public Card(Value cardvalue) {
		value = cardvalue;
		type = switch (cardvalue) {
			case WRENCH, LEAD_PIPE, ROPE, CANDLESTICK, PISTOL, DAGGER -> Type.WEAPON;
			case GREEN, MUSTARD, PEACOCK, WHITE, PLUM, SCARLET -> Type.PERSON;
			case COURTYARD, GARAGE, GAME_ROOM, BEDROOM, BATHROOM, OFFICE, KITCHEN, DINING_ROOM, LIVING_ROOM -> Type.LOCATION;
		};
	}

	public final Type type;
	public final Value value;
	
	public String toString() {
		return Card.toString(this.value);
	}

	/**
	 * Gets the value of a card, given the string name of the card
	 * Partial names are accepted
	 * @param cardstr the string to get the value for
	 * @throws NoSuchElementException if a value could not be derived from cardstr
	 * @return the value represented by cardstr
	 */
	public static Value toValue(String cardstr) {
		cardstr = cardstr.toLowerCase();
		if (cardstr.contains("rope")) return Value.ROPE;
		else if (cardstr.contains("candle")) return Value.CANDLESTICK;
		else if (cardstr.contains("pipe")) return Value.LEAD_PIPE;
		else if (cardstr.contains("pistol")) return Value.PISTOL;
		else if (cardstr.contains("dagger")) return Value.DAGGER;
		else if (cardstr.contains("wrench")) return Value.WRENCH;
		else if (cardstr.contains("green")) return Value.GREEN;
		else if (cardstr.contains("mustard")) return Value.MUSTARD;
		else if (cardstr.contains("yellow")) return Value.MUSTARD;
		else if (cardstr.contains("peacock")) return Value.PEACOCK;
		else if (cardstr.contains("blue")) return Value.PEACOCK;
		else if (cardstr.contains("white")) return Value.WHITE;
		else if (cardstr.contains("plum")) return Value.PLUM;
		else if (cardstr.contains("purple")) return Value.PLUM;
		else if (cardstr.contains("scarlet")) return Value.SCARLET;
		else if (cardstr.contains("red")) return Value.SCARLET;
		else if (cardstr.contains("court")) return Value.COURTYARD;
		else if (cardstr.contains("yard")) return Value.COURTYARD;
		else if (cardstr.contains("garage")) return Value.GARAGE;
		else if (cardstr.contains("game")) return Value.GAME_ROOM;
		else if (cardstr.contains("bed")) return Value.BEDROOM;
		else if (cardstr.contains("bath")) return Value.BATHROOM;
		else if (cardstr.contains("office")) return Value.OFFICE;
		else if (cardstr.contains("kitchen")) return Value.KITCHEN;
		else if (cardstr.contains("dining")) return Value.DINING_ROOM;
		else if (cardstr.contains("living")) return Value.LIVING_ROOM;
		else throw new NoSuchElementException("Unknown card");
	}
	
	public static String toString(Value value) {
		return switch (value) {
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
}

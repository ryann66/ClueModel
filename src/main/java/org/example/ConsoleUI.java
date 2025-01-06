package org.example;

import org.example.model.*;

import java.io.InputStream;
import java.sql.SQLOutput;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleUI {
	private final InputStream input;
	private final PlayerList players;
	private final Player self;

	private final Model model;

	public ConsoleUI(InputStream in) {
		input = in;
		Scanner cin = new Scanner(input);
		System.out.println("Enter the number of players:");
		int np = 0;
		do {
			try {
				np = Integer.parseInt(cin.nextLine());
			} catch (NumberFormatException ignored) { }
		} while (np < 3 || np > 6);
		Player[] pls = new Player[np];

		// calculate the number of cards each player should hold
		final int cardsPerPlayer = (Card.NUM_CARDS - 3) / np;
		final int cardsInPool = (Card.NUM_CARDS - 3) % np;

		self = new Player("self", cardsPerPlayer);
		pls[0] = self;

		System.out.println("Enter the names of players (excluding you), starting from your left");
		for (int i = 1; i < pls.length; i++) {
			pls[i] = new Player(cin.nextLine().trim(), cardsPerPlayer);
		}
		players = new PlayerList(pls);

		Card[] carr = new Card[cardsInPool];
		if (cardsInPool > 0) System.out.println("Enter the " + cardsInPool + " common cards you know:");
		for (int i = 0; i < cardsInPool; i++) {
			try {
				carr[i] = Card.toCard(cin.nextLine());
			} catch (NoSuchElementException nsee) {
				i--;
				System.out.println("Unparseable card, try again");
			}
		}

		Card[] charr = new Card[cardsPerPlayer];
		System.out.println("Enter the " + cardsPerPlayer + " cards in your hand:");
		for (int i = 0; i < cardsPerPlayer; i++) {
			try {
				charr[i] = Card.toCard(cin.nextLine());
			} catch (NoSuchElementException nsee) {
				i--;
				System.out.println("Unparseable card, try again");
			}
		}

		model = new BasicModel(players, self, carr, charr);
	}

	public void startUI() {
		Scanner in = new Scanner(input);
		System.out.print("$ ");
		System.out.flush();
		while (in.hasNextLine()) {
			try {
				Scanner line = new Scanner(in.nextLine());
				String cmd = line.next().toLowerCase();
				switch (cmd) {
					case "add" -> {
						// ADD asker card1 card2 card3 answerer?
						Player asker = players.get(line.next());
						Card[] cards = new Card[3];
						for (int i = 0; i < cards.length; i++)
							cards[i] = Card.toCard(line.next());
						Player answerer = null;
						if (line.hasNext()) answerer = players.get(line.next());
						model.addQuery(new Query(asker, answerer, cards, null));
						break;
					}
					case "ask" -> {
						// ASK card1 card2 card3 answerer? card?
						Card[] cards = new Card[3];
						for (int i = 0; i < cards.length; i++)
							cards[i] = Card.toCard((line.next()));
						Player answerer = null;
						Card answer = null;
						if (line.hasNext()) {
							answerer = players.get(line.next());
							answer = Card.toCard((line.next()));
						}
						model.addQuery(new Query(self, answerer, cards, answer));
						break;
					}
					case "get" -> {
						// GET simple?/full
						String mode = line.hasNext() ? line.next().toLowerCase() : "simple";
						if (mode.equals("simple")) {
							Map<Card, Boolean> card = model.getSimpleScorecard();
							System.out.println("Cards:");
							for (Card v : Card.values()) {
								StringBuilder sb = new StringBuilder(Card.MAX_CARD_STRING_LENGTH + 3);
								sb.append(v.toString());
								sb.append(':');
								while (sb.length() < Card.MAX_CARD_STRING_LENGTH + 1) sb.append(' ');
								sb.append(' ');
								sb.append(card.get(v) ? 'X' : ' ');
								System.out.println(sb);
							}
						} else if (mode.equals("full")) {
							Map<Player, Map<Card, Knowledge>> card = model.getFullScorecard();

							// collect all the player names in order
							int maxlen = 0;
							StringBuilder[] plns = new StringBuilder[players.getPlayerCount()];
							{
								int i = 0;
								for (Player p : players) {
									plns[i] = new StringBuilder(p.name());
									maxlen = Math.max(maxlen, plns[i].length());
									i++;
								}
							}
							// pad out player names
							for (StringBuilder str : plns) {
								while (str.length() < maxlen) str.insert(0, ' ');
							}

							// length of each line in the scorecard
							final int buflen = Card.MAX_CARD_STRING_LENGTH + 1 + 2 * plns.length;

							// print out player names
							for (int i = 0; i < maxlen; i++) {
								StringBuilder buf = new StringBuilder(buflen);
								while (buf.length() < Card.MAX_CARD_STRING_LENGTH + 1) buf.append(' ');
								for (StringBuilder str : plns) {
									buf.append(' ');
									buf.append(str.charAt(i));
								}
								System.out.println(buf);
							}

							// print out card rows
							for (Card c : Card.values()) {
								StringBuilder buf = new StringBuilder(buflen);
								buf.append(c.toString());
								buf.append(':');
								while (buf.length() < Card.MAX_CARD_STRING_LENGTH + 1) buf.append(' ');

								for (Player ply : players) {
									buf.append(' ');

									Knowledge entry = card.get(ply).get(c);
									buf.append(entry == null ? ' ' : switch (entry.t) {
										case HAS -> 'X';
										case KNOWN -> 'k';
										case NO_HAS -> 'n';
										case MIGHT_HAVE -> ' ';
									});
								}

								System.out.println(buf);
							}
						} else {
							throw new NoSuchElementException("Unknown asset");
						}
					}
					case "help" -> {
						// HELP
						System.out.println("-----COMMANDS-----");
						System.out.println("ADD asker card1 card2 card3 answerer?");
						System.out.println("ASK card1 card2 card3 answerer? card?");
						System.out.println("GET simple?/full");
					}
					case "exit" -> {
						return;
					}
					default ->
						// unknown command
							throw new NoSuchElementException("Unknown case");
				}

				if (line.hasNext()) System.out.println("Warning: Extra input detected");
			} catch (NoSuchElementException ignored) {
				System.out.println("Unparseable command");
			} catch (IllegalArgumentException iae) {
				System.out.println("Illegal command: " + iae.getMessage());
			} catch (IllegalStateException ise) {
				System.out.println("Impossible state: " + ise.getMessage());
			}
			System.out.print("$ ");
			System.out.flush();
		}
	}
}

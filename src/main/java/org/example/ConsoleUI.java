package org.example;

import org.example.model.*;

import java.io.InputStream;
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
		self = new Player("self");
		System.out.println("Enter the number of players:");
		int np = 0;
		do {
			np = Integer.parseInt(cin.nextLine());
		} while (np < 3 || np > 6);
		Player[] pls = new Player[np];
		pls[0] = self;

		System.out.println("Enter the names of players, starting from your left");
		for (int i = 1; i < pls.length; i++) {
			pls[i] = new Player(cin.nextLine());
		}
		players = new PlayerList(pls);

		System.out.println("Enter the number of cards you know");
		int nc = 0;
		do {
			nc = Integer.parseInt(cin.nextLine());
		} while (nc < 0 || nc > Card.NUM_CARDS);
		Card[] carr = new Card[nc];

		System.out.println("Enter the cards you know:");
		for (int i = 0; i < nc; i++) {
			try {
				carr[i] = new Card(cin.nextLine());
			} catch (NoSuchElementException nsee) {
				i--;
				System.out.println("Unparseable card, try again");
			}
		}

		model = new BasicModel(players, self, carr);
	}

	public void startUI() {
		Scanner in = new Scanner(input);
		while (in.hasNextLine()) {
			try {
				Scanner line = new Scanner(in.nextLine());
				String cmd = line.next().toLowerCase();
				if (cmd.equals("add")) {
					// ADD asker card1 card2 card3 answerer?
					Player asker = players.get(line.next());
					Card[] cards = new Card[3];
					for (int i = 0; i < cards.length; i++)
						cards[i] = new Card(line.next());
					Player answerer = null;
					if (line.hasNext()) answerer = players.get(line.next());
					model.addQuery(new Query(asker, answerer, cards, null));
				} else if (cmd.equals("ask")) {
					// ASK card1 card2 card3 answerer? card?
					Card[] cards = new Card[3];
					for (int i = 0; i < cards.length; i++)
						cards[i] = new Card(line.next());
					Player answerer = null;
					Card answer = null;
					if (line.hasNext()) {
						answerer = players.get(line.next());
						answer = new Card(line.next());
					}
					model.addQuery(new Query(self, answerer, cards, answer));
				} else if (cmd.equals("get")) {
					// GET
					// todo
				} else if (cmd.equals("help")) {
					// HELP
					System.out.println("-----COMMANDS-----");
					System.out.println("ADD asker card1 card2 card3 answerer?");
					System.out.println("ASK card1 card2 card3 answerer? card?");
					System.out.println("GET");
				} else {
					// unknown command
					throw new NoSuchElementException("Unknown case");
				}

				if (line.hasNext()) System.out.println("Warning: Extra input detected");
			} catch (NoSuchElementException nsee) {
				System.out.println("Unparseable command");
			}
		}
	}
}

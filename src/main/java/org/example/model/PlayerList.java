package org.example.model;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class for iterating over ranges of players
 */
public class PlayerList {
	private Player[] players;

	/**
	 * Constructs a player list from the given array of players.
	 * Players are considered to be in order such that index 1 is the first
	 * player to answer index 0's questions.  Additionally, the array is circular
	 * so that the last player has his questions first asked by the player at index 0
	 * @param players ordered, circular array of players
	 */
	public PlayerList(Player[] players) {
		if (players == null || players.length <= 2) throw new IllegalArgumentException("Illegal players");
		this.players = players;
	}

	/**
	 * @return the number of players in the game
	 */
	public int getPlayerCount() {
		return players.length;
	}

	/**
	 * Gets an iterator that iterates once through each player
	 * @return an iterator of players
	 */
	public Iterator<Player> iterator() {
		return new PlayerIterator(0, players.length);
	}

	/**
	 * Gets an iterator that iterates through the players
	 * that are strictly between the two players
	 * Both begin and end are exclusive
	 * This may return an empty iterator
	 * @param begin the exclusive player to start iteration at
	 * @param end the exclusive player to end iteration at
	 * @return an iterator through the players between the start and end players
	 */
	public Iterator<Player> iterator(Player begin, Player end) {
		int si = -1, ei = -1;
		for (int i = 0; i < players.length; i++) {
			if (players[i].equals(begin)) si = i;
			else if (players[i].equals(end)) ei = i;
		}
		if (si == -1 || ei == -1) throw new IllegalArgumentException("Player not found");
		return new PlayerIterator(si + 1, ei);
	}

	/**
	 * Iterator for a circular subarray of players
	 */
	private class PlayerIterator implements Iterator<Player> {
		// Next index to return
		int idx;
		// Last index, exclusive
		// should be kept low (i.e. less than players.length)
		int endidx;

		/**
		 * Constructs a circular iterator over the range of indicies
		 * @param startidx the index to start at
		 * @param endidx the index to end at, exclusive
		 */
		private PlayerIterator(int startidx, int endidx) {
			this.idx = startidx % players.length;
			this.endidx = endidx % players.length;
		}

		@Override
		public boolean hasNext() {
			return idx != endidx;
		}

		@Override
		public Player next() {
			if (idx == endidx) throw new NoSuchElementException("Iteration finished");
			Player ret = players[idx];
			idx = (idx + 1) % players.length;
			return ret;
		}
	}
}

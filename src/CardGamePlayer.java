//import java.util.Scanner;
//import java.util.StringTokenizer;

/**
 * This class is used to represent a player in general card games.
 * 
 * @author Lee
 *
 */
public class CardGamePlayer {
	private static int playerId = 0;

	private int score = 0;
	private String name = "";
	private CardList cardsInHand = new CardList();

	/**
	 * Creates and returns an instance of the Player class.
	 */
	public CardGamePlayer() {
		this.name = "Player " + playerId;
		playerId++;
	}

	/**
	 * Creates and returns an instance of the Player class.
	 * 
	 * @param name
	 *            the name of the player
	 */
	public CardGamePlayer(String name) {
		this.name = name;
	}

	/**
	 * Returns the name of this player.
	 * 
	 * @return the name of this player
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name of this player.
	 * 
	 * @param name
	 *            the name of this player
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the score of this player.
	 * 
	 * @return the score of this player
	 */
	public int getScore() {
		return this.score;
	}

	/**
	 * Sets the score of this player.
	 * 
	 * @param score
	 *            the score of this player
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Adds the specified score to this player.
	 * 
	 * @param score
	 *            the specified score to be added to this player
	 */
	public void addScore(int score) {
		this.score += score;
	}

	/**
	 * Adds the specified card to this player.
	 * 
	 * @param card
	 *            the specified card to be added to this player
	 */
	public void addCard(Card card) {
		if (card != null) {
			cardsInHand.addCard(card);
		}
	}

	/**
	 * Removes the list of cards from this player, if they are held by this
	 * player.
	 * 
	 * @param cards
	 *            the list of cards to be removed from this player
	 */
	public void removeCards(CardList cards) {
		for (int i = 0; i < cards.size(); i++) {
			cardsInHand.removeCard(cards.getCard(i));
		}
	}

	/**
	 * Removes all cards from this player.
	 */
	public void removeAllCards() {
		cardsInHand = new CardList();
	}

	/**
	 * Returns the number of cards held by this player.
	 * 
	 * @return the number of cards held by this player
	 */
	public int getNumOfCards() {
		return cardsInHand.size();
	}

	/**
	 * Sorts the list of cards held by this player.
	 */
	public void sortCardsInHand() {
		cardsInHand.sort();
	}

	/**
	 * Returns the list of cards held by this player.
	 * 
	 * @return the list of cards held by this player
	 */
	public CardList getCardsInHand() {
		return cardsInHand;
	}

//	/**
//	 * Returns the list of cards played by this player. Player can select the
//	 * cards to be played by specifying the indices (space-separated) of the
//	 * cards via the console.
//	 * 
//	 * @param lastHandOnTable
//	 *            the last hand of cards placed on the table
//	 * @return the list of cards player by this player
//	 */
//	public CardList play(CardList lastHandOnTable) {
//		CardList cards = new CardList();
//
//		Scanner scanner = new Scanner(System.in);
//		System.out.print(getName() + "'s turn: ");
//
//		String input = scanner.nextLine();
//
//		StringTokenizer st = new StringTokenizer(input);
//		while (st.hasMoreTokens()) {
//			try {
//				int idx = Integer.parseInt(st.nextToken());
//				if (idx >= 0 && idx < cardsInHand.size()) {
//					cards.addCard(cardsInHand.getCard(idx));
//				}
//			} catch (Exception e) {
//				// e.printStackTrace();
//			}
//		}
//
//		if (cards.isEmpty()) {
//			return null;
//		} else {
//			return cards;
//		}
//	}

	/**
	 * Returns the list of cards played by this player.
	 * 
	 * @param cardIdx
	 *            the list of the indices of the cards
	 * @return the list of cards played by this player
	 */
	public CardList play(int[] cardIdx) {
		if (cardIdx == null) {
			return null;
		}

		CardList cards = new CardList();
		for (int idx : cardIdx) {
			if (idx >= 0 && idx < cardsInHand.size()) {
				cards.addCard(cardsInHand.getCard(idx));
			}
		}

		if (cards.isEmpty()) {
			return null;
		} else {
			return cards;
		}
	}
}

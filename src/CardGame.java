import java.util.ArrayList;

/**
 * An interface for a general card game.
 * 
 * @author Lee
 *
 */
public interface CardGame {
	/**
	 * Returns the number of players in this card game.
	 * 
	 * @return the number of players in this card game
	 */
	public int getNumOfPlayers();

	/**
	 * Returns the deck of cards being used in this card game.
	 * 
	 * @return the deck of cards being used in this card game
	 */
	public Deck getDeck();

	/**
	 * Returns the list of players in this card game.
	 * 
	 * @return the list of players in this card game
	 */
	public ArrayList<CardGamePlayer> getPlayerList();

	/**
	 * Returns the list of hands played on the table.
	 * @return the list of hands played on the table
	 */
	public ArrayList<Hand> getHandsOnTable();

	/**
	 * Returns the index of the current player.
	 * @return the index of the current player
	 */
	public int getCurrentIdx();

	/**
	 * Starts the card game.
	 * 
	 * @param deck
	 *            the deck of cards to be used in this game
	 */
	public void start(Deck deck);

	/**
	 * Makes a move by the specified player.
	 * 
	 * @param playerID
	 *            the playerID (index) of the specified player who makes the move
	 * @param cardIdx
	 *            the list of the indices of the cards played by the specified
	 *            player
	 */
	public void makeMove(int playerID, int[] cardIdx);

	/**
	 * Checks the move made by the specified player.
	 * 
	 * @param playerID
	 *            the playerID (index) of the specified player who makes the move
	 * @param cardIdx
	 *            the list of the indices of the cards played by the specified
	 *            player
	 */
	public void checkMove(int playerID, int[] cardIdx);
	
	/**
	 * Checks if a game is in progress.
	 * 
	 * @return true if a game is in progress
	 */
	public boolean isPlaying();
	/**
	 * Checks for end of game.
	 * @return true if the game ends; false otherwise
	 */
	public boolean endOfGame();
}

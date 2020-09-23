/**
 * An interface for a general card game table (GUI)
 * 
 * @author Lee
 * 
 */
public interface CardGameTable {
	/**
	 * Sets the index of the active player (i.e., the current player).
	 * 
	 * @param activePlayer
	 *            an int value representing the index of the active player
	 */
	public void setActivePlayer(int activePlayer);

	/**
	 * Sets the index of the active selection (i.e., the index of the player
	 * from whom the current player can pick the cards to make a move).
	 * 
	 * @param activeSelection
	 *            an int value representing the index of the active selection.
	 */
	public void setActiveSelection(int activeSelection);

	/**
	 * Returns an array of indices of the cards selected.
	 * 
	 * @return an array of indices of the cards selected
	 */
	public int[] getSelected();

	/**
	 * Resets the list of selected cards to an empty list.
	 */
	public void resetSelected();

	/**
	 * Repaints the GUI.
	 */
	public void repaint();

	/**
	 * Prints the specified string to the text area of the card game table.
	 * 
	 * @param msg
	 *            the string to be printed to the text area of the card game
	 *            table
	 */
	public void print(String msg);

	/**
	 * Prints the specified string, followed by a newline, to the text area of
	 * the card game table.
	 * 
	 * @param msg
	 *            the string to be printed to the text area of the card game
	 *            table
	 */
	public void println(String msg);

	/**
	 * Clears the text area of the card game table.
	 */
	public void clearTextArea();

	/**
	 * Resets the GUI.
	 */
	public void reset();

	/**
	 * Enables user interactions.
	 */
	public void enable();

	/**
	 * Disables user interactions.
	 */
	public void disable();
}

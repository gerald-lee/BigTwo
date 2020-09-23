
public class BigTwoHand extends Hand
{
	/**
	 * a constructor for building a hand with the specified player and list of cards
	 * @param player	specified player
	 * @param cards		a list of cards
	 */
	public BigTwoHand(CardGamePlayer player, CardList cards)
	{
		super(player, cards);
	}
	
	/**
	 * constructor for the different card combinations
	 * @param cards	a list of cards
	 */
	public BigTwoHand(CardList cards) 
	{
		super(cards);
	}

	public boolean isValid() {
		return false;
	}

	public String getType() {
		return null;
	}
}

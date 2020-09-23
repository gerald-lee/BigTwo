/**
 * This hand consists of only one single card.
 * The only card in a a single is referred to as the top card of this single.
 * A single with a higher rank beats a single with a lower rank.a
 * For singles with the same rank, the one with a higher suit beats the one with a lower suit.
 * @author Lee
 *
 */
public class Single extends Hand
{
	/**
	 * constructor class
	 * @param cards
	 */
	public Single(CardList cards)
	{
		super(cards);
	}
	
	/**
	 * a method that check if the hand contains a single card
	 */
	public boolean isValid()
	{
		return (this.size() == 1);
	}
	
	/**
	 * a method that check if this hand can beat the previous hand
	 * @param tableHand
	 * @return
	 */
	public boolean beats(Hand handOnTable)
	{
		if(handOnTable == null)
			return true;
		if(handOnTable.getType() != this.getType())
			return false;
		return (this.getCard(0).compareTo(handOnTable.getCard(0)) == 1);
	}
	
	/**
	 * a method for retrieving the top card of this hand
	 */
	public Card getTopCard()
	{
		return this.getCard(0);
	}
	
	/**
	 *  a method that return a string of the name of this hand
	 */
	public String getType()
	{
		return " {Single} ";
	}
}

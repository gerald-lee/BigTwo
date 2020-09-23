/**
 * This hand consists of two cards with the same rank. 
 * The card with a higher suit in a pair is referred to as the top card of this pair. 
 * A pair with a higher rank beats a pair with a lower rank. 
 * For pairs with the same rank, 
 * the one containing the highest suit beats the other.
 * @author Lee
 * 
 */
public class Pair extends Hand
{
	/**
	 * constructor class
	 * @param cards
	 */
	public Pair(CardList cards)
	{
		super(cards);
	}
	
	/**
	 * a method that checks if the hand contains two cards of same rank
	 */
	public boolean isValid()
	{
		if(this.size() == 2)
		{
			if(this.getCard(0).getRank()== this.getCard(1).getRank())
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * a method that check if this hand can beat the previous hand
	 * @param handOnTable
	 * @return
	 */
	public boolean beats(Hand handOnTable)
	{
		if(handOnTable == null)
			return true;
		if(handOnTable.getType() != this.getType())
			return false;
		return (this.getCard(1).compareTo(handOnTable.getCard(handOnTable.size()-1)) == 1);
	}

	/**
	 * a method for retrieving the top card of this hand
	 */
	public Card getTopCard()
	{
		return this.getCard(1);
	}
	/**
	 *  a method that return a string of the name of this hand
	 */
	public String getType()
	{
		return " {Pair} ";
	}
}

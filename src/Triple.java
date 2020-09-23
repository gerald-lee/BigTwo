/**
 * This hand consists of three cards with the same rank. 
 * The card with the highest suit in a triple is referred to as the top card of this triple. 
 * A triple with a higher rank beats a triple with a lower rank.
 * @author Lee
 *
 */
public class Triple  extends Hand
{
	/**
	 * constructor class
	 * @param cards
	 */
	public Triple(CardList cards)
	{
		super(cards);
	}
	
	/**
	 * a method that checks if the hand contain three cards of the same rank
	 */
	public boolean isValid()
	{
		if(this.size() == 3)
		{
			if(this.getCard(0).getRank() == this.getCard(1).getRank())
			{
				if(this.getCard(1).getRank() == this.getCard(2).getRank())
				{
					return true;
				}
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
		return (this.getCard(2).compareTo(handOnTable.getCard(2)) == 1);
	}

	/**
	 * a method for retrieving the top card of this hand
	 */
	public Card getTopCard()
	{
		return this.getCard(2);
	}
	
	/**
	 *  a method that return a string of the name of this hand
	 */
	public String getType()
	{

		return " {Triple} ";
	}
}
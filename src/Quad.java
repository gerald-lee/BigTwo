/**
 * This hand consists of five cards, with four having the same rank. 
 * The card in the quadruplet with the highest suit in a quad is referred to as the top card of this quad. 
 * A quad always beats any straight, flush or full house. 
 * A quad having a top card with a higher rank beats a quad having a top card with a lower rank.
 * @author Lee
 *
 */
public class Quad extends Hand
{
	/**
	 * constructor class
	 * @param cards
	 */
	public Quad(CardList cards)
	{
		super(cards);
	}
	
	/**
	 *  a method that check if this five cards, 
	 * with four having the same rank.
	 */
	public boolean isValid()
	{
		if(this.size() == 5)
		{
			if(this.getCard(1).getRank() != this.getCard(2).getRank())
			{
				return false;
			}
			if(this.getCard(2).getRank() != this.getCard(3).getRank())
			{
				return false;
			}
			if(this.getCard(0).getRank() == this.getCard(1).getRank())
			{
				return true;
			}
			if(this.getCard(3).getRank() == this.getCard(4).getRank())
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
		if(handOnTable.getType() == " {Straight} ")
			return true;
		if(handOnTable.getType() == " {Flush} ")
			return true;
		if(handOnTable.getType() == " {FullHouse} ")
			return true;
		if(handOnTable.getType() != this.getType())
			return false;
		//check rank by comparing the middle card, which must be inside the quad 
		return (this.getCard(2).compareTo(handOnTable.getCard(2)) == 1);
	}
	
	/**
	 * a method for retrieving the top card of this hand
	 */
	public Card getTopCard()
	{
		if(this.getCard(3).rank == this.getCard(4).rank)
		{
			return (this.getCard(4));
		}
		else
		{
			return (this.getCard(3));
		}
	}
	
	/**
	 *  a method that return a string of the name of this hand
	 */
	public String getType()
	{
		return " {Quad} ";

	}
}
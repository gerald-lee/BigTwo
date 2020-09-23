/**
 * This hand consists of five cards with consecutive ranks and the same suit.
 * For the sake of simplicity, 2 and A can only form a straight with K but not with 3. 
 * The card with the highest rank in a straight flush is referred to as the top card of this straight flush. 
 * A straight flush always beats any straight, flush, full house or quad. 
 * A straight flush having a top card with a higher rank beats a straight flush having a top card with a lower rank.
 *  For straight flushes having top cards with the same rank, 
 *  the one having a top card with a higher suit beats one having a top card with a lower suit.
 * @author Lee 
 *
 */
public class StraightFlush extends Hand
{
	/**
	 * constructor class
	 * @param cards
	 */
	public StraightFlush(CardList cards)
	{
		super(cards);
	}
	
	/**
	 *  a method that check if this five cards, 
	 * with five cards with consecutive ranks and the same suit.
	 */
	public boolean isValid()
	{
		if(this.size() == 5)
		{
			//check for same suit one by one
			for(int i = 0; i < 4; i++)
			{
				if(this.getCard(i).getSuit() != this.getCard(i+1).getSuit())
				{	
					//if any of a card has a different suit, return false
					return false;
				}
			}
			//check for consecutive ranks suit one by one
			for(int i = 0; i < 4; i++)
			{
				if(this.getCard(i).getRank() == 1)	//if it is 2
				{
					return false;
				}
				if(this.getCard(i).getRank() == 12)	//if it is K, the next card has to be A
				{
					if(this.getCard(i+1).getRank() == 0)
					{
						continue;
					}
				}
				if(this.getCard(i+1).getRank() - this.getCard(i).getRank() != 1)
				{
					//if any of a card does not have a consecutive rank, 
					//return false directly
					return false;
				}
			}
		}
		//return true only if it passes all tests
		return true;
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
		if(handOnTable.getType() == " {Quad} ")
			return true;
		if(handOnTable.getType() != this.getType())
			return false;
		
		//case 1: higher rank
		if(this.getCard(4).compareTo(handOnTable.getCard(4)) == 1)
		{
			return true;
		}
		//case 2: same rank; higher suit
		else if (this.getCard(4).compareTo(handOnTable.getCard(4)) == 0)
		{
			if(this.getCard(4).getSuit() > handOnTable.getCard(4).getSuit())
			{
				return true;
			}
		}
		//other case:
		return false;
	}

	/**
	 * a method for retrieving the top card of this hand
	 */
	public Card getTopCard()
	{
		return this.getCard(4);
	}
	
	/**
	 *  a method that return a string of the name of this hand
	 */
	public String getType()
	{

		return " {StraightFlush} ";

	}
}
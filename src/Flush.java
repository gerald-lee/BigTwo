/**
 *  This hand consists of five cards with the same suit. 
 *  The card with the highest rank in a flush is referred to as the top card of this flush.
 *   A flush always beats any straight. 
 *   A flush with a higher suit beats a flush with a lower suit. 
 *   For flushes with the same suit, 
 *   the one having a top card with a higher rank beats the one having a top card with a lower rank
 * @author Lee
 *
 */
public class Flush extends Hand
{
	/**
	 * constructor class
	 * @param cards
	 */
	public Flush(CardList cards)
	{
		super(cards);
	}
	
	/**
	 *  method the checks if this hand consists of five cards with the same suit. 
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
		}
		return true;
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
		if(handOnTable.getType() == " {Straight} ")
			return true;
		if(handOnTable.getType() != this.getType())
			return false;
		
		//case 1: higher suit
		if(this.getCard(4).getSuit() > handOnTable.getCard(4).getSuit())
		{
			return true;
		}
		//case 2: same suit; higher rank
		else if (this.getCard(4).getSuit() == handOnTable.getCard(4).getSuit())
		{
			if(this.getCard(4).compareTo(handOnTable.getCard(4)) == 1)
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
		return " {Flush} ";

	}
}

/**
 * This hand consists of five cards with consecutive ranks. 
 * For the sake of simplicity, 2 and A can only form a straight with K but not with 3. 
 * The card with the highest rank in a straight is referred to as the top card of this straight. 
 * A straight having a top card with a higher rank beats a straight having a top card with a lower rank. 
 * For straights having top cards with the same rank, 
 * the one having a top card with a higher suit beats the one having a top card with a lower suit. 
 * @author Lee
 *
 */
public class Straight  extends Hand
{
	/**
	 * constructor class
	 * @param cards
	 */
	public Straight(CardList cards)
	{
		super(cards);
	}
	
	/**
	 * a method that checks if the hand consists of five cards with consecutive ranks. 
	 */
	public boolean isValid()
	{
		if(this.size() == 5)
		{
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
		return true;
	}
	
	/**
	 * a method that check if this hand can beat the previous hand
	 * @param tableHand
	 * @return
	 */
	public boolean beats(Hand tableHand)
	{
		if(tableHand == null)
			return true;
		if(tableHand.getType() != this.getType())
			return false;
		
		//case 1: higher rank
		if(this.getCard(4).compareTo(tableHand.getCard(4)) == 1)
		{
			return true;
		}
		//case 2: same rank; higher suit
		else if (this.getCard(4).compareTo(tableHand.getCard(4)) == 0)
		{
			if(this.getCard(4).getSuit() > tableHand.getCard(4).getSuit())
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

		return " {Straight} ";
	}
}
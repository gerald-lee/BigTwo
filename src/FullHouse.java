/**
 * This hand consists of five cards, 
 * with two having the same rank and three having another same rank.
 *  The card in the triplet with the highest suit in a full house is referred to as the top card of this full house. 
 *  A full house always beats any straight or flush.
 *   A full house having a top card with a higher rank beats a full house having a top card with a lower rank
 * @author Lee
 *
 */
public class FullHouse extends Hand
{
	/**
	 * constructor class
	 * @param cards
	 */
	public FullHouse(CardList cards)
	{
		super(cards);
	}
	
	/**
	 *  a method that check if this five cards, 
	 * with two having the same rank and three having another same rank.
	 */
	public boolean isValid()
	{
		if(this.size() == 5)
		{
			//for a sorted FullHouse, the first two and last two cards must be the same
			if(this.getCard(0).getRank() == this.getCard(1).getRank())
			{
				if(this.getCard(3).getRank() == this.getCard(4).getRank())
				{
					//the middle card is equal to either of the pairs
					if(this.getCard(2).getRank() == this.getCard(1).getRank() 
							|| this.getCard(2).getRank() == this.getCard(3).getRank())
					{
						return true;
					}
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
		if(handOnTable.getType() == " {Straight} ")
			return true;
		if(handOnTable.getType() == " {Flush} ")
			return true;
		if(handOnTable.getType() != this.getType())
			return false;
		
		//check rank by comparing the middle card, which must be inside the triple 
		return (this.getCard(2).compareTo(handOnTable.getCard(2)) == 1);
	}

	/**
	 * a method for retrieving the top card of this hand
	 */
	public Card getTopCard()
	{
		if(this.getCard(2).rank == this.getCard(1).rank)
		{
			return (this.getCard(2));
		}
		else
		{
			return (this.getCard(4));
		}
	}
	
	/**
	 *  a method that return a string of the name of this hand
	 */
	public String getType()
	{

		return " {FullHouse} ";
	}
}

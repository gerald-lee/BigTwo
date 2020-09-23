/**
 * The Hand class is a sub-class of the CardList class, 
 * and is used to model a hand of cards. 
 * It has a private instance variable for storing the player who plays this hand. 
 * It also has methods for getting the player of this hand, 
 * checking if it is a valid hand, getting the type of this hand, 
 * getting the top card of this hand, 
 * and checking if it beats a specified hand. 
 * @author Lee
 */
public abstract class Hand extends CardList
{
	/**
	 * a constructor for building a hand with the specified player and list of cards
	 * @param player	specified player
	 * @param cards		a list of cards
	 */
	public Hand(CardGamePlayer player, CardList cards)
	{
		for(int i = 0; i < cards.size(); i++)
		{
			player.addCard(cards.getCard(i));
		}
		
		this.player = player;
	}
	
	/**
	 * constructor for the different card combinations
	 * @param cards	a list of cards
	 */
	public Hand(CardList cards) 
	{
		if(cards == null)
			return;
		for(int i = 0; i < cards.size(); i++)
		{
			this.addCard(cards.getCard(i));
		}
	}
	
	/**
	 * the player who plays this hand
	 */
	private CardGamePlayer player;
	
	/**
	 * a method for retrieving the player of this hand
	 * @return
	 */
	public CardGamePlayer getPlayer()
	{
		return this.player;
	}
	
	/**
	 * a method for retrieving the top card of this hand
	 */
	public Card getTopCard()
	{
		return this.player.getCardsInHand().getCard(this.player.getCardsInHand().size()-1);
	}
	
	/**
	 * abstract method
	 * a method for checking if this is a valid hand
	 */
	public abstract boolean isValid();
	
	/**
	 * abstract method
	 * a method for returning a string specifying the type of this hand
	 */
	public abstract String getType();
	
	/**
	 * checks if one hand can beat the other
	 */
	public boolean beats(Hand handOnTable)
	{
		return false;
	}
}

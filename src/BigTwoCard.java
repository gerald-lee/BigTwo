/**
 * The BigTwoCard class is a sub-class of the Card class, 
 * and is used to model a card used in a Big Two card game. 
 * It should override the compareTo() method it inherited from the Card class to
 * reflect the ordering of cards used in a Big Two card game
 * @author Lee
 */
public class BigTwoCard extends Card
{
	/**
	 * a constructor for building a card with the specified suit and rank. 
	 * suit is an integer between 0 and 3, and rank is an integer between 0 and 12.
	 * @param suit
	 * @param rank
	 */
	public BigTwoCard(int suit, int rank)
	{
		super(suit, rank);
	}
	
	/**
	 * a method for comparing this card with the specified card for order. 
	 * Returns a negative integer, zero, or a positive integer 
	 * as this card is less than, equal to, or greater than the specified card.
	 */
	public int compareTo(Card card)
	{
		//'2' is biggest, check case for '2'
		if(this.rank == 1)
		{
			if(card.rank != 1){
				return 1;
			} else if(this.suit > card.suit) {
				return 1;
			} else if (this.suit < card.suit) {
				return -1;
			} else {
				return 0;
			}
		}
		
		//check case for 'A'
		else if (this.rank == 0)
		{
			if(card.rank == 1){
				return -1;	//A<2
			}else if(card.rank != 1){
				return 1; //A>cards other than 2
			}else if(this.suit > card.suit) {
				return 1;
			} else if (this.suit < card.suit) {
				return -1;
			} else {
				return 0;
			}
		}
		
		else if(card.rank == 1 || card.rank == 0)
		{
			return -1;
		}
			
		//check for normal cases
		else if (this.rank > card.rank) 
		{
			return 1;
		} else if (this.rank < card.rank) {
			return -1;
		} else if (this.suit > card.suit) {
			return 1;
		} else if (this.suit < card.suit) {
			return -1;
		} else {
			return 0;
		}
	}
}

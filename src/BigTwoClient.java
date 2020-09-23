import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;
/**
 * The BigTwoClient class implements the CardGmae interface and Network interface.
 * It is used to model a Big Two card game that supports 4 players over the internet.
 * @author Lee
 *
 */
public class BigTwoClient implements CardGame, NetworkGame
{

	 //a integer specifying the number of players.
	private int numOfPlayer;
	
	//a deck of cards
	private Deck deck;

	//a list of players.
	private ArrayList<CardGamePlayer> playerList;
	
	// a list of hands played on the table
	private ArrayList<Hand> handsOnTable;
	
	//an integer specifying the name of the local player
	private int playerID;
	
	//a string specifying the name of the local player.
	private String playerName;
	
	//a string specifying the IP address of the game server
	private String serverIP;
	
	//an integer specifying the TCP port of the game server
	private int serverPort;
	
	//a socket connection to the game server.
	private Socket sock;
	
	//an ObjectOutputStream for sending messages to the server.
	private ObjectOutputStream oos;
	
	//an integer specifying the index of the current player.
	private int currentIdx;
	
	//a Big Two table which builds the GUI for the game and handles all user actions.
	private BigTwoTable table;
	
	//self-introduced variables
	//an integer specifying the index of the player who played the last hand.
	private int previousIdx;
	
	// a variable containing a list of cards from user input
	private Hand currentList;
	
	//a variable containing a list of cards from user input with its type checked
	//null if it is invalid
	private Hand playedHand;
	
	//store the first card of the hand
	//used for checking diamond three
	private Card firstCard;
	
	//for receiving messages
	private ObjectInputStream ois;
	
	//store the name entered by the user
	private String name;
	
	//whether the game is in progress
	private boolean isPlaying;
	
	//thread for receiving message
	Thread serverHandlerThread;
	
	//main method
	public static void main(String[] args)
	{
		new BigTwoClient();
	}
	
	/**
	 * a constructor for creating a Big Two client.
	 * (i) create 4 players and add them to the list of players
	 * (ii) create a Big Two table which builds the GUI for the game and handles user actions
	 * (iii) make a connection to the game server by calling the makeConnection() method
	 */
	public BigTwoClient()
	{
		//create players
		numOfPlayer = 4;	//there are always four players in a game
		playerList = new ArrayList<CardGamePlayer>();
		//add null players into the list
		for(int i = 0; i < numOfPlayer ; i++)
		{
			playerList.add(new CardGamePlayer(null));
		}
		
		//show an input dialog box for the user to enter his/her name
		name = JOptionPane.showInputDialog("Please enter your name");
		
		//create table
		table = new BigTwoTable(this);
		
		//make connection
		this.makeConnection();
				
	}
	
	@Override
	public int getPlayerID() {
		return this.playerID;
	}

	@Override
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	@Override
	public String getPlayerName() {
		return this.playerName;
	}

	@Override
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	@Override
	public String getServerIP() {
		return this.serverIP;
	}

	@Override
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	@Override
	public int getServerPort() {
		return this.serverPort;
	}

	@Override
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	@Override
	public void makeConnection() 
	{
		//make a socket connection
		try {
			sock = new Socket("127.0.0.1",2396);
			
			//for sending messages
			oos = new ObjectOutputStream(sock.getOutputStream());
			
			//send message of type JOIN to game server
			this.sendMessage(new CardGameMessage(CardGameMessage.JOIN, -1, this.name));
			
			//send message of type READY to game server
			this.sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
			
			//for receiving messages
			ois = new ObjectInputStream(sock.getInputStream());
			//thread to read messages
			serverHandlerThread = new Thread(new ServerHandler());
			serverHandlerThread.start();
		} catch (Exception e) {
			e.printStackTrace();
			table.println("Cannot connect to server");
		}
	}

	@Override
	public void parseMessage(GameMessage message) {
		// parses the message based on it type
		switch (message.getType()) {
			case CardGameMessage.JOIN:
				//add player's own name to the player list
				this.playerList.get(message.getPlayerID()).setName((String)message.getData());
				break;
			case CardGameMessage.PLAYER_LIST:
				//get player list
				this.setPlayerID(message.getPlayerID());
				String name[] = (String[])message.getData();
				for(int i = 0; i < name.length; i++)
				{
					this.playerList.get(i).setName(name[i]);
				}
				break;
			case CardGameMessage.FULL:
				//the server is full
				table.println("The server is full. you cannot join the game");
				try {
					sock.close();
					serverHandlerThread = null;
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case CardGameMessage.QUIT:
//!IP address and port(?
				this.playerList.get(message.getPlayerID()).setName(null);
				isPlaying = false;
				//if game in progress
				if(!this.endOfGame())
				{
					//stop the game
					table.disable();
					//send READY message to the server
					this.sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
				}
			case CardGameMessage.READY:
				// shows that the specified player is ready for a new game
				table.println("Player "+this.playerList.get(message.getPlayerID()).getName()+" is ready for the game");
				break;
			case CardGameMessage.START:
				//start the game
				isPlaying = true;
				table.enable();
				table.clearTextArea();
				table.println("All players are ready. Game starts.");
				this.start((BigTwoDeck)message.getData());
				break;
			case CardGameMessage.MOVE:
				//check the move
				this.checkMove(message.getPlayerID(), (int[])message.getData());
				break;
			case CardGameMessage.MSG:
				//print message
				table.printlnChat((String)message.getData());
				break;
			default:
				// invalid message
				table.println("Receive wrong message type: " + message.getType());
				break;
		}
	}

	@Override
	public void sendMessage(GameMessage message) {
		try {
			oos.writeObject(message);
			oos.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public int getNumOfPlayers() {
		return this.numOfPlayer;
	}

	@Override
	public Deck getDeck() {
		return this.deck;
	}

	@Override
	public ArrayList<CardGamePlayer> getPlayerList() {
		return this.playerList;
	}

	@Override
	public ArrayList<Hand> getHandsOnTable() {
		return this.handsOnTable;
	}

	@Override
	public int getCurrentIdx() {
		return this.currentIdx;
	}

	@Override
	public void start(Deck deck) 
	{
		CardList listForHand;	//temp var to store the hand
		for(int i = 0; i < numOfPlayer; i++)
		{
			listForHand = new CardList();
			for(int j = 0; j < 13; j++)
			{
				listForHand.addCard(deck.getCard(i*13+j));
			}
			//distribute cards to players, 13 cards for each player
			playerList.get(i).removeAllCards();
			new BigTwoHand(playerList.get(i), listForHand);
			playerList.get(i).sortCardsInHand();
		}
		
		//create a new array for handsOnTable 
		handsOnTable = new ArrayList<Hand>();
		handsOnTable.add(null);
		
		//find out starting player
		for(int i = 0; i < numOfPlayer; i++)
		{
			Card firstCard = playerList.get(i).getCardsInHand().getCard(0);
			//if this hand has diamond 3, set it to currentIndex and previousIndex
			if(firstCard.getRank() == 2 && firstCard.getSuit() == 0)
			{
				currentIdx = i;
				previousIdx = i;
				table.setActivePlayer(this.playerID);
				table.setActiveSelection(this.playerID);
				table.setPreviousIdx(currentIdx);
			}
		}
		
		table.println(playerList.get(this.currentIdx).getName() + "'s turn: ");

		table.repaint();
	}

	@Override
	public void makeMove(int playerID, int[] cardIdx) {
		
		//check for special case "not this player's turn"
		if(playerID != this.currentIdx)
		{
			table.println("This is not your turn  <== Not a legal move!!!");
			return;
		}
		
		//check for special case "no cards selected"
		if(cardIdx == null)
		{
			table.println("No cards were selected  <== Not a legal move!!!");
			return;
		}
		
		//check for special case "Pass"
		if(cardIdx[0] == -1)
		{
			 //check if all players had passed, not valid
			if(this.currentIdx == this.previousIdx)
			{
				table.println("{Pass}  <== Not a legal move!!!");
				return;
			}
			else
			{
				//continue for the next player
				table.resetSelected();
				this.sendMessage(new CardGameMessage(CardGameMessage.MOVE, -1, cardIdx));
				return;
			}	
		}
		
		//get the hand from player input
		CardList listSelected = new CardList();	//temp var to store the list
		for(int i = 0; i < cardIdx.length; i++)
		{
			listSelected.addCard(this.playerList.get(currentIdx).getCardsInHand().getCard(cardIdx[i]));
		}
		currentList = new BigTwoHand(listSelected);
		
		//assign the type of the hand, by calling the checkType method
		playedHand = checkType(currentList);
		
		//check the type of the hand, gets null if it is invalid
		if(playedHand == null)
		{
			table.println("Not a legal move!!!");
			return;
		}
		
		//check for special case: if it is the first hand in the game, check if it contains 3 of Diamond
		firstCard = playerList.get(this.currentIdx).getCardsInHand().getCard(0);
		if(firstCard.getRank() == 2 && firstCard.getSuit() == 0)
		{
			//if the player does not play the diamond three, invalid hand
			if(playedHand.getCard(0).getRank() != 2 || playedHand.getCard(0).getSuit() != 0)
			{
				tablePrint(playedHand);
				table.println("  Does not contain three of diamond  <== Not a legal move!!!");
				return;
			}
		}
		
		//do not check beats if all other players passed
		if(this.currentIdx != this.previousIdx)
		{
			//check if the hand can beat the previous hand
			if(!playedHand.beats(handsOnTable.get(handsOnTable.size()-1)))
			{
				tablePrint(playedHand);
				table.println("  <== Not a legal move!!!");
				playedHand = null;
				return;
			}
		}

		///if it is a valid hand
		table.resetSelected();
		this.sendMessage(new CardGameMessage(CardGameMessage.MOVE, -1, cardIdx));
		return;
		
	}

	@Override
	public boolean isPlaying() {
		return this.isPlaying;
	}

	@Override
	public boolean endOfGame() {
		if(this.playerList.get(currentIdx).getNumOfCards() == 0)
		{
			isPlaying = false;
			return true;
		}
		else 
			return false;
	}
	
	/**
	 * an inner class that implements the Runnable interface.
	 * Upon receiving messages, the parseMessaage() method should be called to parse the messages accordingly.
	 * @author h1192391
	 *
	 */
	class ServerHandler implements Runnable
	{
		// implementation of method from the Runnable interface
		public void run() {
			CardGameMessage message;
			try {
				// waits for messages from the client
				while ((message = (CardGameMessage) ois.readObject()) != null) {
					parseMessage(message);
					table.repaint();
				} // close while
			}
			catch (Exception e) {
				e.printStackTrace();
				return;
			}
		} // run
	}


	@Override
	public void checkMove(int idx,int[] cardIdx) 
	{	
		
		//check for special case "Pass"
		if(cardIdx[0] == -1)
		{
			//continue for the next player
			currentIdx = (currentIdx + 1) % numOfPlayer;
			table.println("{pass}");
			table.println(playerList.get(this.currentIdx).getName() + "'s turn: ");
			return;
		}
		
		//get the hand from player input
		CardList listSelected = new CardList();	//temp var to store the list
		for(int i = 0; i < cardIdx.length; i++)
		{
			listSelected.addCard(this.playerList.get(currentIdx).getCardsInHand().getCard(cardIdx[i]));
		}
		currentList = new BigTwoHand(listSelected);
		
		//assign the type of the hand, by calling the checkType method
		playedHand = checkType(currentList);
		
		//print information
		tablePrint(playedHand);
		table.println("");	
		
		this.handsOnTable.add(playedHand);			//add to the array
		previousIdx = this.currentIdx;				//change index
		table.setPreviousIdx(previousIdx);
		this.playerList.get(currentIdx).removeCards(currentList);	//remove cards from player's hand
		
		//check end of game
		if(endOfGame())
		{
			//show messages in a dialog box
			String _msg;	//content of the box
			String _title;	//title of the box
			_msg = "Game ends \n";
			_msg = _msg + this.playerList.get(0).getName()+" has "+this.playerList.get(0).getNumOfCards()+" cards in hand. \n";
			_msg = _msg + this.playerList.get(1).getName()+" has "+this.playerList.get(1).getNumOfCards()+" cards in hand. \n";
			_msg = _msg + this.playerList.get(2).getName()+" has "+this.playerList.get(2).getNumOfCards()+" cards in hand. \n";
			_msg = _msg + this.playerList.get(3).getName()+" has "+this.playerList.get(3).getNumOfCards()+" cards in hand. \n";
			if(this.playerList.get(this.playerID).getNumOfCards() == 0){
				_title = "You win!";
			}else{
				_title = "You lose!";
			}
			//dialog box
			JOptionPane.showMessageDialog(null, _msg, _title , JOptionPane.DEFAULT_OPTION);
			//ready
			this.sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
			return;
		}
		
		//continue for the next player
		currentIdx = (currentIdx + 1) % numOfPlayer;
		table.println(playerList.get(this.currentIdx).getName() + "'s turn: ");
		
		return;
	}
	
	 // a method that checks the type of the hand
	private Hand checkType(Hand currentHand)
	{
		//sort the cards accordingly, so that the hands are always well-sorted before any operations
		//methods such as isValid, getTopCard assume the cards have been sorted
		currentHand.sort();

		//check invalid case: whether there is a same card played twice
		for(int i=0; i < currentHand.size()-1; i++)
		{
			if(currentHand.getCard(i).getRank() == currentHand.getCard(i+1).getRank())
			{
				if(currentHand.getCard(i).getSuit() == currentHand.getCard(i+1).getSuit())
				{
					return null;
				}
			}
		}

		if(currentHand.size() == 1)
		{
			//if hand size is one, assign it to single
			Single convertToSingle = new Single(currentHand);
			if(convertToSingle.isValid())	//check structural valid
			{
				return convertToSingle;
			}	
		}
		else if(currentHand.size() == 2)
		{
			//if hand size is two, assign it to pair
			Pair convertToPair = new Pair(currentHand);
			if(convertToPair.isValid())
			{
				return convertToPair;
			}
		}
		else if(currentHand.size() == 3)
		{
			//if hand size is three, assign it to triple
			Triple convertToTriple = new Triple(currentHand);
			if(convertToTriple.isValid())
			{
				return convertToTriple;
			}
		}
		else if(currentHand.size() == 5)	//if hand size is five, check its type one by one
		{
			StraightFlush convertToSF = new StraightFlush(currentHand);
			if(convertToSF.isValid())		//check the type of the hand
			{
				return convertToSF;	
			}
			Quad convertToQuad = new Quad(currentHand);
			if(convertToQuad.isValid())
			{
				return convertToQuad;
			}
			FullHouse convertToFH = new FullHouse(currentHand);
			if((convertToFH).isValid())
			{
				return convertToFH;
			}
			Flush convertToFlush = new Flush(currentHand);
			if(convertToFlush.isValid())
			{
				return convertToFlush;
			}
			Straight convertToStraight = new Straight(currentHand);
			if(convertToStraight.isValid())
			{
				return convertToStraight;
			}
		}
		//return null if the hand does not suit any type
		return null;
	}
	
	//print information on table
	private void tablePrint(Hand _playedHand)
	{
		table.print(_playedHand.getType());
		String string = "";
		for (int i = 0; i < _playedHand.size(); i++) {
			string = string + "[" + _playedHand.getCard(i) + "]";
			if (i % 13 != 0) {
				string = " " + string;
			}
		}
		table.print(string);
	}
	
	
}

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

/**
 * The BigTwoTable class implements the CardGameTable interface. 
 * It is used to build a GUI for the Big Two card game and handle all user actions. 
 * @author Lee
 *
 */
public class BigTwoTable implements CardGameTable
{
	//required components
	private BigTwoClient game;			//a card game associates with this table.
	private boolean[] selected;		//a boolean array indicating which cards are being selected.
	int activePlayer;				//an integer specifying the index of the active player.
	int activeSelection;			//an integer specifying the index of the active selection (i.e., the index of the player from whom the active player can pick the cards to make a move).
	private JFrame frame;			//the main window of the application.
	private JPanel bigTwoPanel;		//a panel for showing the cards of each player and the cards played on the table.
	private JButton playButton;		//a “Play” button for the active player to play the selected cards.
	private JButton passButton;		//a “Pass” button for the active player to pass his/her turn to the next player.
	private JTextArea textArea;		//a text area for showing the current game status as well as end of game messages.
	private Image[][] cardImages;	//a 2D array storing the images for the faces of the cards.
	private Image cardBackImage;	//an image for the backs of the cards.
	private Image[] avatars;		//an array storing the images for the avatars.
	
	//self-introduced components
	private JPanel buttonPanel;		//a panel for showing the play/pass buttons
	private ArrayList<CardGamePlayer> playerList;	//reference to the list of player
	private int playerCards;			//store the number of cards held by a particular player
	private int previousIdx;		//store the index of the previous player
	private JMenuBar menuBar;		//for displaying the menu
	private JMenu menu;				//for displaying the menu items
	private JMenuItem quitItem;		//for quitting the game after being pressed
	private JMenuItem connectItem;		//for quitting the game after being pressed
	private JScrollPane scrollerForText;	//vertical scrollbar for the text area
	private JTextArea chatArea;		//a text area for showing the game messages.
	private JScrollPane scrollerForChat;	//vertical scrollbar for the text area
	private JPanel pane;			//container for layout
	private JTextArea textField;	//for typing messages
	private JLabel messageLabel;	//showing the message word
	
	/**
	 * BigTwoTable(CardGame game) – a constructor for creating a BigTwoTable. 
	 * The parameter game is a reference to a card game associates with this table.
	 */
	public BigTwoTable(BigTwoClient game)
	{
		//assign game to the reference
		this.game = game;
		
		//array initialization
		avatars = new Image[game.getNumOfPlayers()];
		cardImages = new Image[4][13];
		selected = new boolean[13];
		
		//load avatars images
		avatars[0] = new ImageIcon(this.getClass().getResource("/avatars/batman_128.png")).getImage();
		avatars[1] = new ImageIcon(this.getClass().getResource("/avatars/flash_128.png")).getImage();
		avatars[2] = new ImageIcon(this.getClass().getResource("/avatars/green_lantern_128.png")).getImage();
		avatars[3] = new ImageIcon(this.getClass().getResource("/avatars/superman_128.png")).getImage();
		
		//load card images
		cardBackImage = new ImageIcon((this.getClass().getResource("/cards/b.gif"))).getImage();
		String ImageName;
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 13; j++)
			{
				switch(i)
				{
					case 0:	
						ImageName = "d";
						break;
					case 1:	
						ImageName = "c";
						break;
					case 2:	
						ImageName = "h";
						break;
					case 3:	
						ImageName = "s";
						break;
					default:
						ImageName = "";
						break;
				}
				switch(j)
				{
					case 0:	
						ImageName = "a" + ImageName;
						break;
					case 9:	
						ImageName = "t" + ImageName;
						break;
					case 10:	
						ImageName = "j" + ImageName;
						break;
					case 11:	
						ImageName = "q" + ImageName;
						break;
					case 12:	
						ImageName = "k" + ImageName;
						break;
					default:
						ImageName = Integer.toString(j+1) + ImageName;
						break;
				}
				ImageName = "/cards/" + ImageName+".gif";
				cardImages[i][j] = new ImageIcon(this.getClass().getResource(ImageName)).getImage();
			}
		}
		
		//swing initialization
		frame = new JFrame();
		bigTwoPanel = new BigTwoPanel();
		playButton = new JButton("Play");
		passButton = new JButton("Pass");
		textArea = new JTextArea();
		chatArea = new JTextArea();
		scrollerForText = new JScrollPane(textArea);
		scrollerForChat = new JScrollPane(chatArea);
		buttonPanel = new JPanel();
		menuBar = new JMenuBar();	
		menu = new JMenu("Game");				
		quitItem = new JMenuItem("Quit");	
		connectItem = new JMenuItem("Connect");	
		pane = new JPanel();
		textField = new JTextArea();
		messageLabel = new JLabel("message");
		
		messageLabel.setSize(10,10);
		
		//menu
		quitItem.addActionListener(new QuitMenuItemListener());
		connectItem.addActionListener(new ConnectMenuItemListener());
		textField.addKeyListener(new KeyboardListener());
		menu.add(connectItem);
		menu.add(quitItem);
		menuBar.add(menu);
		
		//main panel
		bigTwoPanel.setBackground(Color.GREEN);
		
		//text area
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setSize(450, 400);
		scrollerForText.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollerForText.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollerForText.setPreferredSize(new Dimension(400, 400));
		chatArea.setEditable(false);
		chatArea.setLineWrap(true);
		chatArea.setSize(450, 400);
		scrollerForChat.setPreferredSize(new Dimension(400, 400));
		scrollerForChat.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollerForChat.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		textField.setEditable(true);
		textField.setLineWrap(true);
		textField.setSize(350,30);
		textField.setPreferredSize(new Dimension(350, 30));
		
		//buttons
		playButton.addActionListener(new PlayButtonListener());
		passButton.addActionListener(new PassButtonListener());
		buttonPanel.add(passButton);
		buttonPanel.add(playButton);
		
		//grid bag
		pane.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    c.fill = GridBagConstraints.VERTICAL;
	    c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
	    pane.add(scrollerForText, c);
	    
	    c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 1;
	    pane.add(scrollerForChat, c);
	    
	    c.fill = GridBagConstraints.VERTICAL;
	    c.gridwidth = 1;
	    c.weightx = 0.5;
	    c.gridx = 0;
		c.gridy = 2;
		
		c.ipady = 10;
	    pane.add(messageLabel, c);
	    
	    c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 1;
		c.gridy = 2;
		c.ipady = 10;
	    pane.add(textField, c);
		
		//frame
		frame.add(BorderLayout.CENTER, bigTwoPanel);
		frame.add(BorderLayout.EAST, pane);
		frame.add(BorderLayout.SOUTH, buttonPanel);
		frame.add(BorderLayout.NORTH, menuBar);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 1000);
		frame.setVisible(true);
		
	}

	@Override
	public void setActivePlayer(int activePlayer) {
		 this.activePlayer = activePlayer;
	}

	@Override
	public void setActiveSelection(int activeSelection) {
		 this.activeSelection = activeSelection;
	}

	/**
	 * self-introduced method
	 * @param previousIdx	get the index of the player who played the last hand
	 */
	public void setPreviousIdx(int previousIdx) {
		 this.previousIdx = previousIdx;
	}

	@Override
	public int[] getSelected() 
	{
		//count the total number of selected cards
		int selectedLength = 0;
		for(int i = 0; i < selected.length; i++)
		{
			if(selected[i])
			{
				selectedLength++;
			}
		}
		
		if(selectedLength == 0)
		{
			//return null if nothing was selected
			return null;
		}
		
		//store the selected indices
		int[] indices = new int[selectedLength];
		int j = 0;
		for(int i = 0; i < selected.length; i++)
		{
			if(selected[i])
			{
				indices[j] = i;
				j++;
			}
		}
		return indices;
	}

	@Override
	public void resetSelected() 
	{
		//assign all values in the array to false
		for(int i = 0; i < 13; i++)
		{
			selected[i] = false;
		}
		repaint();
	}

	@Override
	public void repaint() {
		 frame.repaint();
	}

	@Override
	public void print(String msg) {
		 this.textArea.append(msg);
		 this.textArea.setCaretPosition(textArea.getDocument().getLength());
	}

	@Override
	public void println(String msg) {
		 this.textArea.append(msg +"\n");
		 this.textArea.setCaretPosition(textArea.getDocument().getLength());
	}

	@Override
	public void clearTextArea() {
		 this.textArea.setText(null);
	}

	@Override
	public void reset() {
		clearTextArea();
		this.resetSelected();
		enable();
		frame.repaint();
	}

	@Override
	public void enable() 
	{
		//GUI are enabled
		bigTwoPanel.setEnabled(true);
		playButton.setEnabled(true);
		passButton.setEnabled(true);
		buttonPanel.setEnabled(true);
	}

	@Override
	public void disable() 
	{
		//GUI are disabled except quit item, and text area
		bigTwoPanel.setEnabled(false);
		playButton.setEnabled(false);
		passButton.setEnabled(false);
		buttonPanel.setEnabled(false);
	}
	
	/**
	 * an inner class that extends the JPanel class and implements the MouseListener interface. 
	 * Overrides the paintComponent() method inherited from the JPanel class to draw the card game table. 
	 * Implements the mouseClicked() method from the MouseListener interface to handle mouse click events.
	 * @author h1192391
	 *
	 */
	class BigTwoPanel extends JPanel implements MouseListener
	{
		BigTwoPanel()
		{
			this.addMouseListener(this);
		}
		
		public void paintComponent(Graphics g){
		    super.paintComponent(g);
		  //paint the cards and avatars
		    for(int i = 0; i < game.getNumOfPlayers(); i++)
		    {
		    	//paint names and avatars
		    	if(game.getPlayerList().get(i).getName() != null)
		    	{
		    		g.drawString(game.getPlayerList().get(i).getName(), 10, (i * 180) + 50);
		    		g.drawImage(avatars[i], 10, (i * 180) + 50, this);
		    	}
		    	//paint cards
			    playerList = game.getPlayerList();
		    	playerCards = playerList.get(i).getNumOfCards();
		    	
		    	//only show cards held by the player, or show all cards if the game is ended
			    if(i == activeSelection || game.endOfGame())
			    {
			    	//show cards of the active player
			    	for(int j = 0; j <playerCards; j++)
			    	{
			    		if(selected[j])
			    		{
				    		//paint selected cards
			    			g.drawImage(cardImages[playerList.get(i).getCardsInHand().getCard(j).getSuit()][playerList.get(i).getCardsInHand().getCard(j).getRank()],
				    				150 + 20 * j, 
				    				(i * 180) + 30, 
				    				this);
			    		}
			    		else
			    		{
				    		//paint cards in normal state
			    			g.drawImage(cardImages[playerList.get(i).getCardsInHand().getCard(j).getSuit()][playerList.get(i).getCardsInHand().getCard(j).getRank()],
				    				150 + 20 * j, 
				    				(i * 180) + 50, 
				    				this);
			    		}
			    	}
			    	
			    }
			    else
			    {
			    	//show back of cards of other players
			    	for(int j = 0; j <playerCards; j++)
			    	{
			    		g.drawImage(cardBackImage, 150 + 20 * j, (i * 180) + 50, this);
			    	}
			    }
			    
		    }
		    
		    //paint the hand on table
		    if(game.getHandsOnTable() != null)
		    {
			    g.drawString("Played by "+playerList.get(previousIdx).getName(), 10, (game.getNumOfPlayers() * 180) + 50);
			    if(game.getHandsOnTable().get(game.getHandsOnTable().size() - 1) != null)
			    {
			    	Hand tableList = game.getHandsOnTable().get(game.getHandsOnTable().size() - 1);
			    	playerCards = game.getHandsOnTable().get(game.getHandsOnTable().size() - 1).size();
			    	
			    	for(int k = 0; k <playerCards; k++)
			    	{
		    			g.drawImage(cardImages[tableList.getCard(k).getSuit()][tableList.getCard(k).getRank()],
			    				150 + 20 * k, 
			    				(game.getNumOfPlayers() * 180) + 50, 
			    				this);
			    	}
			    }
		    }

		}
		
		@Override
		public void mouseClicked(MouseEvent event) 
		{
			//click on card->card go up/down
			//detect by coordinate
			int x = event.getX();
			int y = event.getY();
			x = (x - 150) / 20;
			if((y >= 50 &&y <145) || (y >= 230 &&y <325)  || (y >= 410 &&y <505)  || (y >= 590 &&y <685))
			{
				y = (y - 50) / 180;
			}else{
				y = -1;
			}
			if(event.getX() > (playerList.get(activePlayer).getNumOfCards() * 20 + 150) && event.getX() < (playerList.get(activePlayer).getNumOfCards() * 20 + 205))
			{
				x = playerList.get(activePlayer).getNumOfCards() -1;
			}else if(event.getX() < 150 || event.getX() >= (playerList.get(activePlayer).getNumOfCards() * 20 + 205)){
				x = -1;
			}
			if(y == -1 || x == -1)
			{ return; }
			if(y == activePlayer){
				selected[x] = !selected[x];
			}
			frame.repaint();
		}
		
		@Override
		public void mouseEntered(MouseEvent event) {
			//do nothing
		}
		@Override
		public void mouseExited(MouseEvent event) {
			//do nothing
		}
		@Override
		public void mousePressed(MouseEvent event) {
			//do nothing
		}
		@Override
		public void mouseReleased(MouseEvent event) {
			//do nothing
		}
	}
	
	/**
	 * an inner class that implements the ActionListener interface. 
	 * Implements the actionPerformed() method from the ActionListener interface to handle button-click events for the “Play” button.
	 * @author h1192391
	 *
	 */
	class PlayButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{	
			game.makeMove(activeSelection, getSelected());
		}
	}
	
	/**
	 * an inner class that implements the ActionListener interface. 
	 * Implements the actionPerformed() method from the ActionListener interface to handle button-click events for the “Pass” button.
	 * @author h1192391
	 *
	 */
	class PassButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			//pass an array containing -1 to the checkMove so to check for the special case "Pass"
			game.makeMove(activeSelection, new int[] {-1});		
		}
	}
	
	/**
	 * an inner class that implements the ActionListener interface. 
	 * Implements the actionPerformed() method from the ActionListener interface to handle menu-item-click events for the “Quit” menu item.
	 * @author h1192391
	 *
	 */
	class QuitMenuItemListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			//exit the program
			System.exit(0);
		}
	}
	
	/**
	 * an inner class that implements the ActionListener interface. 
	 * Implements the actionPerformed() method from the ActionListener interface to handle menu-item-click events for the “Connect” menu item.
	 * connect to the server
	 * @author h1192391
	 *
	 */
	class ConnectMenuItemListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			game.makeConnection();
		}
		
	}
	
	
	/**
	 * when the "Enter" key is pressed,
	 * send a message to the server
	 */
	class KeyboardListener implements KeyListener
	{
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				//send message
				game.sendMessage(new CardGameMessage(CardGameMessage.MSG, -1, textField.getText()));
				//reset the text fields
				textField.setText(null);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			//do nothing
		}

		@Override
		public void keyTyped(KeyEvent e) {
			//do nothing
		}
		
	}

	/**
	 * print messages onto the chat area
	 * @param msg
	 */
	public void printlnChat(String msg) {
		 this.chatArea.append(msg +"\n");
		 this.chatArea.setCaretPosition(chatArea.getDocument().getLength());
	}
	
}

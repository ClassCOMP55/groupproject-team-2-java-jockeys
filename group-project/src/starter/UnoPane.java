//This file displays the game play of Uno

package starter;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.*;

public class UnoPane extends GraphicsPane {
	private MainApplication program;
	private GButton unoButton;
	private GButton drawCard;
	private GImage currCardDisplayed;
	private GLabel playerHand1;
	private GLabel playerHand2;
	public static final int playerHandX = 1500;
	public static final int playerHandY = 875;
	private Player player1;
	private Player player2;
	private Dealer deck;
	private UnoCard currentCard;
	private UnoCard cardClicked;
	private boolean player1Turn = true;
	private int cardY;
	private int cardX;
	private String colorChange = "";
	private GLabel prompt;
	private GButton red;
	private GButton blue;
	private GButton green;
	private GButton yellow;
	private boolean wildCase = false;
	private boolean unoButtonPressed = false;
	private GButton currentColor;
	
	Color myBlue = new Color(0,128,255); 
	Color myGreen = new Color(0,204,0); 
	
	public UnoPane(MainApplication app) {
		super();
		program = app;
		
		//Creates UNO button 
		unoButton = new GButton("UNO", 1550,25, 200, 200);
		unoButton.setColor(Color.WHITE);
		unoButton.setFillColor(Color.ORANGE);
		
		//Creates DRAW CARD button
		drawCard = new GButton("DRAW CARD", 50, 100, 250, 100);
		drawCard.setColor(Color.WHITE);
		drawCard.setFillColor(Color.RED);
		
		//Shown when Player 1's hand is visible 
		playerHand1 = new GLabel("Player 1", playerHandX, playerHandY);
		playerHand1.setFont("TimesRoman-Bold-45");
		playerHand1.setColor(Color.BLACK);
		
		//Shown when Player 2's hand is visible
		playerHand2 = new GLabel("Player 2", playerHandX, playerHandY);
		playerHand2.setFont("TimesRoman-Bold-45");
		playerHand2.setColor(Color.BLACK);
		
		//Initializes both players
		player1 = program.getPlayer1();
		player2 = program.getPlayer2();
		
		//Initializes deck
		deck = new Dealer(108);
		for(int i = 0; i < 7; i++) {
			player1.addToHand(deck.deal());
			player2.addToHand(deck.deal());
		}
		
		
		//current card
		currentCard = deck.deal();
		while(currentCard.getColorType().getColor() == "Wild") {
			currentCard = deck.deal();
		}
		
		//Current color button
		currentColor = new GButton("Current Color", 600, 25, 200, 200);
		currentColor.setColor(Color.WHITE);
		
		//For wild case
		prompt = new GLabel("Choose a Color:", 700, 100);
		prompt.setFont("TimesRoman-Bold-50");
		red = new GButton("", 50, 300, 300,300);
		red.setFillColor(Color.RED);
		blue = new GButton("", 500, 300, 300,300);
		blue.setFillColor(myBlue);
		green = new GButton("", 950, 300, 300,300);
		green.setFillColor(myGreen);
		yellow = new GButton("", 1400, 300, 300,300);
		yellow.setFillColor(Color.ORANGE);
	}
	
	//updates the current color button based off of the current color if a color is choosen
	public void updateCurrentColor() {
		//creates current color
		if(currentCard.getColorType().getColor() == "Red") {
			currentColor.setFillColor(Color.RED);
		}
		else if(currentCard.getColorType().getColor() == "Green") {
			currentColor.setFillColor(myGreen);
		}
		else if(currentCard.getColorType().getColor() == "Blue") {
			currentColor.setFillColor(myBlue);
		}
		else if(currentCard.getColorType().getColor() == "Yellow") {
			currentColor.setFillColor(Color.ORANGE);
		}
		else if(currentCard.getColorType().getColor() == "Wild") {
			if(colorChange!= null) {
				if(colorChange == "Red") {	
					currentColor.setFillColor(Color.RED);
				}
				else if(colorChange == "Green") {
					currentColor.setFillColor(myGreen);
				}
				else if(colorChange == "Blue") {
					currentColor.setFillColor(myBlue);
				}
				else if(colorChange== "Yellow") {
					currentColor.setFillColor(Color.ORANGE);
				}
			}
		}
	}
	
	//gets player 1
	public Player getPlayer1() {
		return player1;
	}
	
	//gets player 2
	public Player getPlayer2() {
		return player2;
	}
	
	//displays the current players hand
	public void displayCards(Player player) {
		for(int i = 0; i < player.getPlayerHand().length; i++) {
			if(player.getPlayerHand()[i] != null) {
				if(i% 25 < 12) {	cardY = 250;} else if(i % 25 < 24) { cardY = 470;} else {cardY = 690;}
				cardX = (i%12)*150+20;
				String filepath = player.getPlayerHand()[i].getColorType().getColor() + "/" + player.getPlayerHand()[i].getCardValue().getValue() + ".png";
				GImage card = new GImage(filepath, cardX,cardY);
				card.setSize(100, 200);
				int[] location = {cardX, cardY, cardX+100, cardY+200};
				player.setCoordinates(i, location);
				program.add(card);
			}
		}
	}
	
	//updates and displays the current card
	public void displayCurrentCard(UnoCard card) {
		String path = card.getColorType().getColor() + "/" + card.getCardValue().getValue() + ".png";
		currCardDisplayed = new GImage(path, 850, 25);
		currCardDisplayed.setSize(100, 200);
		program.add(currCardDisplayed);	
	}
	
	//gets the current player playing based on a boolean variable
	public Player getCurrentPlayer() {
		if(player1Turn) {
			return player2;	
		}
		else {
			return player1;	
		}
	}
	
	//gets the obj pressed's location
	public int[] getObjSpace(GObject obj) {
		int[] space = new int[4];
		space[0] = (int) obj.getX();
		space[1] = (int) obj.getY();
		space[2] = (int) (obj.getX() + obj.getWidth());
		space[3] = (int) (obj.getY() + obj.getHeight());
		return space;
	}
	
	//checks what card the card pressed on matches in the player's hand
	public UnoCard userCardPressed(GObject obj) {
		int[] location = getObjSpace(obj);
		for(int i = 0; i < getCurrentPlayer().playerHand.length; i++) {
			if(getCurrentPlayer().playerHand[i] != null) {
				if(getCurrentPlayer().playerHand[i].getCoordinates()[0] == location[0] &&
					getCurrentPlayer().playerHand[i].getCoordinates()[1] == location[1] &&
					getCurrentPlayer().playerHand[i].getCoordinates()[2] == location[2] &&
					getCurrentPlayer().playerHand[i].getCoordinates()[3] == location[3]) {
					cardClicked = getCurrentPlayer().playerHand[i];
					if(cardClicked.getColorType().getColor() == "Wild") {
						wildCase = true;
					}
					return cardClicked;
				}
			}
		}
		return null;
	}
	
	//checks if the card pressed is a valid play
	public boolean validateCard(GObject obj) {
		UnoCard card = userCardPressed(obj);
		if(card != null) {
			if(currentCard.getColorType().getColor() == card.getColorType().getColor()) {
				return true;
			}
			else if(currentCard.getCardValue().getValue() == card.getCardValue().getValue()) {
				return true;
			}
			else if(card.getColorType().getColor() == "Wild") {
				return true;
			}
			else if(card.getColorType().getColor() == colorChange) {
				return true;
			}
		}
		return false;
	}
	
	//checks if the card clicked matches a card in the player hand
	public boolean checkCardMatch(UnoCard card1, UnoCard card2) {
		if(card1.getColorType().getColor() == card2.getColorType().getColor() &&
			card1.getCardValue().getValue() == card2.getCardValue().getValue() &&
			card1.getCoordinates()[0] == card2.getCoordinates()[0] &&
			card1.getCoordinates()[1] == card2.getCoordinates()[1] &&
			card1.getCoordinates()[2] == card2.getCoordinates()[2] &&
			card1.getCoordinates()[3] == card2.getCoordinates()[3]) {
				return true;
		}
		return false;
	}
	
	//removes a card from the current player's hand
	public void changeHands() {
		currentCard = cardClicked;
		for(int i = 0; i < getCurrentPlayer().getPlayerHand().length; i++) {
			if(getCurrentPlayer().getPlayerHand()[i] != null) {
				if(checkCardMatch(cardClicked, getCurrentPlayer().getPlayerHand()[i])) {
					getCurrentPlayer().removeFromHand(i);
					return;
				}
			}
		}
	}
	
	//changes the current player's turn based on the card
	public void affectPlayerTurn() {
		if(getCurrentPlayer() == player1) {
			player1Turn = true;
		}
		else {
			player1Turn = false;
		}
	}
	
	//affects the next player by skipping their turn and adding cards based on the card played
	public void affectNextPlayer() {
		if(currentCard.getCardValue().getValue() == "Wild") {
			affectPlayerTurn();
		}
		else if(currentCard.getCardValue().getValue() == "skip") {
			affectPlayerTurn();
		}
		else if(currentCard.getCardValue().getValue() == "reverse") {
			affectPlayerTurn();
		}
		else if(currentCard.getCardValue().getValue() == "+2" ) {
			for(int i = 0; i<2;i++) {
				if(getCurrentPlayer() == player1) {
					player2.addToHand(deck.deal());
				}
				else {
					player1.addToHand(deck.deal());
				}
			}
			affectPlayerTurn();
		}
		else if(currentCard.getCardValue().getValue() == "+4") {
			for(int i = 0; i<4;i++) {
				if(getCurrentPlayer() == player1) {
					player2.addToHand(deck.deal());
				}
				else {
					player1.addToHand(deck.deal());
				}
			}
		}
	}
	
	//penalises players if the play the last card without pressing uno
	public void penalty() {
		if (player1.getNumCards() == 0 && !unoButtonPressed) {
			for (int i=0; i<2; i++) {
				player1.addToHand(deck.deal());
			}     
		}          
		else if (player2.getNumCards() == 0 && !unoButtonPressed) {
			for (int i=0; i<2; i++) {
				player2.addToHand(deck.deal());
			}      
		} 
	}
	
	//checks when to ask to switch players
	public boolean checkCardForSwitch() {
		if(currentCard.getCardValue().getValue() == "+2") {
			return true;
		}
		else if(currentCard.getCardValue().getValue() == "+4") {
			return true;
		}
		else if(currentCard.getCardValue().getValue() == "reverse") {
			return true;
		}
		else if(currentCard.getCardValue().getValue() == "skip") {
			return true;
		}
		return false;
	}
	
	@Override
	public void showContents() {
		program.add(unoButton);
		program.add(drawCard);
		program.add(currentColor);
		if(player1Turn) {
			playerHand1.setLabel(player1.playerName);
			displayCards(player1);
			program.add(playerHand1);
			player1Turn = false;
		}
		else {
			playerHand2.setLabel(player2.playerName);
			displayCards(player2);
			program.add(playerHand2);
			player1Turn = true;
		}		
		displayCurrentCard(currentCard);
		if(wildCase) {
			program.removeAll();
			program.add(prompt);
			program.add(red);
			program.add(yellow);
			program.add(blue);
			program.add(green);
		}
		updateCurrentColor();

	}

	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
		program.removeAll();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj instanceof GImage && obj != currCardDisplayed) {
			if(validateCard(obj)) {
				changeHands();
				affectNextPlayer();
				penalty();
				hideContents();
				if(!checkCardForSwitch()) {
					program.switchToSwitchPane();
				}
				else if(cardClicked.getCardValue().getValue() == "Wild") {
					showContents();
					program.switchToSwitchPane();
				}
				else {
					showContents();
				}
				if(player1.getNumCards() == 0 && unoButtonPressed) {
					hideContents();
					program.switchToWinPane();
				}
				else if(player2.getNumCards() == 0 && unoButtonPressed) {
					hideContents();
					program.switchToWinPane();
				}
				unoButtonPressed = false;
			}
		}
		if(obj == drawCard) {
			if(getCurrentPlayer().getNumCards() < 25) {
				getCurrentPlayer().addToHand(deck.deal());
			}
			hideContents();
			program.switchToSwitchPane();
		}
		if(obj == unoButton) {
			unoButtonPressed = true;
		}
		if (obj == red) {
			colorChange = "Red";
			wildCase = false;
			hideContents();
			showContents();
		}
		if (obj == yellow) {
			colorChange = "Yellow";
			wildCase = false;
			hideContents();
			showContents();
		}
		if (obj == blue) {
			colorChange = "Blue";
			wildCase = false;
			hideContents();
			showContents();
		}
		if (obj == green) {
			colorChange = "Green";
			wildCase = false;
			hideContents();
			showContents();
		}
		
	}
}

package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameController {

    @FXML
    private Label playerNameLabel, betLabel, cashLabel, dealerCardTotalLabel, playerCardTotalLabel, insuranceBetLabel, winLoseLabel, 
    			  dealerBlackjackOrBustLabel, playerBlackjackOrBustLabel;
    @FXML
    private TextField betTextField;
    @FXML
    private Button betButton, doubleButton, hitButton, standButton, playAgainButton;
    @FXML
    private ImageView dealerCard1, dealerCard2, dealerCard3, dealerCard4, dealerCard5, dealerCard6, playerCard1, playerCard2, 
                      playerCard3, playerCard4, playerCard5, playerCard6;


    private List<Button> buttonList;
    private List<ImageView> dealerImageViewList;
    private List<ImageView> playerImageViewList;
    List<Card> playerHand;
    List<Card> dealerHand;
    //private List<ImageView> playerImageViewList2; //When there is a split

    private Deck deck = new Deck();
    private Dealer dealer = new Dealer();
    private Player player = new Player(5000);
    private double bet;
    private double insuranceBet = 0;
    private int playerCardTotal;
    private int dealerCardTotal;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    //the method for writing the players name to the playerNameLabel
    public void displayName(String playerName) {
        playerNameLabel.setText("PLAYER NAME: " + playerName.toUpperCase());
        cashLabel.setText("CASH: " + player.showMoney());
        player.setName(playerName);
    }

    //Helper method to hid nodes
    public void hideStuff(){
        buttonList = new ArrayList<>();
        buttonList.add(doubleButton);
        buttonList.add(hitButton);
        buttonList.add(standButton);

        //Disable and hide buttons initially
        for (Button button: buttonList){
            button.setDisable(true);
            button.setOpacity(0.0);
        }
        playAgainButton.setDisable(true);
        playAgainButton.setOpacity(0.0);
        
        dealerImageViewList = new ArrayList<>();
        dealerImageViewList.add(dealerCard1);
        dealerImageViewList.add(dealerCard2);
        dealerImageViewList.add(dealerCard3);
        dealerImageViewList.add(dealerCard4);
        dealerImageViewList.add(dealerCard5);
        dealerImageViewList.add(dealerCard6);
        
      //Disable and hide Dealer ImageViews initially
        for (ImageView imageView: dealerImageViewList){
            imageView.setDisable(true);
            imageView.setOpacity(0.0);
        }
        
        playerImageViewList = new ArrayList<>();
		playerImageViewList.add(playerCard1);
		playerImageViewList.add(playerCard2);
		playerImageViewList.add(playerCard3);
		playerImageViewList.add(playerCard4);
		playerImageViewList.add(playerCard5);
		playerImageViewList.add(playerCard6);
		
		//Disable and hide Player ImageViews initially.
        for (ImageView imageView: playerImageViewList){
            imageView.setDisable(true);
            imageView.setOpacity(0.0);
        }
        
        winLoseLabel.setDisable(true);
        winLoseLabel.setOpacity(0.0);
        dealerBlackjackOrBustLabel.setDisable(true);
        dealerBlackjackOrBustLabel.setOpacity(0.0);
        playerBlackjackOrBustLabel.setDisable(true);
        playerBlackjackOrBustLabel.setOpacity(0.0);

    }

    //The main game method all the helper methods in this class are called here.
    public void setUpTheGame(ActionEvent event) throws IOException{
    	hideStuff();
    	
        try {
            bet = Double.parseDouble(betTextField.getText());
            if (bet < 25 || bet > player.showMoney()) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("IMPROPER BET");
                alert.setHeaderText("This bet is not allowed.");
                alert.setContentText("Bet must be between 25 and " + player.showMoney() + ".\nInput must be a number.");

                alert.show();

            } else {
                takeBetAndDeal();
                //Check if the player has blackjack
                if(playerCardTotal == 21) {
                	playerBlackjackOrBustLabel.setDisable(false);
                	playerBlackjackOrBustLabel.setOpacity(1.0);
                	playerBlackjackOrBustLabel.setTextFill(Color.YELLOW);
                	
                    player.blackjackBet();
                    cashLabel.setText("CASH: "+ player.showMoney());
                    betLabel.setText("BET: 0");
                    bet = 0;
                	stand(event);
                }
            }
        }
        catch (NumberFormatException e){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("IMPROPER BET");
            alert.setHeaderText("This bet is not allowed.");
            alert.setContentText("Bet must be between 25 and " + player.showMoney() + ".\nInput must be a number.");

            alert.show();
        }
        
    }
    
    //takes Bet and dealers cards
    public void takeBetAndDeal() {
    	betLabel.setText("BET: " + bet);
        player.makeBet(bet);
        cashLabel.setText("CASH: "+ player.showMoney());

        //Disable and hide button and text field.
        betButton.setDisable(true);
        betButton.setOpacity(0.0);
        betTextField.setDisable(true);
        betTextField.setOpacity(0.0);
        
        //Dealing cards.
        dealer.deal(deck);
        playerHand = dealer.getPlayerHand();
        dealerHand = dealer.getDealerHand();
        
        //showing players cards.
        for (int i = 0; i < playerHand.size(); i++){
        	playerImageViewList.get(i).setDisable(false);
            playerImageViewList.get(i).setOpacity(1.0);
            playerImageViewList.get(i).setImage(playerHand.get(i).getImage());
            playerCardTotal += playerHand.get(i).getValue();
        }
        playerCardTotalLabel.setText("PLAYER CARD TOTAL: " + playerCardTotal);
        
        //showing 1 of the dealers cards and one face down.
    	dealerImageViewList.get(0).setDisable(false);
        dealerImageViewList.get(0).setOpacity(1.0);
        dealerImageViewList.get(0).setImage(dealerHand.get(0).getImage());
        dealerCardTotal += dealerHand.get(0).getValue();
        dealerCardTotalLabel.setText("DEALER CARD TOTAL: " + dealerCardTotal);
       
        Image facedown = new Image(getClass().getResourceAsStream("1_astronaut.png"));
        dealerImageViewList.get(1).setDisable(false);
        dealerImageViewList.get(1).setOpacity(1.0);
        dealerImageViewList.get(1).setImage(facedown);
        
        if(dealerHand.get(0).getName().equals("ace")) {
        	Alert insurance = new Alert(AlertType.CONFIRMATION);
            insurance.setTitle("INSURANCE?");
            insurance.setHeaderText("The dealer is showing ace.");
            insurance.setContentText("Would you like insurance?");
            ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            insurance.getButtonTypes().setAll(yesButton, noButton);
            insurance.showAndWait().ifPresent(type -> {
            	if (type == yesButton) {
            		insuranceBet = 0.5*bet;
            		player.makeBet(insuranceBet);
            		insuranceBetLabel.setText("INSURANCE BET: " + insuranceBet);		
            	} 
            	else if (type == noButton) {
            		insuranceBetLabel.setText("INSURANCE BET: " + insuranceBet);
            	} 
            	else { 
            		insuranceBetLabel.setText("INSURANCE BET: " + insuranceBet);
            	}
            });
        }
        
        //Showing buttons for player to choose.
    	for (int i = 0; i < buttonList.size(); i++){
            buttonList.get(i).setDisable(false);
            buttonList.get(i).setOpacity(1.0);
        }
    }
    
    //the hit button method 
    public void hitHand(ActionEvent event) throws IOException {
    	dealer.hit(deck, playerHand); 
    	int carLoc = playerHand.size()-1;
    	playerImageViewList.get(carLoc).setDisable(false);
        playerImageViewList.get(carLoc).setOpacity(1.0);
    	playerImageViewList.get(carLoc).setImage(playerHand.get(carLoc).getImage());
    	
    	doubleButton.setDisable(true);
        doubleButton.setOpacity(0.0);
    	
        //check is player busts
    	if (playerCardTotal + playerHand.get(carLoc).getValue() > 21) {
	        if (playerHand.get(carLoc).getName().equals("ace")) {
	        	playerCardTotal += 1;
	        	playerCardTotalLabel.setText("PLAYER CARD TOTAL: " + playerCardTotal);
	        }
	        else {
	        	playerCardTotal += playerHand.get(carLoc).getValue();
	        	playerCardTotalLabel.setText("PLAYER CARD TOTAL: " + playerCardTotal); 
	        	playerBlackjackOrBustLabel.setDisable(false);
            	playerBlackjackOrBustLabel.setOpacity(1.0);
            	playerBlackjackOrBustLabel.setText("BUST");
            	playerBlackjackOrBustLabel.setTextFill(Color.BLACK);
	        	stand(event);
	        }
        }
    	//continues give player the option to stand
        else {
        	playerCardTotal += playerHand.get(carLoc).getValue();
        	playerCardTotalLabel.setText("PLAYER CARD TOTAL: " + playerCardTotal);
        }	
    }

    //double down button
    public void doubleHand(ActionEvent event) throws IOException {
    	dealer.hit(deck, playerHand); 
    	int carLoc = playerHand.size()-1;
    	playerImageViewList.get(carLoc).setDisable(false);
        playerImageViewList.get(carLoc).setOpacity(1.0);
    	playerImageViewList.get(carLoc).setImage(playerHand.get(carLoc).getImage());
    	playerCardTotal += playerHand.get(carLoc).getValue();
    	playerCardTotalLabel.setText("PLAYER CARD TOTAL: " + playerCardTotal);
    	
        betLabel.setText("BET: " + 2*bet);
        player.makeBet(bet);
        cashLabel.setText("CASH: "+ player.showMoney()); 
        
        stand(event);
    }
    
    //progresses the game and lets the dealer move.
    public void stand(ActionEvent event) throws IOException {
    	//reveals dealers last card
    	dealerImageViewList.get(1).setImage(dealerHand.get(1).getImage());
        dealerCardTotal += dealerHand.get(1).getValue();
        dealerCardTotalLabel.setText("DEALER CARD TOTAL: " + dealerCardTotal);
        
        //checks if dealer has blackjack
        if(dealerCardTotal == 21) {
        	dealerBlackjackOrBustLabel.setDisable(false);
        	dealerBlackjackOrBustLabel.setOpacity(1.0);
        	dealerBlackjackOrBustLabel.setTextFill(Color.YELLOW);
        	
        	player.insureBet(insuranceBet);
            cashLabel.setText("CASH: "+ player.showMoney());
            insuranceBet = 0;
            insuranceBetLabel.setText("INSURANCE BET: " + insuranceBet);
        }
        
        //checks if dealer has below 17 and hits until dealer has above 17.
        //checks if dealer busts
        int i = 2;
        while(dealerCardTotal < 17) {
        	dealer.hit(deck, dealerHand);
        	if (dealerCardTotal + dealerHand.get(i).getValue() > 21) {
        		if(dealerHand.get(i).getName().equals("ace")) {
        			dealerCardTotal += 1;
        		}
        		else {
        			dealerCardTotal += dealerHand.get(i).getValue();
        			dealerBlackjackOrBustLabel.setDisable(false);
                	dealerBlackjackOrBustLabel.setOpacity(1.0);
                	dealerBlackjackOrBustLabel.setText("BUST");
                	dealerBlackjackOrBustLabel.setTextFill(Color.BLACK);
        		}
        	}
        	else {
        		dealerCardTotal += dealerHand.get(i).getValue();
        	}
        	i++;
        }
        
        //shows dealers extra cards if the dealer has to hit.
        for (int j = 0; j < dealerHand.size(); j++){
        	dealerImageViewList.get(j).setDisable(false);
        	dealerImageViewList.get(j).setOpacity(1.0);
        	dealerImageViewList.get(j).setImage(dealerHand.get(j).getImage());
        }
        dealerCardTotalLabel.setText("DEALER CARD TOTAL: " + dealerCardTotal);
        
        //hide and disable buttons
        for (int k = 0; k < buttonList.size(); k++){
            buttonList.get(k).setDisable(true);
            buttonList.get(k).setOpacity(0.0);
        }
        
        //win conditions and pay outs
        //player and dealer don't bust
        if(playerCardTotal <= 21 && dealerCardTotal <= 21) {
        	//if player's score is higher
        	if(playerCardTotal > dealerCardTotal) {
        		winLoseLabel.setText("YOU WIN");
            	winLoseLabel.setDisable(false);
            	winLoseLabel.setOpacity(1.0);
            	
                player.winBet();
                cashLabel.setText("CASH: "+ player.showMoney());
                bet = 0;
                betLabel.setText("BET: " + bet);
        	}
        	
        	//if scores are equal
        	else if(playerCardTotal == dealerCardTotal) {
        		winLoseLabel.setText("PUSH");
            	winLoseLabel.setDisable(false);
            	winLoseLabel.setOpacity(1.0);
            	
                player.pushBet();
                cashLabel.setText("CASH: "+ player.showMoney());
                bet = 0;
                betLabel.setText("BET: " + bet);
        	}
        	
        	//if player's score is lower
        	else {
        		winLoseLabel.setText("YOU LOSE");
            	winLoseLabel.setDisable(false);
            	winLoseLabel.setOpacity(1.0);
            	
                cashLabel.setText("CASH: "+ player.showMoney());
                bet = 0;
                betLabel.setText("BET: " + bet);
        	}
        }
        
        //if player busts
        else if(playerCardTotal > 21 && dealerCardTotal <= 21) {
        	winLoseLabel.setText("YOU LOSE");
        	winLoseLabel.setDisable(false);
        	winLoseLabel.setOpacity(1.0);
        	
            cashLabel.setText("CASH: "+ player.showMoney());
            bet = 0;
            betLabel.setText("BET: " + bet);
        }
        
        //if player and dealer bust
        else if(playerCardTotal > 21 && dealerCardTotal > 21) {
        	winLoseLabel.setText("YOU LOSE");
        	winLoseLabel.setDisable(false);
        	winLoseLabel.setOpacity(1.0);
        	
            cashLabel.setText("CASH: "+ player.showMoney());
            bet = 0;
            betLabel.setText("BET: " + bet);
        }
        
        //if dealer busts
        else {
        	winLoseLabel.setText("YOU WIN");
        	winLoseLabel.setDisable(false);
        	winLoseLabel.setOpacity(1.0);
        
            player.winBet();
            cashLabel.setText("CASH: "+ player.showMoney());
            bet = 0;
            betLabel.setText("BET: " + bet);
        }
        
        playAgainButton.setDisable(false);
        playAgainButton.setOpacity(1.0);
        
    }

    //This method restarts the entire game no stored values though
    public void restartGame(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("startScene.fxml"));
        root = loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
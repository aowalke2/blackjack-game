package application;

import java.util.*;

public class Dealer {
    private List<Card> playerHand;
    private List<Card> dealerHand;

    //Empty constructor
    public Dealer() {
        playerHand = new ArrayList<>();
        dealerHand = new ArrayList<>();
    }

    //Creating dealer and player hand and dealing the cards
    public void deal(Deck deck){
        playerHand.add(deck.drawCard());
        dealerHand.add(deck.drawCard());
        playerHand.add(deck.drawCard());
        dealerHand.add(deck.drawCard());
    }

    //get dealer's hand
    public List<Card> getDealerHand(){
        return dealerHand;
    }

    //get player's hand
    public List<Card> getPlayerHand(){
        return playerHand;
    }

    //Shows contents of any hand. for troubleshooting
    public String showHand(List<Card> hand) {
        List<String>handString = new ArrayList<String>();
        for (Card c : hand) {
            handString.add(c.getFullName());
        }
        return handString.toString();
    }

    //The hit method: take another card
    public void hit(Deck deck, List<Card> hand) {
    	hand.add(deck.drawCard());
    }
}

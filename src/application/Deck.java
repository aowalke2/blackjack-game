package application;

import java.util.*;

public class Deck {
    //BlackjackGUI.Deck variables
    private List<String> suits;
    private List<String> names;
    private List<Card> cards;
    private int size;

    //Constructor
    public Deck() {
        //Adding the card suits to the suits list
        suits = new ArrayList<>();
        suits.add("spades");
        suits.add("hearts");
        suits.add("clubs");
        suits.add("diamonds");

        //Adding the card names to the names list
        names = new ArrayList<>();
        for (int i = 2; i <= 10; i++) {
            names.add(String.valueOf(i));
        }
        names.add("jack");
        names.add("queen");
        names.add("king");
        names.add("ace");

        //Creating the deck
        cards = new ArrayList<>();
        for (String suit : suits) {
            for (String name : names) {
                Card card = new Card(suit, name);
                cards.add(card);
                size++;
            }
        }
        
        //Adding images to cards (need to make this generic)
        for(Card c : cards){
            c.setImage(c.getImageName());   
        }
        //Shuffle the deck
        Collections.shuffle(cards);
    }

    //Get deck size.
    public int size() {
        return size;
    }

    //Draw a card from the deck
    public Card drawCard() {
        Card card = cards.get(0);
        cards.remove(0);
        return card;
    }

    //toString method()
    public String toString() {
        List<String> deck = new ArrayList<String>();
        for (Card c : cards) {
            deck.add(c.getFullName());
        }
        return deck.toString();
    }
}
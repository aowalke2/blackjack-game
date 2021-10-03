package application;

import javafx.scene.image.Image;

public class Card {
    //The three parameters of a card
    private String suit;
    private String name;
    private int value;
    private Image cardImage;

    //constructor
    public Card(String suit, String name) {
        //Storing a value in the card parameters
        this.suit = suit;
        this.name = name;

        //Using logic to assign the value. Will need to make sure the strings are right when creating the cards
        if (name.equals("king") || name.equals("queen") || name.equals("jack")) {
            value = 10;
        } else if (name.equals("ace")) {
            value = 11;
        } else {
            value = Integer.parseInt(name);
        }
    }

    //Get card suit
    public String getSuit() {
        return suit;
    }

    //get card name
    public String getName() {
        return name;
    }
    
    public String getFullName() {
        return name + "_of_" + suit;
    }

    //set card image
    public void setImage(String cardFileName) {
        cardImage = new Image(getClass().getResourceAsStream(cardFileName));
    }

    //get card image
    public Image getImage() {
        return cardImage;
    }

    //Get full card name
    public String getImageName() {
        return "CardImages/"+ name + "_of_" + suit+".png";
    }

    //get card value
    public int getValue() {
        return value;
    }
}

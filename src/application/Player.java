package application;

public class Player {
    //player attributes or variables
    private String name;
    private double money;
    private double bet;

    //Empty constructor
    public Player() {
        this.money = 500;
    }

    //constructor
    public Player(double money) {
        this.money = money;
    }

    //Set player name
    public void setName(String name){
        this.name = name;
    }

    //betting method
    //may just want to add the logic for the bet in the main program
    public void makeBet(double bet) {
        this.bet = bet;
        money -= bet;
    }
    
    public void winBet() {
    	money += 2*bet;
    }
    
    public void blackjackBet() {
    	money += 2.5*bet;
    }
    
    public void pushBet() {
    	money += bet;
    }
    
    public void insureBet(double insuranceBet) {
    	money += 2*insuranceBet;
    }

    //Shows the player's bet
    public double showBet() {
        return bet;
    }

    //Shows the player's money
    public double showMoney() {
        return money;
    }

    //Get players name
    public String getName(){
        return name;
    }
}


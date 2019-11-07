package ServerModel;

import ServerController.Constants;
import javafx.beans.Observable;

import javax.swing.*;

public class Player implements Constants {

    private String username;
    private String password;
    private Hand hand;
    private double balance;
    private double bet;
    private boolean inGame;
    private boolean played;

    public Player(String username, String password, int balance){
        this.username = username;
        this.password = password;
        this.balance = balance;
        hand = new Hand();
        bet = 0;
        inGame = false;
        played = false;
    }

    public Player(){
        this.username = OBSERVER;
        this.password = OBSERVER_PASSWORD;
        this.balance = 0;
    }

    public boolean isObserver(){
        if(username.equals(OBSERVER))
            return true;
        return false;
    }

    public boolean isCardCount(int i){
        if(hand.getCards().size() == i)
            return true;
        return false;
    }

    public boolean isDealer(){
        if(username.equals("dealer")){
            return true;
        }
        return false;
    }

    public boolean isInGame(){
        return inGame;
    }

    public void bench(){
        inGame = false;
        bet = 0;
        hand.reset();
    }

    public void resetBet(){
        bet = 0;
    }

    public void addBalance(double winning){
        balance += winning;
    }

    public void decreaseBalance(double loss){
        balance -= loss;
    }

    public void makeBet(double bet){
        this.bet += bet;
        decreaseBalance(bet);
    }

    public String getName() {
        return username;
    }

    public Hand getHand() {
        return hand;
    }

    public void setName(String name) {
        this.username = name;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public double getBet() {
        return bet;
    }

    public String toString(){
        String result = "";
        result += username + ": ";

        if(hand.getCards().size() == 0)
            return result;

        Card card;
        for(int i = 0; i < hand.getCards().size(); i++){
            card = hand.getCards().get(i);
            result += card.toString();
        }

        if(hand.getCards().size() > 0) {
            result += " Value: " + hand.getValue();
            result += " Bet: " + bet;
        }

        return result;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }
}

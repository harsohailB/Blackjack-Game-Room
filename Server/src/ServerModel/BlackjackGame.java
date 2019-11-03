package ServerModel;

import java.util.ArrayList;

public class BlackjackGame {

    private ArrayList<Player> players;
    private int turn;
    private Deck deck;

    public BlackjackGame(){
        players = new ArrayList<>();
        players.add(new Player("dealer", "dealer", Integer.MAX_VALUE));
        deck = new Deck();
        turn = 1;
    }

    public void addPlayer(Player p){
        players.add(new Player(p.getName(), p.getPassword(), p.getBalance()));
    }

    public void dealCardToPlayer(Player p, boolean visibility){
        Card randomCard = deck.getRandomCard();
        randomCard.setVisibility(visibility);
        p.getHand().addCard(randomCard);
    }

    public void advanceTurn(){
        if(turn == players.size() - 1){
            turn = 0;
        }else{
            turn++;
        }
    }

    public Player getTurnPlayer(){
        return players.get(turn);
    }

    public void takeStartingBet(int bet){
        Player p;
        int newBalance, currBalance;

        for(int i = 0; i < players.size(); i++){
            p = players.get(i);
            currBalance = p.getBalance();
            newBalance = currBalance - bet;
            p.setBalance(newBalance);
            p.setBet(bet);
        }
    }

    public boolean isReady(){
        // TODO Change party size to 5
        if(players.size() == 3){
            return true;
        }else{
            return false;
        }
    }

    public boolean isGameInPlay(){
        return true;
//        if(players.get(0).getHand().getHandValue() < 17)
//            return true;
//        return false;
    }

    // Getters and Setters

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Deck getDeck() {
        return deck;
    }

    public int getTurn() {
        return turn;
    }
}

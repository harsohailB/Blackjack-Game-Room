package ServerModel;

import java.util.ArrayList;

public class BlackjackGame {

    // Players participating in the game
    private ArrayList<Player> players;

    // Index of players list ^ to indicate player's turn
    private int turn;

    private Deck deck;

    public BlackjackGame(){
        players = new ArrayList<>();
        players.add(new Player("dealer", "dealer", Integer.MAX_VALUE));
        deck = new Deck();
        turn = 1;
    }

    // Adds new player to game
    public void addPlayer(Player p){
        players.add(new Player(p.getName(), p.getPassword(), p.getBalance()));
    }

    // Deals a card to player
    public void dealCardToPlayer(Player p, boolean visibility){
        Card randomCard = deck.getRandomCard();
        randomCard.setVisibility(visibility);
        p.getHand().addCard(randomCard);
    }

    // Advances turn to next player
    public void advanceTurn(){
        if(turn == players.size() - 1){
            turn = 0;
        }else{
            turn++;
        }
    }

    // Return player whose turn it is
    public Player getTurnPlayer(){
        return players.get(turn);
    }

    // Charges starting bets
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

    // Check if game is ready to start with party size
    public boolean isReady(){
        // TODO Change party size to 5
        if(players.size() == 3){
            return true;
        }else{
            return false;
        }
    }

    // Return game status
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

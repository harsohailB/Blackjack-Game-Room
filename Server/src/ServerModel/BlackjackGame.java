package ServerModel;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * This class controls and updates the black jack game
 */
public class BlackjackGame {

    // Players participating in the game
    private ArrayList<Player> players;

    // Index of players list ^ to indicate player's turn
    private int turn;

    // Deck containing cards
    private Deck deck;

    // Constructor
    public BlackjackGame(){
        players = new ArrayList<>();
<<<<<<< HEAD
        for(int i = 0; i < 4; i++){
            players.add(new Player(null));
        }
=======
        players.add(new Player("dealer", "dealer", Integer.MAX_VALUE));
>>>>>>> 2be4464dc1ac2bf761e8ba90cd8fb574fdde0e39
        deck = new Deck();
        turn = 1;
    }

    // Adds new player to game
    public void addPlayer(Player p){
        players.add(p);
        p.setInGame(true);
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

    // Hits player by dealing a card
    public void hitPlayer(Player p){
        dealCardToPlayer(p, true);
    }

    // Double player by hitting them and doubling their bet
    public void doublePlayer(Player p){
        dealCardToPlayer(p, true);
        p.makeBet(10);
    }

    // Return player whose turn it is
    public Player getTurnPlayer(){
        return players.get(turn);
    }

    // Charges starting bets
    public void takeStartingBet(int bet){
        Player p;

        for(int i = 0; i < players.size(); i++){
            p = players.get(i);
            p.makeBet(bet);
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

    // Resets table for a new game
    public void resetTable(){
        deck = new Deck();
        for(Player player: players){
            player.getHand().clear();
            player.resetBet();
            player.setInGame(true);
        }
    }

    // Pays players who got 21 on first round
    public void payNaturals(){
        Player p;
        for(int i = 1; i < players.size(); i++){
            p = players.get(i);
            if(p.getHand().hasBlackjack()){
                payPlayer(p, p.getBet() * 1.5);
                p.bench();
            }
        }
    }

<<<<<<< HEAD
=======
    // Kick player out of game if they go over 21
    public boolean kickIfBusts(Player p){
        if(p.isInGame() && p.getHand().hasBust()){
            p.bench();
            return true;
        }
        return false;
    }

    // Dealer pays player
    public void payPlayer(Player player, double payment){
        Player dealer = players.get(0);
        dealer.decreaseBalance(payment);
        player.addBalance(payment);
    }

    // Dealer goes over 17 and busts
    public ArrayList<Player> dealerBust(){
        ArrayList<Player> playersInGame = new ArrayList<>();
        Player dealer = players.get(0);
        Player player;

        if(dealer.getHand().getValue() > 21){
            for(int i = 1; i < players.size(); i++){
                player = players.get(i);
                if(player.isInGame()){
                    playersInGame.add(player);
                    player.addBalance(player.getBet() * 2);
                }
            }
        }

        return playersInGame;
    }

    // Players that lose pay dealer
    public ArrayList<Player> chargeLosers(){
        ArrayList<Player> loserPlayers = new ArrayList<>();
        Player player;

        for(int i = 1; i < players.size(); i++){
            player = players.get(i);
            if(player.isInGame()) {
                if (!didPlayerWin(player)) {
                    loserPlayers.add(player);
                }
            }
        }

        return loserPlayers;
    }

    // Checks if player won against dealer
    public boolean didPlayerWin(Player player){
        Player dealer = players.get(0);
        if(player.getHand().getValue() > dealer.getHand().getValue()){
            return true;
        }
        return false;
    }

    // Dealer pays players who won
    public ArrayList<Player> payWinners(){
        ArrayList<Player> winnerPlayers = new ArrayList<>();
        Player player;

        for(int i = 1; i < players.size(); i++){
            player = players.get(i);
            if(player.isInGame()) {
                if (didPlayerWin(player)) {
                    player.addBalance(player.getBet() * 2);
                    winnerPlayers.add(player);
                }
            }
        }

        return winnerPlayers;
    }

>>>>>>> 2be4464dc1ac2bf761e8ba90cd8fb574fdde0e39
    // Getters and Setters

    public ArrayList<Player> getPlayers() {
        return players;
    }

<<<<<<< HEAD

=======
    public Deck getDeck() {
        return deck;
    }

    public int getTurn() {
        return turn;
    }
>>>>>>> 2be4464dc1ac2bf761e8ba90cd8fb574fdde0e39
}

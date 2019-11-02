package ServerModel;

import java.util.ArrayList;

public class BlackjackGame {

    private ArrayList<Player> players;
    private Deck deck;
    private boolean ready;

    public BlackjackGame(){
        players = new ArrayList<>();
        players.add(new Player("dealer", "dealer"));
        deck = new Deck();
    }

    public void addPlayer(Player p){
        players.add(new Player(p.getName(), p.getPassword(), p.getBalance()));
    }

    // Getters and Setters

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Deck getDeck() {
        return deck;
    }

    public boolean isReady(){
        // TODO Change party size to 5
        if(players.size() == 3){
            return true;
        }else{
            return false;
        }
    }
}

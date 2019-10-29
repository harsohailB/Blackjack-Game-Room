package ServerModel;

import java.util.ArrayList;

public class BlackjackGame {

    private ArrayList<Player> players;
    private Deck deck;

    public BlackjackGame(){
        players = new ArrayList<>();
        deck = new Deck();
    }

    public Deck getDeck() {
        return deck;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getPlayer(int i){
        return players.get(i);
    }
}

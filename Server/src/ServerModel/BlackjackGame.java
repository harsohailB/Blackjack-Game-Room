package ServerModel;

import java.util.ArrayList;

public class BlackjackGame {

    private ArrayList<Player> players;
    private Deck deck;

    public BlackjackGame(){
        players = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            players.add(new Player(null));
        }
        deck = new Deck();
    }

    public Deck getDeck() {
        return deck;
    }

    // Getters and Setters

    public ArrayList<Player> getPlayers() {
        return players;
    }


}

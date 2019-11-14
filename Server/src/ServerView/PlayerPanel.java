package ServerView;

import ServerModel.Card;

import javax.swing.*;
import java.util.ArrayList;

public class PlayerPanel {

    private JLabel playerName;
    private ArrayList<JLabel> playersCards;

    public PlayerPanel(){
        playerName = new JLabel("Player:");
        playersCards = new ArrayList<>();
    }

    public void setPlayerName(String username){
        playerName.setText(username);
    }

    public void addCard(Card card){
        playersCards.add(new JLabel(card.getImageIcon()));
    }

    // Getters and setters
    public JLabel getPlayerName() {
        return playerName;
    }

    public ArrayList<JLabel> getPlayersCards() {
        return playersCards;
    }


}

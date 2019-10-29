package ServerController;

import ServerModel.*;
import ServerView.DealerView;

import javax.swing.*;

public class DealerController {

    private DealerView dealerView;
    private ServerController serverController;

    private BlackjackGame blackjackGame;
    private Deck deck;

    public DealerController(DealerView dv, ServerController sc){
        dealerView = dv;
        serverController = sc;

        blackjackGame = new BlackjackGame();

        // TODO add action listeners
    }

    public void dealCard(Card card, Hand hand, JPanel panel){
        hand.getCards().add(card);
        dealerView.updateCard(card, panel);
    }

    public void addPlayer(String name){
        blackjackGame.getPlayers().add(new Player(name));
        dealerView.updateCard(blackjackGame.getDeck().getRandomCard(), dealerView.getPlayer1Panel());
    }

}

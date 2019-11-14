package ServerController;

import ServerModel.*;
import ServerView.DealerView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DealerController {

    private DealerView dealerView;
    private ServerController serverController;

    private BlackjackGame blackjackGame;
    private Deck deck;

    private PlayerAccounts playerAccounts;

    public DealerController(DealerView dv, ServerController sc){
        dealerView = dv;
        serverController = sc;

        blackjackGame = new BlackjackGame();
        playerAccounts = new PlayerAccounts();

        // TODO Remove after testing
        playerAccounts.addAccount("test", "123");

        // TODO Add action listeners for buttons
    }

    public void addPlayer(String name){
        for(int i = 0; i < blackjackGame.getPlayers().size(); i++){
            Player player = blackjackGame.getPlayers().get(i);
            if(player.getName() == null){
                player.setName(name);
                dealCardToPlayer(blackjackGame.getPlayers().get(i), blackjackGame.getDeck().getRandomCard());
                break;
            }
        }
        dealerView.updatePlayerSeats(blackjackGame.getPlayers());
    }

    public void dealCardToPlayer(Player player, Card card){
        player.getHand().addCard(card);
    }

    public boolean validatePlayerLogin(String username, String password){
        return playerAccounts.playerAccountExists(username, password);
    }

}

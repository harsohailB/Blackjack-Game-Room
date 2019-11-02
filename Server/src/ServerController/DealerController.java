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

    public void runGame(){
        dealCardToPlayer(blackjackGame.getPlayers().get(1), blackjackGame.getDeck().getRandomCard());
        serverController.updatePlayers();
        displayTable();
    }

    public void displayTable(){
        System.out.println(dealerView.displayTable(blackjackGame.getPlayers()));
    }

    public String getTable(){
        return dealerView.displayTable(blackjackGame.getPlayers());
    }

    public void addPlayer(String username, String password){
        if(validatePlayerLogin(username, password)){
            Player p = playerAccounts.getPlayer(username);
            blackjackGame.addPlayer(p);
        }
    }

    public void dealCardToPlayer(Player player, Card card){
        player.getHand().addCard(card);
    }

    public boolean validatePlayerLogin(String username, String password){
        return playerAccounts.verifyPlayer(username, password);
    }

    public BlackjackGame getBlackjackGame() {
        return blackjackGame;
    }

    public DealerView getDealerView() {
        return dealerView;
    }
}

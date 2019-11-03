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
    }

    public void runGame(){
        takeStartingBets(10);
        dealFirstRound();
        System.out.println(blackjackGame.isGameInPlay());
        while(blackjackGame.isGameInPlay()){
            dealNextPlayer();
        }
    }

    public void dealFirstRound(){
        Player turnPlayer = blackjackGame.getTurnPlayer();

        while(!turnPlayer.isCardCount(2)){
            String dealerInput = dealerView.promptDeal();

            if (dealerInput.equals("deal")) {
                if(turnPlayer.getName().equals("dealer")){
                    blackjackGame.dealCardToPlayer(turnPlayer, false);
                }
                blackjackGame.dealCardToPlayer(turnPlayer, true);
                blackjackGame.advanceTurn();
            }

            turnPlayer = blackjackGame.getTurnPlayer();

            serverController.updatePlayers();
            displayTable();
        }

        dealerView.displayMessage("First Round Done!");
    }

    public void dealNextPlayer() {
        Player turnPlayer = blackjackGame.getTurnPlayer();
        dealerView.displayMessage("Dealing Next Player: " + turnPlayer.getName());
        String playerResponse;

        if (!turnPlayer.getName().equals("dealer")) {
            serverController.sendToPlayer("turn", blackjackGame.getTurn());
            playerResponse = serverController.receiveFromPlayer(blackjackGame.getTurn());
            if(playerResponse.equals("hit")){
                blackjackGame.dealCardToPlayer(turnPlayer, true);
            }
        }else{
            blackjackGame.dealCardToPlayer(turnPlayer, true);
        }

        serverController.updatePlayers();
        displayTable();
        blackjackGame.advanceTurn();
    }

    public void takeStartingBets(int bet){
        blackjackGame.takeStartingBet(bet);
        serverController.sendToAllPlayers("Made default bet: 10");
    }

    public void displayTable(){
        System.out.println(dealerView.displayTable(blackjackGame.getPlayers()));
    }

    public String getTable(){
        return dealerView.displayTable(blackjackGame.getPlayers());
    }

    public void addPlayer(Player p){
        blackjackGame.addPlayer(p);
    }

    public Player validatePlayerLogin(String username, String password){
        return playerAccounts.verifyPlayer(username, password);
    }

    public BlackjackGame getBlackjackGame() {
        return blackjackGame;
    }

    public DealerView getDealerView() {
        return dealerView;
    }
}

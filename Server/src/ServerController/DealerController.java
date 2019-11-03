package ServerController;

import ServerModel.*;
import ServerView.DealerView;

import javax.swing.*;
import java.lang.reflect.Array;
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

        // TODO Predefined Accounts
        playerAccounts.addAccount("test", "123");
        playerAccounts.addAccount("testt","123");
    }

    public void runGame(){
        takeStartingBets(10);
        dealFirstRound();
        blackjackGame.payNaturals();
        dealSecondRound();
        distributeWinnings();
        endGame();
    }

    public void endGame(){
        serverController.updatePlayers();
        serverController.sendToAllPlayers("Ending Game: Dealer Bust");
    }

    public void dealFirstRound(){
        Player turnPlayer = blackjackGame.getTurnPlayer();

        while(!turnPlayer.isCardCount(2)){
            dealerView.promptDeal();

            if(turnPlayer.getName().equals("dealer") && turnPlayer.isCardCount(0)){
                blackjackGame.dealCardToPlayer(turnPlayer, false);
            }else {
                blackjackGame.dealCardToPlayer(turnPlayer, true);
            }
            blackjackGame.advanceTurn();

            turnPlayer = blackjackGame.getTurnPlayer();

            serverController.updatePlayers();
            displayTable();
        }

        dealerView.displayMessage("First Round Done!");
    }

    public void dealSecondRound() {
        int playersDealt = 0;
        int totalPlayers = blackjackGame.getPlayers().size();
        Player turnPlayer = blackjackGame.getTurnPlayer();;

        while(playersDealt < totalPlayers) {
            dealerView.displayMessage("Dealing Next Player: " + turnPlayer.getName());
            if (!turnPlayer.getName().equals("dealer") && turnPlayer.isInGame()) {
                playPlayerTurn(turnPlayer);
            } else {
                dealerTurn(turnPlayer);
            }

            blackjackGame.advanceTurn();
            playersDealt++;
            turnPlayer = blackjackGame.getTurnPlayer();
        }

        System.out.println("Second round done!");
    }

    public void dealerTurn(Player dealer){
        dealer.getHand().showHand();
        while(dealer.getHand().getValue() < 17) {
            dealerView.promptDeal();

            blackjackGame.dealCardToPlayer(dealer, true);

            serverController.sendToAllPlayers("Dealer's turn:");
            serverController.updatePlayers();
            displayTable();
        }
    }

    public void playPlayerTurn(Player turnPlayer){
        String playerResponse;

        do {
            serverController.sendToPlayer("turn", blackjackGame.getTurn());
            playerResponse = serverController.receiveFromPlayer(blackjackGame.getTurn());
            if (playerResponse.equals("hit")) {
                dealerView.displayMessage(turnPlayer.getName() + " hits:");
                blackjackGame.hitPlayer(turnPlayer);

                serverController.sendToAllPlayers(turnPlayer.getName() + " hits:");
                serverController.updatePlayers();
                displayTable();
                if(blackjackGame.kickIfBusts(turnPlayer)){
                    serverController.sendToPlayer("You lose...", blackjackGame.getTurn());
                }
            }
        }while(!playerResponse.equals("stand") && turnPlayer.isInGame());

        serverController.updatePlayers();
        displayTable();
        dealerView.displayMessage(turnPlayer.getName() + "stands");
    }

    public void distributeWinnings(){
        dealerView.displayMessage("Distributing Winnings and Collecting Losses");
        serverController.sendToAllPlayers("Distributing Winnings and Collecting Losses");

        Player p;

        ArrayList<Integer> playerIndices;
        playerIndices = blackjackGame.dealerBust();
        if(playerIndices.size() != 0){
            for(Integer i: playerIndices) {
                p = blackjackGame.getPlayers().get(i);
                serverController.sendToPlayer("Dealer Bust: You win!", i);
                serverController.sendToPlayer("Ending Balance: " + p.getBalance(), i);
            }
        }

        playerIndices = blackjackGame.payWinners();
        if(playerIndices.size() != 0){
            for(Integer i: playerIndices) {
                p = blackjackGame.getPlayers().get(i);
                serverController.sendToPlayer("You win!", i);
                serverController.sendToPlayer("Ending Balance: " + p.getBalance(), i);
            }
        }

        playerIndices = blackjackGame.chargeLosers();
        if(playerIndices.size() != 0){
            for(Integer i: playerIndices) {
                p = blackjackGame.getPlayers().get(i);
                serverController.sendToPlayer("You lose! Dealer Wins...", i);
                serverController.sendToPlayer("Ending Balance: " + p.getBalance(), i);
            }
        }
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

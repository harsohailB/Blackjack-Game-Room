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
            String dealerInput = dealerView.promptDeal();

            if (dealerInput.equals("deal")) {
                if(turnPlayer.getName().equals("dealer") && turnPlayer.isCardCount(0)){
                    blackjackGame.dealCardToPlayer(turnPlayer, false);
                }else {
                    blackjackGame.dealCardToPlayer(turnPlayer, true);
                }
                blackjackGame.advanceTurn();
            }

            turnPlayer = blackjackGame.getTurnPlayer();

            serverController.updatePlayers();
            displayTable();
        }

        dealerView.displayMessage("First Round Done!");
    }

    public void dealSecondRound() {
        Player turnPlayer = blackjackGame.getTurnPlayer();;

        while(turnPlayer.isCardCount(2)) {
            dealerView.displayMessage("Dealing Next Player: " + turnPlayer.getName());
            if (!turnPlayer.getName().equals("dealer") && turnPlayer.isInGame()) {
                playPlayerTurn(turnPlayer);
            } else {
                dealerTurn(turnPlayer);
            }

            blackjackGame.advanceTurn();
            turnPlayer = blackjackGame.getTurnPlayer();
        }

        System.out.println("Second round done!");
    }

    public void dealerTurn(Player dealer){
        dealer.getHand().showHand();
        while(dealer.getHand().getValue() < 17) {
            String dealerInput = dealerView.promptDeal();

            if (dealerInput.equals("deal"))
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
        Player p;
        Player dealer = blackjackGame.getPlayers().get(0);
        for(int i = 1; i < blackjackGame.getPlayers().size(); i++){
            p = blackjackGame.getPlayers().get(i);
            if(p.isInGame()){
                if(dealer.getHand().getValue() > 21){
                    p.addBalance(p.getBet() * 2);
                    serverController.sendToPlayer("You win!", i);
                }else{
                    if(p.getHand().getValue() > dealer.getHand().getValue()){
                        p.addBalance(p.getBet() * 2);
                    }else{
                        serverController.sendToPlayer("You lose...", i);
                    }
                }
            }
            serverController.sendToPlayer("Ending Balance: " + p.getBalance(),i);
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

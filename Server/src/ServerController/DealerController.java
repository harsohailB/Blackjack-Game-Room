package ServerController;

import ServerModel.*;
import ServerView.DealerView;

<<<<<<< HEAD
import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
=======
import java.nio.file.LinkPermission;
import java.util.ArrayList;
>>>>>>> 2be4464dc1ac2bf761e8ba90cd8fb574fdde0e39

/**
 * This class acts as bridge between the blackjack game to communication with the client
 * The dealer contorller is responsible for running the game and updating the blackjack
 * table while displaying to the DealerView and outputting to all players
 */
public class DealerController implements Constants {

    private DealerView dealerView;
    private ServerController serverController;

    // Blackjack Game
    private BlackjackGame blackjackGame;
    private Deck deck;

<<<<<<< HEAD
    private PlayerAccounts playerAccounts;

=======
    // Saved player Accounts
    private PlayerAccounts playerAccounts;

    // Constructor
>>>>>>> 2be4464dc1ac2bf761e8ba90cd8fb574fdde0e39
    public DealerController(DealerView dv, ServerController sc){
        dealerView = dv;
        serverController = sc;

        blackjackGame = new BlackjackGame();
        playerAccounts = new PlayerAccounts();
<<<<<<< HEAD

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
=======

        // TODO Predefined Accounts
        playerAccounts.addAccount("test", "123");
        playerAccounts.addAccount("testt","123");
        playerAccounts.addAccount("blacklisted", "123");
    }

    // Runs game
    public void runGame(){
        takeStartingBets(10);
        dealFirstRound();
        blackjackGame.payNaturals();
        dealSecondRound();
        distributeWinnings();
    }

    // Deals first round
    public void dealFirstRound(){
        Player turnPlayer = blackjackGame.getTurnPlayer();
        int firstRoundCardCount = 2;

        while(!turnPlayer.isCardCount(firstRoundCardCount)){
            String[] input;
            do {
                input = dealerView.promptDealer();
                dealerDecision(input);
            }while(!input[0].equals("deal"));

            if(turnPlayer.isDealer() && turnPlayer.isCardCount(0)){
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

    // Deals second round
    public void dealSecondRound() {
        int playersDealt = 0;
        int totalPlayers = blackjackGame.getPlayers().size();
        Player turnPlayer = blackjackGame.getTurnPlayer();;

        while(playersDealt < totalPlayers) {
            dealerView.displayMessage("Dealing Next Player: " + turnPlayer.getName());
            if (!turnPlayer.isDealer() && turnPlayer.isInGame()) {
                playPlayerTurn(turnPlayer);
            }
            if(turnPlayer.isDealer()){
                dealerTurn(turnPlayer);
            }

            blackjackGame.advanceTurn();
            playersDealt++;
            turnPlayer = blackjackGame.getTurnPlayer();
        }

        System.out.println("Second round done!");
    }

    // Deals to dealer until over 17
    public void dealerTurn(Player dealer){
        serverController.sendToAllPlayers("Dealer's turn...");
        dealer.getHand().showHand();
        while(dealer.getHand().getValue() < 17) {
            String[] input;
            do {
                input = dealerView.promptDealer();
                dealerDecision(input);
            }while(!input[0].equals("deal"));

            blackjackGame.dealCardToPlayer(dealer, true);

            dealerView.displayMessage("Dealer's turn:");
            serverController.sendToAllPlayers("Dealer's turn:");
            serverController.updatePlayers();
            displayTable();
        }
    }

    // Plays player turn by getting their decision
    public void playPlayerTurn(Player turnPlayer){
        serverController.sendToAllPlayers(turnPlayer.getName() + "'s turn...");
        String playerResponse;

        do {
            serverController.sendToPlayer(TURN, turnPlayer);
            playerResponse = serverController.receiveFromPlayer(turnPlayer);

            if (playerResponse.equals(HIT)) {
                dealerView.displayMessage(turnPlayer.getName() + " hits:");
                blackjackGame.hitPlayer(turnPlayer);

                serverController.sendToAllPlayers(turnPlayer.getName() + " hits:");
                displayTable();
                serverController.updatePlayers();
                if(blackjackGame.kickIfBusts(turnPlayer)){
                    serverController.sendToPlayer("You lose...", turnPlayer);
                }
            }
        }while(playerResponse.equals(HIT) && turnPlayer.isInGame());

        if(playerResponse.equals(DOUBLE) && turnPlayer.isInGame()){
            dealerView.displayMessage(turnPlayer.getName() + " doubles:");
            blackjackGame.doublePlayer(turnPlayer);

            serverController.sendToAllPlayers(turnPlayer.getName() + " doubles:");
            displayTable();
            serverController.updatePlayers();
            if(blackjackGame.kickIfBusts(turnPlayer)){
                serverController.sendToPlayer("You lose...", turnPlayer);
            }
        }else{
            dealerView.displayMessage(turnPlayer.getName() + " stands:");
        }

        serverController.updatePlayers();
        displayTable();
>>>>>>> 2be4464dc1ac2bf761e8ba90cd8fb574fdde0e39
    }

    // Checks for winners and losers and distributes money accordingly
    public void distributeWinnings(){
        dealerView.displayMessage("Distributing Winnings and Collecting Losses");
        serverController.sendToAllPlayers("Distributing Winnings and Collecting Losses");

        Player p;

        // Notify players in game that dealer busted
        ArrayList<Player> players = blackjackGame.dealerBust();
        if(players.size() != 0){
            System.out.println("Dealer Bust...");
            for(Player player: players) {
                serverController.sendToPlayer("Dealer Bust: You win!", player);
                serverController.sendToPlayer("Ending Balance: " + player.getBalance(), player);
            }
            return;
        }

        players = blackjackGame.payWinners();
        if(players.size() != 0){
            for(Player player: players) {
                serverController.sendToPlayer("You win!", player);
                serverController.sendToPlayer("Ending Balance: " + player.getBalance(), player);
            }
        }

        players = blackjackGame.chargeLosers();
        if(players.size() != 0){
            for(Player player: players) {
                serverController.sendToPlayer("You lose! Dealer Wins...", player);
                serverController.sendToPlayer("Ending Balance: " + player.getBalance(), player);
            }
        }
    }

    // Sets starting bets of all players
    public void takeStartingBets(int bet){
        blackjackGame.takeStartingBet(bet);
        serverController.sendToAllPlayers("Made default bet: 10");
    }

    // Displays table to the dealer
    public void displayTable(){
        System.out.println(dealerView.getTableView(blackjackGame.getPlayers()));
    }

    // Gets a string table from blackjack game object
    public String getTable(){
        return dealerView.getTableView(blackjackGame.getPlayers());
    }

    // adds player to blackjack game
    public void addPlayer(Player p){
        blackjackGame.addPlayer(p);
    }

    // validates player login by verifying again player accounts
    public Player validatePlayerLogin(String username, String password){
        return playerAccounts.verifyPlayer(username, password);
    }

    public void dealerDecision(String[] input){
        String dealerInput = input[0];
        switch (dealerInput){
            case "deal":
            case "start":
                return;
            case "help":
                dealerView.displayMenu();
                break;
            case "/blacklist":
                blacklist(input[1]);
                break;
            case "leaderboard":
                System.out.println(playerAccounts.getLeaderboard());
                break;
            default:
                System.out.println("Invalid Command");
        }
    }

    public void blacklist(String playerName){
        Player player = playerAccounts.getPlayer(playerName);
        if(player == null){
            System.out.println("Player "+ playerName + " doesn't exist");
        }else{
            System.out.println(playerName + " blacklisted!");
            player.setBlacklisted(true);
        }
    }

    // Getters and Setters

    public BlackjackGame getBlackjackGame() {
        return blackjackGame;
    }

    public DealerView getDealerView() {
        return dealerView;
    }
}

package ServerController;

import ServerModel.*;
import ServerView.DealerView;

import java.util.ArrayList;

public class DealerController implements Constants {

    private DealerView dealerView;
    private ServerController serverController;

    private BlackjackGame blackjackGame;
    private Deck deck;

    private PlayerAccounts playerAccounts;

    private Thread recvMessage;

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
    }

    public void dealFirstRound(){
        Player turnPlayer = blackjackGame.getTurnPlayer();
        int firstRoundCardCount = 2;

        while(!turnPlayer.isCardCount(firstRoundCardCount)){
            dealerView.promptDeal();

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
            serverController.sendToPlayer(TURN, turnPlayer);
            playerResponse = serverController.receiveFromPlayer(turnPlayer);
            if (playerResponse.equals(HIT)) {
                dealerView.displayMessage(turnPlayer.getName() + " hits:");
                blackjackGame.hitPlayer(turnPlayer);

                serverController.sendToAllPlayers(turnPlayer.getName() + " hits:");
                serverController.updatePlayers();
                displayTable();
                if(blackjackGame.kickIfBusts(turnPlayer)){
                    serverController.sendToPlayer("You lose...", turnPlayer);
                }
            }
        }while(!playerResponse.equals(STAND) && turnPlayer.isInGame());

        serverController.updatePlayers();
        displayTable();
        dealerView.displayMessage(turnPlayer.getName() + " stands:");
    }

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

    public void takeStartingBets(int bet){
        blackjackGame.takeStartingBet(bet);
        serverController.sendToAllPlayers("Made default bet: 10");
    }

    public void displayTable(){
        System.out.println(dealerView.getTableView(blackjackGame.getPlayers()));
    }

    public String getTable(){
        return dealerView.getTableView(blackjackGame.getPlayers());
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

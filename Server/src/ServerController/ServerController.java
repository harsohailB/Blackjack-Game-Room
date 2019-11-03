package ServerController;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ServerView.*;

/**
 * This class manages a thread pool and holds
 * a ServerCommunicationController. A new ServerCommunicationController
 * object is delegated to a new thread as well as a new client
 */

public class ServerController {

    // Server Socket
    private static final int PORT = 9000;
    private ServerSocket serverSocket;

    // Thread Pool
    private ExecutorService pool;

    // Holds Dealer Model and Dealer View
    private DealerController dealerController;

    // Players
    private ArrayList<ServerCommunicationController> playerControllers;

    // Constructor to construct ServerController objects
    public ServerController(){
        try{
            serverSocket = new ServerSocket(PORT);
            pool = Executors.newFixedThreadPool(10);

            DealerView dealerView = new DealerView();
            dealerController = new DealerController(dealerView, this);

            playerControllers = new ArrayList<>();

            System.out.println("Server is running");
            printIPInfo();
            System.out.println("********");
        }catch (IOException e){
            System.out.println("ServerController: Create a new socket error");
            e.printStackTrace();
        }
    }

    // Main function which creates a server controller object
    public static void main(String[] args){
        ServerController myServer = new ServerController();
        myServer.communicateWithClient();
    }

    // Forever while loop which waits for client connections
    // and creates a new ServerCommunicationController object
    // upon connection. Each ServerCommunicationController is
    // given to a new thread to run
    public void communicateWithClient(){
        try{
            while(true){
                ServerCommunicationController scc = new ServerCommunicationController(serverSocket.accept(), this);
                playerControllers.add(scc);
                System.out.println("New Client Connected");
                pool.execute(scc);
            }
        }catch (IOException e){
            System.out.println("ServerController: CommunicateWithClient() Error");
            e.printStackTrace();
        }
    }

    // Prints IP Info of the server
    public void printIPInfo(){
        InetAddress ip;
        try{
            ip = InetAddress.getLocalHost();
            System.out.println("Your current IP address: " + ip);
        }catch (UnknownHostException e){
            System.out.println("IP Print Error");
            e.printStackTrace();
        }
    }

    // Updates players with the Blackjack Table
    public void updatePlayers(){
        String table = dealerController.getTable();
        sendTableToAllPlayers(table);
    }

    // Sends a string to all players
    public void sendToAllPlayers(String s){
        for(int i = 0; i < playerControllers.size(); i++){
            ServerCommunicationController playerController = playerControllers.get(i);
            playerController.send(s);
        }
    }

    // Sends a string to a specific player
    public void sendToPlayer(String s, int player){
        playerControllers.get(player - 1).send(s);
    }

    // Receives a string from a specific player
    public String receiveFromPlayer(int player){
        return playerControllers.get(player - 1).receive();
    }

    // Sends table to all players
    public void sendTableToAllPlayers(String s){
        for(int i = 0; i < playerControllers.size(); i++){
            ServerCommunicationController playerController = playerControllers.get(i);
            playerController.send("table");
            playerController.send(s);
            // TODO not updating with bets (player reference???)
            playerController.sendAccountBalance();
        }
    }

    // Notifies waiting players with game status
    public void notifyPlayersIfReady(){
        if(dealerController.getBlackjackGame().isReady()){
            sendToAllPlayers("ready");
        }else{
            sendToAllPlayers("Waiting for players");
        }
    }

    // Getters and Setters

    public DealerController getDealerController() {
        return dealerController;
    }
}

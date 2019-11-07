package ServerController;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ServerModel.Player;
import ServerView.*;

/**
 * This class manages a thread pool and holds
 * a ServerCommunicationController. A new ServerCommunicationController
 * object is delegated to a new thread as well as a new client
 */

public class ServerController implements Constants {

    // Server Socket
    private static final int TCP_PORT = 9000;
    private static final int UDP_PORT = 1234;
    private ServerSocket serverSocket;
    private DatagramSocket udpSocket;
    private byte[] receiveBuffer;

    // Thread Pool
    private ExecutorService pool;

    // Holds Dealer Model and Dealer View
    private DealerController dealerController;

    // Players
    private ArrayList<ServerCommunicationController> playerControllers;

    // Constructor to construct ServerController objects
    public ServerController(){
        try{
            serverSocket = new ServerSocket(TCP_PORT);
            udpSocket = new DatagramSocket(UDP_PORT);   // change port magic number
            receiveBuffer = new byte[65535];
            pool = Executors.newFixedThreadPool(10);

            DealerView dealerView = new DealerView();
            dealerController = new DealerController(dealerView, this);

            playerControllers = new ArrayList<>();

            System.out.println("Server is running");
            printIPInfo();
            System.out.println(BREAK_LINE);
        }catch (IOException e){
            System.out.println("ServerController: Create a new socket error");
            e.printStackTrace();
        }
    }

    // Main function which creates a server controller object
    public static void main(String[] args){
        ServerController myServer = new ServerController();
        Thread recvMessage = new Thread(() -> myServer.getChatMessages());
        recvMessage.start();
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

    public void getChatMessages(){
        DatagramPacket udpPacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
        String msg;
        while(true){
            try {
                udpSocket.receive(udpPacket);
                msg = NEW_CHAT_HEADER + "\n";
                msg += new String(udpPacket.getData(), udpPacket.getOffset(), udpPacket.getLength());
                msg += "\n" + BREAK_LINE;
                System.out.println(msg);
                for(ServerCommunicationController scc: playerControllers){
                    scc.send(msg);
                }
                receiveBuffer = new byte[65535];
            }catch (IOException e){
                e.printStackTrace();
            }
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
        sendToAllPlayers(table);
        sendToAllPlayers(ACCOUNT_BALANCE);
    }

    // Sends a string to all players
    public void sendToAllPlayers(String s){
        for(int i = 0; i < playerControllers.size(); i++){
            ServerCommunicationController playerController = playerControllers.get(i);
            if(s.equals(ACCOUNT_BALANCE)){
                playerController.sendAccountBalance();
            }else {
                playerController.send(s);
            }
        }
    }

    // Sends a string to a specific player
    public void sendToPlayer(String s, Player player){
        ServerCommunicationController playerController = getPlayerController(player);
        playerController.send(s);
    }

    // Receives a string from a specific player
    public String receiveFromPlayer(Player player){
        return getPlayerController(player).receive();
    }

    // Notifies waiting players with game status
    public void notifyPlayersIfReady(){
        if(dealerController.getBlackjackGame().isReady()){
            sendToAllPlayers(READY);
        }else{
            sendToAllPlayers(WAITING_FOR_PLAYERS);
        }
    }

    public void sendWelcomeMessage(Player player){
        ServerCommunicationController playerController = getPlayerController(player);
        playerController.send(WELCOME_MESSAGE);
    }

    public ServerCommunicationController getPlayerController(Player player){
        ServerCommunicationController scc;
        for(int i = 0; i < playerControllers.size(); i++){
            scc = playerControllers.get(i);
            if(scc.getPlayer() == player){
                return scc;
            }
        }
        return null;
    }

    // Getters and Setters

    public DealerController getDealerController() {
        return dealerController;
    }
}

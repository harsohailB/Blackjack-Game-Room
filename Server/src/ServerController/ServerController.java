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
    private static final int TCP_PORT = 6000;
    private static final int UDP_PORT = 1236;
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
        // Thread runs getChatMessages() server indefinetly to receive chat msgs through UDP
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
            promptStartGame();
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

    // Function run indefinetly with a thread to receive chat msgs through UDP
    public void getChatMessages(){
        DatagramPacket udpPacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
        while(true){
            try {
                udpSocket.receive(udpPacket);
                String content = new String(udpPacket.getData(), udpPacket.getOffset(), udpPacket.getLength());
                String[] contentArray = content.split(" ");
                String msg = createMessage(contentArray);
                System.out.println(msg);
                if(contentArray[0].equals("all")) {
                    for (ServerCommunicationController scc : playerControllers) {
                        scc.send(msg);
                    }
                }else{
                    String senderUsername = contentArray[1];
                    String recvUsername = contentArray[0];
                    if(verifyPrivateMessage(senderUsername, recvUsername)) {
                        sendToPlayer(msg, dealerController.getBlackjackGame().getPlayer(recvUsername));
                    }
                }
                receiveBuffer = new byte[65535];
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public boolean verifyPrivateMessage(String senderUsername, String recvUsername){
        Player recvPlayer = dealerController.getBlackjackGame().getPlayer(recvUsername);
        Player senderPlayer = dealerController.getBlackjackGame().getPlayer(senderUsername);
        if(recvPlayer == null){
            sendToPlayer("Player with username " + recvUsername + " doesn't exist... Message not delivered...", senderPlayer);
            return false;
        }

        return true;
    }

    public String createMessage(String[] contentArray){
        String message = NEW_CHAT_HEADER + "\n";

        if(contentArray[0].equals("all")){
            for(int i = 1; i < contentArray.length; i++){
                message += contentArray[i] + " ";
            }
        }else{
            String username = contentArray[0];
            message += "PRIVATE : ";
            for(int i = 1; i < contentArray.length; i++){
                message += contentArray[i] + " ";
            }
        }

        message += "\n" + BREAK_LINE;
        return message;
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

    // Sends a welcome message to player that connected
    public void sendWelcomeMessage(Player player){
        ServerCommunicationController playerController = getPlayerController(player);
        playerController.send(WELCOME_MESSAGE);
    }

    // Return the player controller communicating with the player requested
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

    public void promptStartGame(){
        String[] input;
        do {
            input = dealerController.getDealerView().promptDealer();
            dealerController.dealerDecision(input);
        }while(!input[0].equals("start"));
        System.out.println("Starting server!");
    }

    // Getters and Setters

    public DealerController getDealerController() {
        return dealerController;
    }
}

package ServerController;

import ServerModel.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class is responsible for communicating with the client
 */

public class ServerCommunicationController implements Runnable{

    // Sockets
    private Socket aSocket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private ServerController serverController;

    // Player that this object is connected with
    private Player player;

    public ServerCommunicationController(Socket s, ServerController serverController){
        try{
            aSocket = s;
            setServerController(serverController);

            socketOut = new ObjectOutputStream(aSocket.getOutputStream());

            serverController.printIPInfo();
        }catch (IOException e){
            System.out.println("ServerCommController: Create ServerCommController Error");
            e.printStackTrace();
        }
    }

    // Threads in threadpool of server run this function
    @Override
    public void run(){
        createUniqueInputStream();
        verifyLogin();
        waitUntilReady();
        startGame();
        communicate();
    }

    public void communicate(){
        while(true){

        }
    }

    // Creates a unique input stream from the player
    // If this is not done, input stream from other players is used
    public void createUniqueInputStream() {
        try {
            socketIn = new ObjectInputStream(aSocket.getInputStream());
        } catch (IOException e) {
            System.out.println("Error creating server output stream");
            e.printStackTrace();
        }
    }

    // Receives string from player
    public String receive(){
        try {
            return (String)socketIn.readObject();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    // Association with server controller
    public void setServerController(ServerController serverController) {
        this.serverController = serverController;
    }

    // Verifies player login
    public void verifyLogin() {
        try {
            boolean verified = false;

            while (!verified) {
                String username = (String) socketIn.readObject();
                String password = (String) socketIn.readObject();

                player = serverController.getDealerController().validatePlayerLogin(username, password);
                if (player != null) {
                    send("verified");
                    System.out.println("Login Success!");
                    verified = true;

                    serverController.getDealerController().addPlayer(player);
                    serverController.getDealerController().displayTable();

                    serverController.updatePlayers();
                    serverController.notifyPlayersIfReady();
                    return;
                } else {
                    send("Invalid Username and Password");
                }

                socketOut.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // A while loop to wait until game is ready to start
    public void waitUntilReady(){
        while(!serverController.getDealerController().getBlackjackGame().isReady()){
            // wait until game is ready
        }
    }

    // Starts game
    public void startGame(){
        System.out.println("Game Starting");
        serverController.getDealerController().runGame();
    }

    // Sends an object to player
    public void send(Object o){
        try {
            socketOut.writeObject(o);
        }catch (IOException e){
            System.out.println("ServerCommController: send() error");
            e.printStackTrace();
        }
    }

    // Sends player account balance to player
    public void sendAccountBalance(){
        try {
            String accountBalance = "Account Balance: " + player.getBalance();
            socketOut.writeObject(accountBalance);
        }catch (IOException e){
            System.out.println("ServerCommController: send() error");
            e.printStackTrace();
        }
    }
}

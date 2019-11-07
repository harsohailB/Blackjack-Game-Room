package ServerController;

import ServerModel.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class is responsible for communicating with the client
 */

public class ServerCommunicationController implements Runnable, Constants {

    // Sockets
    private Socket aSocket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private ServerController serverController;

    // Player that this object is connected with
    private Player player;

    private boolean verified;

    public ServerCommunicationController(Socket s, ServerController serverController){
        try{
            aSocket = s;
            setServerController(serverController);

            socketOut = new ObjectOutputStream(aSocket.getOutputStream());

            serverController.printIPInfo();
            verified = false;
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
        while(true) {
            if(!player.isObserver()) {
                waitUntilReady();
                timer(5);
                startGame();
            }
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
            while (!isVerified()) {
                String username = (String) socketIn.readObject();
                String password = (String) socketIn.readObject();

                player = serverController.getDealerController().validatePlayerLogin(username, password);
                if (player != null) {
                    socketOut.writeObject(VERIFIED);
                    System.out.println("Login Success!");
                    verified = true;

                    serverController.getDealerController().addPlayer(player);
                    serverController.getDealerController().displayTable();

                    serverController.sendWelcomeMessage(player);

                    serverController.updatePlayers();
                    serverController.notifyPlayersIfReady();
                    return;
                }else if(isObserver(username, password)){
                    socketOut.writeObject(VERIFIED);
                    System.out.println("New Observer connected!");
                    player = new Player();
                    verified = true;
                } else {
                    socketOut.writeObject("Invalid Username and Password");
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
        String playersReady = "Players Ready!";
        System.out.println(playersReady);
        serverController.sendToAllPlayers(playersReady);
    }

    // Starts game
    public void startGame(){
        String gameStartingMsg = "Game Starting!";
        System.out.println(gameStartingMsg);
        serverController.sendToAllPlayers(gameStartingMsg);
        serverController.getDealerController().getBlackjackGame().resetTable();
        serverController.sendToAllPlayers(serverController.getDealerController().getTable());
        serverController.getDealerController().runGame();
    }

    // Sends an object to player
    public void send(Object o){
        if(!verified){
            return;
        }

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

    public void timer(int seconds){
        String timerMsg;
        for(int i = seconds; i > 0; i--){
            timerMsg = "Game starting in " + i + " seconds!";
            System.out.println(timerMsg);
            serverController.sendToAllPlayers(timerMsg);
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public boolean isObserver(String username, String password){
        System.out.println(username.equals(OBSERVER));
        System.out.println(password.equals(OBSERVER_PASSWORD));
        if(username.equals(OBSERVER) && password.equals(OBSERVER_PASSWORD)){
            return true;
        }
        return false;
    }

    public boolean isVerified(){
        return verified;
    }


    public Player getPlayer() {
        return player;
    }


}

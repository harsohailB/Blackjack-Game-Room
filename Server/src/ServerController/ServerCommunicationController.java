package ServerController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerCommunicationController implements Runnable{

    private Socket aSocket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private ServerController serverController;

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
            // TODO forever loop to listen to client request
        }
    }

    public String getPlayerName(){
        try {
            return (String) socketIn.readObject();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void createUniqueInputStream() {
        try {
            socketIn = new ObjectInputStream(aSocket.getInputStream());
        } catch (IOException e) {
            System.out.println("Error creating server output stream");
            e.printStackTrace();
        }
    }

    public void setServerController(ServerController serverController) {
        this.serverController = serverController;
    }

    public void verifyLogin() {
        try {
            boolean verified = false;

            while (!verified) {
                String username = (String) socketIn.readObject();
                String password = (String) socketIn.readObject();

                if (serverController.getDealerController().validatePlayerLogin(username, password)) {
                    send("verified");
                    System.out.println("Login Success!");
                    verified = true;

                    serverController.getDealerController().addPlayer(username, password);
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

    public void waitUntilReady(){
        while(!serverController.getDealerController().getBlackjackGame().isReady()){
            // wait until game is ready
        }
    }

    public void startGame(){
        System.out.println("Game Starting");
        serverController.getDealerController().runGame();
    }

    public void send(Object o){
        try {
            socketOut.writeObject(o);
        }catch (IOException e){
            System.out.println("ServerCommController: send() error");
            e.printStackTrace();
        }
    }
}

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
        communicate();
    }

    public void communicate(){
        while(true){
            // TODO forever loop to listen to client requests

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
                    socketOut.writeObject("verified");
                    System.out.println("Login Success!");
                    verified = true;
                    serverController.getDealerController().addPlayer(username); // change name to username after login
                    return;
                } else {
                    socketOut.writeObject("Invalid Username and Password");
                }

                socketOut.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

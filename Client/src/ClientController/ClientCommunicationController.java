package ClientController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import ClientView.*;

/**
 * This ClientCommunicationControlller class connects to the
 * server and is responsible for holding the model and the view.
 * It sends requests to the server using the actions of the player
 */

public class ClientCommunicationController {

    private ObjectOutputStream socketOut;
    private Socket aSocket;
    private ObjectInputStream socketIn;

    private LoginController loginController;
    private MainGUIController mainGUIController;

    public ClientCommunicationController(String serverName, int port){
        try{
            aSocket = new Socket(serverName, port);

            socketIn = new ObjectInputStream(aSocket.getInputStream());
            socketOut = new ObjectOutputStream(aSocket.getOutputStream());

            MainView mainView = new MainView();
            LoginView loginView = new LoginView(250, 200);

            loginController = new LoginController(loginView, this);
            mainGUIController = new MainGUIController(mainView, this);
        }catch (IOException e){
            System.out.println("Client Communication Controller constructor error");
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        ClientCommunicationController ccc = new ClientCommunicationController("localhost", 9000);
        ccc.communicate();
    }

    public void communicate(){
        displayMainView();
        while(true){

        }
    }

    public void displayMainView(){
        while(true) {
            System.out.println("waiting");
           if(loginController.isVerified())
               break;
        }
        mainGUIController.getMainView().display();
    }

    // Getters and Setters

    public ObjectOutputStream getSocketOut() {
        return socketOut;
    }

    public ObjectInputStream getSocketIn() {
        return socketIn;
    }
}

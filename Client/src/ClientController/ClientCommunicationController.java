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
            LoginView loginView = new LoginView();

            loginController = new LoginController(loginView, this);
            mainGUIController = new MainGUIController(mainView, this);
        }catch (IOException e){
            System.out.println("Client Communication Controller constructor error");
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        // TODO commented out for testing purposes
//        Scanner inputScanner = new Scanner(System.in);
//        System.out.println("Enter IP of Blackjack Room:");
//        String inputIP = inputScanner.nextLine();
//        System.out.println("Enter PORT of Blackjack Room:");
//        int inputPort = Integer.parseInt(inputScanner.nextLine());
//
//        ClientCommunicationController ccc = new ClientCommunicationController(inputIP, inputPort);

        ClientCommunicationController ccc = new ClientCommunicationController("localhost", 9000);
    }

}

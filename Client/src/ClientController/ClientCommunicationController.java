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

            LoginView loginView = new LoginView();
            MainView mainView = new MainView();

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
        loginController.loginListen();
        waitTillGameReady();

        try {
            while (true) {
                String input = (String) socketIn.readObject();
                switch (input){
                    case "table":
                        receiveTable();
                        break;
                    case "turn":
                        hitOrStand();
                        break;
                    default:
                        System.out.println(input);
                }
            }
        }catch (Exception e) {
            System.out.println("communicate() error");
            e.printStackTrace();
        }
    }

    public void waitTillGameReady(){
        try {
            while (true) {
                String input = (String) socketIn.readObject();
                switch (input) {
                    case "ready":
                        System.out.println("Game Starting");
                        return;
                    default:
                        System.out.println(input);
                }
            }
        } catch (Exception e){
            System.out.println("WaitTillGameReady() error");
            e.printStackTrace();
        }
    }

    public void receiveTable(){
        try {
            System.out.println((String)socketIn.readObject());
        } catch (Exception e){
            System.out.println("WaitTillGameReady() error");
            e.printStackTrace();
        }
    }

    public void hitOrStand(){
        try {
            String input = (String) loginController.getLoginView().promptHitOrStand();
            socketOut.writeObject(input);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // Getters and Setters

    public ObjectOutputStream getSocketOut() {
        return socketOut;
    }

    public ObjectInputStream getSocketIn() {
        return socketIn;
    }
}

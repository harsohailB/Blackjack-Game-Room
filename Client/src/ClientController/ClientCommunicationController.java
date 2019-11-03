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

    // Sockets
    private ObjectOutputStream socketOut;
    private Socket aSocket;
    private ObjectInputStream socketIn;

    // Controllers
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

    // Connects to blackjack server
    public static void main(String[] args){
        ClientCommunicationController ccc = new ClientCommunicationController("localhost", 9000);
        ccc.communicate();
    }

    // First: Login listen takes username from player and send to server for verification
    // Second: Player waits until game is ready to start
    // Finally: Forever while loop to listen and interact with server using switch statement
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

    // Receives updates from server while waiting for game to start
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

    // Receives table from server to display
    public void receiveTable(){
        try {
            System.out.println((String)socketIn.readObject());
        } catch (Exception e){
            System.out.println("WaitTillGameReady() error");
            e.printStackTrace();
        }
    }

    // Gives hit or stand decision to server
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

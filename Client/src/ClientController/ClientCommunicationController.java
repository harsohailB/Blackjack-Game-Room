package ClientController;

import ClientView.LoginView;
import ClientView.MainView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * This ClientCommunicationControlller class connects to the
 * server and is responsible for holding the model and the view.
 * It sends requests to the server using the actions of the player
 */

public class ClientCommunicationController extends Thread{

    // Sockets
    private ObjectOutputStream socketOut;
    private Socket aSocket;
    private ObjectInputStream socketIn;
    private DatagramSocket udpSocket;
    private byte[] udpBuffer;
    private InetAddress IP;
    private boolean turn;

    // Controllers
    private LoginController loginController;
    private MainGUIController mainGUIController;

    public ClientCommunicationController(String serverName, int port){
        try{
            aSocket = new Socket(serverName, port);

            socketIn = new ObjectInputStream(aSocket.getInputStream());
            socketOut = new ObjectOutputStream(aSocket.getOutputStream());

            udpSocket = new DatagramSocket();
            IP = InetAddress.getLocalHost();
            turn = false;

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
        Thread sendMessage = new Thread(() -> {
            try {
                sendMessagesToServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        sendMessage.start();

        try {
            while (true) {
                String input = (String) socketIn.readObject();
                switch (input){
                    case "turn":
                        System.out.println("Hit or Stand:");
                        turn = true;
                        break;
                    default:
                        System.out.println(input);
                        break;
                }
            }
        }catch (Exception e) {
            System.out.println("communicate() error");
            e.printStackTrace();
        }
    }

    public void welcomeAdvance(){
        mainGUIController.getMainView().promptPressEnter();
    }

    public void sendMessagesToServer() throws IOException{
        Scanner scanner = new Scanner(System.in);
        InetAddress ip = InetAddress.getLocalHost();
        String input = "";

        while(true){
            if(turn){
                while(!input.equals("hit") && !input.equals("stand")) {
                    input = mainGUIController.getMainView().promptHitOrStand();
                }
                socketOut.writeObject(input);
                turn = false;
            }else {
                System.out.println("Enter message with '/all':");
                input = scanner.nextLine();

                if(turn) {
                    continue;   // directed to "Hit or Stand" input
                }

                sendChatMessage(input);
            }
        }
    }

    public void sendChatMessage(String input){
        String[] inputArray = input.split(" ");

        try {
            if (inputArray[0].equals("/all")) {
                input = loginController.getUsername() + ": " + input.substring(5);
                udpBuffer = input.getBytes();
                DatagramPacket udpPacket = new DatagramPacket(udpBuffer, udpBuffer.length, IP, 1234);
                udpSocket.send(udpPacket);
                System.out.println("Message sent!");
            } else {
                System.out.println("Invalid Message format");
            }
        }catch (IOException e){
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

    // Getters and Setters

    public ObjectOutputStream getSocketOut() {
        return socketOut;
    }

    public ObjectInputStream getSocketIn() {
        return socketIn;
    }
}

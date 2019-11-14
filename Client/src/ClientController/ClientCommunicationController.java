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

<<<<<<< HEAD
            MainView mainView = new MainView();
            LoginView loginView = new LoginView(250, 200);
=======
            udpSocket = new DatagramSocket();
            IP = InetAddress.getLocalHost();
            turn = false;

            LoginView loginView = new LoginView();
            MainView mainView = new MainView();
>>>>>>> 2be4464dc1ac2bf761e8ba90cd8fb574fdde0e39

            loginController = new LoginController(loginView, this);
            mainGUIController = new MainGUIController(mainView, this);
        }catch (IOException e){
            System.out.println("Client Communication Controller constructor error");
            e.printStackTrace();
        }
    }

    // Connects to blackjack server
    public static void main(String[] args){
<<<<<<< HEAD
        ClientCommunicationController ccc = new ClientCommunicationController("localhost", 9000);
        ccc.communicate();
    }

    public void communicate(){
        displayMainView();
=======
        String ip = LoginView.promptIP();
        ClientCommunicationController ccc = new ClientCommunicationController(ip, 8000);
        ccc.communicate();
    }

    // First: Login listen takes username from player and send to server for verification
    // Second: Player waits until game is ready to start
    // Finally: Forever while loop to listen and interact with server using switch statement
    public void communicate(){
        loginController.loginListen();
        // This thread runs the sendMessagesToServer() function
        Thread sendMessage = new Thread(() -> {
            try {
                sendMessagesToServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        sendMessage.start();

        waitTillGameReady();

        try {
            while (true) {
                String input = (String) socketIn.readObject();
                // switch statement responsible for changing boolean to
                // manage the sendMessagesToServer() threaded function
                // for specific inputs
                switch (input){
                    case "turn":
                        System.out.println("Hit or Stand or Double:");
                        turn = true;
                        break;
                    case "raise":
                        String raise = (String) socketIn.readObject();
                        System.out.println("Call " + raise + " to hit or Stand");
                        turn = true;
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

    // This function is run on a thread beside the communicate function
    // to incorporate chat messages to be sent alongside player decisions
    // for the game
    public void sendMessagesToServer() throws IOException{
        Scanner scanner = new Scanner(System.in);
        InetAddress ip = InetAddress.getLocalHost();
        String input = "";

        // Forever while loop that either sends player decisions if requested
        // by server or prompts user for chat messages '/all'
>>>>>>> 2be4464dc1ac2bf761e8ba90cd8fb574fdde0e39
        while(true){
            if(turn){           // if player's turn then make hit or stand decision
                while(!input.equals("hit") && !input.equals("stand") && !input.equals("double")) {
                    input = mainGUIController.getMainView().promptMakeDecision();
                }
                socketOut.writeObject(input);
                turn = false;
            }else {             // otherwise, wait for chat input
                System.out.println("Enter message with '/all':");
                input = scanner.nextLine();

                if(turn) {      // if server requires decision
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
                DatagramPacket udpPacket = new DatagramPacket(udpBuffer, udpBuffer.length, IP, 1235);
                udpSocket.send(udpPacket);
                System.out.println("Message sent!");
            } else {
                System.out.println("Invalid Message format");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

<<<<<<< HEAD
    public void displayMainView(){
        while(true) {
            System.out.println("waiting");
           if(loginController.isVerified())
               break;
        }
        mainGUIController.getMainView().display();
=======
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
                        // Updated table when another player joins lobby
                        System.out.println(input);
                }
            }
        } catch (Exception e){
            System.out.println("WaitTillGameReady() error");
            e.printStackTrace();
        }
>>>>>>> 2be4464dc1ac2bf761e8ba90cd8fb574fdde0e39
    }

    // Getters and Setters

    public ObjectOutputStream getSocketOut() {
        return socketOut;
    }

    public ObjectInputStream getSocketIn() {
        return socketIn;
    }
}

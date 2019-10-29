package ServerController;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ServerView.*;

/**
 * This class manages a thread pool and holds
 * a ServerCommunicationController. A new ServerCommunicationController
 * object is delegated to a new thread as well as a new client
 */

public class ServerController {

    private static final int PORT = 9000;
    private ServerSocket serverSocket;
    private ExecutorService pool;

    private DealerController dealerController;

    // Players
    private ArrayList<ServerCommunicationController> playerControllers;


    public ServerController(){
        try{
            serverSocket = new ServerSocket(PORT);
            pool = Executors.newFixedThreadPool(10);

            DealerView dealerView = new DealerView();
            dealerController = new DealerController(dealerView, this);

            playerControllers = new ArrayList<>();

            System.out.println("Server is running");
            printIPInfo();
            System.out.println("********");
        }catch (IOException e){
            System.out.println("ServerController: Create a new socket error");
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        ServerController myServer = new ServerController();
        myServer.communicateWithClient();
    }

    public void communicateWithClient(){
        try{
            while(true){
                ServerCommunicationController scc = new ServerCommunicationController(serverSocket.accept(), this);
                playerControllers.add(scc);
                System.out.println("New Client Connected");
                pool.execute(scc);
            }
        }catch (IOException e){
            System.out.println("ServerController: CommunicateWithClient() Error");
            e.printStackTrace();
        }
    }

    public void printIPInfo(){
        InetAddress ip;
        try{
            ip = InetAddress.getLocalHost();
            System.out.println("Your current IP address: " + ip);
        }catch (UnknownHostException e){
            System.out.println("IP Print Error");
            e.printStackTrace();
        }
    }

    public DealerController getDealerController() {
        return dealerController;
    }
}

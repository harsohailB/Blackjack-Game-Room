package ClientController;

import ClientView.*;

import javax.swing.*;
import java.io.IOException;

public class LoginController extends GUIController{

    //MEMBER VARIABLES

    private LoginView loginView;
    private boolean verified;
    private String username;

    public LoginController(LoginView l, ClientCommunicationController ccc){
        super(ccc);
        loginView = l;
        verified = false;
    }

    /**
     * Action Listener implementation for Login Button
     */
    public void loginListen() {
        while (!isVerified()) {
            try {
                String username = loginView.promptUsername();
                String password = loginView.promptPassword();

                clientCommunicationController.getSocketOut().writeObject(username);
                clientCommunicationController.getSocketOut().writeObject(password);

                String verification = (String) clientCommunicationController.getSocketIn().readObject();
                if (verification.equals("verified")) {
                    verified = true;
                    System.out.println("User Logged In!");
                    this.username = username;
                } else {
                    System.out.println("Invalid Username or Password or user already logged in. Try again");
                    continue;
                }

                if(!username.equals("observer"))
                    verified = verifyPing();

                clientCommunicationController.getSocketOut().flush();
            } catch (Exception f) {
                f.printStackTrace();
            }
        }
    }

    public boolean verifyPing(){
        try {
            String serverReq;
            do {
                serverReq = (String)clientCommunicationController.getSocketIn().readObject();
            }while(!serverReq.equals("ping"));

            clientCommunicationController.getSocketOut().writeObject("check");

            String serverResponse = (String)clientCommunicationController.getSocketIn().readObject();
            if(serverResponse.equals("passed")){
                System.out.println("Ping test passed!");
                return true;
            }else{
                System.out.println("Ping test failed... Try again...");
            }
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return false;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public boolean isVerified() {
        return verified;
    }

    public boolean isObserver(){
        if(username.equals("observer")){
            return true;
        }
        return false;
    }
}
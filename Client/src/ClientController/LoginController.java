package ClientController;

import ClientView.*;

import javax.swing.*;

public class LoginController extends GUIController{

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
                    System.out.println("Invalid Username or Password. Try again");
                }

                clientCommunicationController.getSocketOut().flush();
            } catch (Exception f) {
                f.printStackTrace();
            }
        }
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
}
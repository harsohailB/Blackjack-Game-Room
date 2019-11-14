package ClientController;

import ClientView.*;

import javax.swing.*;

public class LoginController extends GUIController{

    //MEMBER VARIABLES

    private LoginView loginView;
    private boolean verified;

    public LoginController(LoginView l, ClientCommunicationController ccc){
        super(ccc);
        loginView = l;
        verified = false;

        loginView.addLoginListener(e -> loginListen());
    }

    /**
     * Action Listener implementation for Login Button
     */
    public void loginListen(){
        try{
            String username = loginView.getUsernameField().getText();
            String password = loginView.getPasswordFeild().getText();

            clientCommunicationController.getSocketOut().writeObject(username);
            clientCommunicationController.getSocketOut().writeObject(password);

            String verification = (String)clientCommunicationController.getSocketIn().readObject();

            if(verification.equals("verified")) {
                loginView.setVisible(false);
                verified = true;
                System.out.println("User Logged In!");
            }else{
                JOptionPane.showMessageDialog(null, "Invalid User!");
            }

            clientCommunicationController.getSocketOut().flush();
        }catch(Exception f){
            f.printStackTrace();
        }
    }

    // Getters and setters
    public LoginView getLoginView() {
        return loginView;
    }

    public boolean isVerified() {
        return verified;
    }
}
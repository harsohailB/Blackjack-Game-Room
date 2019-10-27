package ClientController;

import ClientView.LoginView;

public class LoginController extends GUIController{

    private LoginView loginView;
    private boolean verified;

    public LoginController(LoginView l, ClientCommunicationController ccc){
        super(ccc);
        loginView = l;
        verified = false;

        //loginView.addLoginListener(e -> loginListen());
    }

}

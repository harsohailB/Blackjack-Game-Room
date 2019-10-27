package ClientController;

import javax.swing.*;

public abstract class GUIController extends JFrame {

    // holds socket connections to server
    protected ClientCommunicationController clientCommunicationController;

    public GUIController(ClientCommunicationController ccc){
        setClientCommunicationController(ccc);
    }

    public void setClientCommunicationController(ClientCommunicationController ccc){
        clientCommunicationController = ccc;
    }
}

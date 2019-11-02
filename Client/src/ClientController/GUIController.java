package ClientController;

public abstract class GUIController{

    // holds socket connections to server
    protected ClientCommunicationController clientCommunicationController;

    public GUIController(ClientCommunicationController ccc){
        setClientCommunicationController(ccc);
    }

    public void setClientCommunicationController(ClientCommunicationController ccc){
        clientCommunicationController = ccc;
    }
}

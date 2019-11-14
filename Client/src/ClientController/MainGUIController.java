package ClientController;

import ClientView.MainView;

public class MainGUIController extends GUIController {

    private MainView mainView;

    public MainGUIController(MainView v, ClientCommunicationController ccc){
        super(ccc);
        mainView = v;
    }

    // Getters and setters

    public MainView getMainView() {
        return mainView;
    }
}

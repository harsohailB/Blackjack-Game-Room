package ClientController;

import ClientView.MainView;
import com.sun.tools.javac.Main;

public class MainGUIController extends GUIController {

    private MainView mainView;

    public MainGUIController(MainView v, ClientCommunicationController ccc){
        super(ccc);
        mainView = v;

        // add action listeners
    }

    // Getters and setters

    public MainView getMainView() {
        return mainView;
    }
}

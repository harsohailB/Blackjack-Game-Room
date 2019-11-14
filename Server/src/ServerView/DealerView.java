package ServerView;

import ServerModel.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


//http://faculty.washington.edu/moishe/javademos/blackjack/BlackjackGUI.java

public class DealerView extends JFrame{

    // TODO add features accordingly
    JPanel topPanel, dealerPanel, playerCardPanel;

    JButton startGameButton;

    ArrayList<JPanel> seatPanels;

    public DealerView() {
        topPanel = new JPanel();
        playerCardPanel = new JPanel();
        startGameButton = new JButton();

        dealerPanel = new JPanel();
        dealerPanel.add(new JLabel("Dealer:"));

        seatPanels = new ArrayList<>();
        createSeatPanels();
        addSeatPanelsToGUI();

        topPanel.setLayout(new FlowLayout());
        startGameButton.setText("Start Game");

        topPanel.add(startGameButton);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(dealerPanel, BorderLayout.CENTER);
        add(playerCardPanel, BorderLayout.SOUTH);

        display();
    }

    public void display(){
        setSize(700, 250);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void updatePlayerSeats(ArrayList<Player> players){
        // update players
        for(int i = 0; i < seatPanels.size(); i++){
            seatPanels.get(i).removeAll();
        }

        for(int i = 0; i < players.size(); i++){
            seatPanels.get(i).add(new JLabel(players.get(i).getName()));
            for(int j = 0; j < players.get(i).getHand().getCards().size(); i++){
                seatPanels.get(i).add(new JLabel(players.get(i).getHand().getCards().get(j).getImageIcon()));
            }
        }
        revalidate();
    }

    // Player seat creation
    public void createSeatPanels(){
        for(int i = 0; i < 4; i++){
            seatPanels.add(new JPanel());
        }
    }

    public void addSeatPanelsToGUI(){
        for(int i = 0; i < seatPanels.size(); i++){
            playerCardPanel.add(seatPanels.get(i));
        }
    }

    // Getters and Setters

    public JPanel getDealerPanel() {
        return dealerPanel;
    }
}

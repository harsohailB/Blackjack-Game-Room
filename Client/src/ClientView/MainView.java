package ClientView;

<<<<<<< HEAD
import javax.swing.*;
import java.awt.*;

//http://faculty.washington.edu/moishe/javademos/blackjack/BlackjackGUI.java

public class MainView extends JFrame{

    // TODO add features accordingly
    JPanel topPanel, dealerPanel, playerCardPanel;

    JButton hitButton, dealButton, stayButton;

    JPanel player1Panel, player2Panel, player3Panel, player4Panel;


    public MainView() {
        topPanel = new JPanel();
        playerCardPanel = new JPanel();
        hitButton = new JButton();
        dealButton = new JButton();
        stayButton = new JButton();
        dealerPanel = new JPanel();
        player1Panel = new JPanel();
        player2Panel = new JPanel();
        player3Panel = new JPanel();
        player4Panel = new JPanel();


        topPanel.setLayout(new FlowLayout());
        dealButton.setText("Deal");
        hitButton.setText("Hit");
        stayButton.setText("Stay");

        dealerPanel.add(new JLabel("Dealer:"));
        player1Panel.add(new JLabel("Player 1:"));
        player2Panel.add(new JLabel("Player 2:"));
        player3Panel.add(new JLabel("Player 3:"));
        player4Panel.add(new JLabel("Player 4:"));

        topPanel.add(dealButton);
        topPanel.add(hitButton);
        topPanel.add(stayButton);
        playerCardPanel.add(player1Panel);
        playerCardPanel.add(player2Panel);
        playerCardPanel.add(player3Panel);
        playerCardPanel.add(player4Panel);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(dealerPanel, BorderLayout.CENTER);
        add(playerCardPanel, BorderLayout.SOUTH);
        setVisible(false);
    }

    public void display(){
        setSize(700, 250);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public JPanel getDealerPanel() {
        return dealerPanel;
    }

    public JPanel getPlayer1Panel() {
        return player1Panel;
    }

    public JPanel getPlayer2Panel() {
        return player2Panel;
    }

    public JPanel getPlayer3Panel() {
        return player3Panel;
    }

    public JPanel getPlayer4Panel() {
        return player4Panel;
    }
=======
import java.io.IOException;
import java.util.Scanner;

public class MainView{

    private Scanner scanner;

    public MainView(){
        scanner = new Scanner(System.in);
    }

    public String promptMakeDecision(){
        scanner = new Scanner(System.in);
        String input = null;

        do{
            System.out.println("Hit or Stand or Double:");
            input = scanner.nextLine();
            input = input.toLowerCase();
        }while(!input.equals("hit") && !input.equals("stand") && !input.equals("double"));

        return input;
    }

>>>>>>> 2be4464dc1ac2bf761e8ba90cd8fb574fdde0e39
}

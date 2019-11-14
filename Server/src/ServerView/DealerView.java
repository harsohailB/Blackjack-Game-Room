package ServerView;

import ServerController.Constants;
import ServerModel.*;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Dealer view class responsible for displaying to dealer
 */
public class DealerView implements Constants {

    // Scanner to get dealer input
    private Scanner scanner;

    // Constructor
    public DealerView(){
        scanner = new Scanner(System.in);
    }

    // Create table string
   public String getTableView(ArrayList<Player> players){
       String result = BREAK_LINE + "\n";
       for(int i = 0; i < players.size(); i++) {
           result += displayPlayer(players.get(i)) + "\n";
       }
       result += BREAK_LINE;
       return result;
   }

   // Displays player with their hand
   public String displayPlayer(Player player){
       return player.toString();
   }

   // Prompts dealer to deal
   public String[] promptDealer(){
       String[] input = null;

       System.out.println("Please give your input: (or Type 'help' for menu commands)");
       return scanner.nextLine().toLowerCase().split(" ");
   }

   // Displays a string object to dealer
   public void displayMessage(String s){
       System.out.println(s);
   }

   public void displayMenu(){
       System.out.println("'deal' to deal a card to the next player");
       System.out.println("'help' to display help menu");
       System.out.println("'/blacklist <player name>' to blacklist players from server");
       System.out.println("'start' to start server");
       System.out.println("'leaderboard' to view player stats in a leaderboard format");
   }

<<<<<<< HEAD

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
=======
>>>>>>> 2be4464dc1ac2bf761e8ba90cd8fb574fdde0e39
}

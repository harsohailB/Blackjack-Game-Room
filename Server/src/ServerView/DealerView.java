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

}

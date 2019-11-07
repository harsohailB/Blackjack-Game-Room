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
   public void promptDeal(){
       String input = null;

       do{
           System.out.println("Type 'deal' to deal card to next player:");
           input = scanner.nextLine();
           input = input.toLowerCase();
       }while(!input.equals("deal"));
   }

   // Displays a string object to dealer
   public void displayMessage(String s){
       System.out.println(s);
   }

}

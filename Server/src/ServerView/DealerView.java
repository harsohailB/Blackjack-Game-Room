package ServerView;

import ServerController.Constants;
import ServerModel.*;

import java.util.ArrayList;
import java.util.Scanner;

public class DealerView implements Constants {

    private Scanner scanner;

    public DealerView(){
        scanner = new Scanner(System.in);
    }

   public String getTableView(ArrayList<Player> players){
       String result = BREAK_LINE + "\n";
       for(int i = 0; i < players.size(); i++) {
           result += displayPlayer(players.get(i)) + "\n";
       }
       result += BREAK_LINE;
       return result;
   }

   public String displayPlayer(Player player){
       return player.toString();
   }

   public void promptDeal(){
       String input = null;

       do{
           System.out.println("Type 'deal' to deal card to next player:");
           input = scanner.nextLine();
           input = input.toLowerCase();
       }while(!input.equals("deal"));
   }

   public void displayMessage(String s){
       System.out.println(s);
   }

}

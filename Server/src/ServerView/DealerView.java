package ServerView;

import ServerModel.*;

import java.util.ArrayList;
import java.util.Scanner;

public class DealerView{

    private Scanner scanner;

    public DealerView(){
        scanner = new Scanner(System.in);
    }

   public String displayTable(ArrayList<Player> players){
       String result = showLine() + "\n";
       for(int i = 0; i < players.size(); i++) {
           result += displayPlayer(players.get(i)) + "\n";
       }
       result += showLine();
       return result;
   }

   public String showLine(){
       return "**************************";
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

package ServerView;

import ServerModel.*;

import java.util.ArrayList;

public class DealerView{

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

}

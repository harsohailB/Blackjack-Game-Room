package ServerModel;

<<<<<<< HEAD
import java.util.HashMap;

public class PlayerAccounts {

    HashMap<String, String> userTable;

    public PlayerAccounts(){
        userTable = new HashMap<>();
    }

    public void addAccount(String username, String password){
        userTable.put(username, password);
    }

    public boolean playerAccountExists(String username, String password){
        if(userTable.containsKey(username)){
            if(userTable.get(username).equals(password)){
                return true;
            }
        }
        return false;
    }

    public HashMap<String, String> getUserTable() {
        return userTable;
    }
=======
import ServerController.Constants;

import java.util.HashMap;
import java.util.Map;

public class PlayerAccounts implements Constants {

    HashMap<String, Player> players;

    public PlayerAccounts(){
        players = new HashMap<>();
    }

    public Player getPlayer(String username){
        return players.get(username);
    }

    public Player verifyPlayer(String username, String password){
        if(players.containsKey(username)){
            Player p = players.get(username);
            if(p.getPassword().equals(password)){
                return p;
            }
        }
        return null;
    }

    public void addAccount(String username, String password){
        players.put(username, new Player(username, password, 200));
    }

    public String getLeaderboard(){
        String leaderboard = BREAK_LINE + "\n";
        Player player;
        String key;

        for(Map.Entry<String, Player> entry: players.entrySet()){
            key = entry.getKey();
            player = players.get(key);
            leaderboard += player.getStats() + "\n";
        }

        leaderboard += BREAK_LINE;
        return leaderboard;
    }

>>>>>>> 2be4464dc1ac2bf761e8ba90cd8fb574fdde0e39
}

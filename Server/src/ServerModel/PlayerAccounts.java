package ServerModel;

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

}

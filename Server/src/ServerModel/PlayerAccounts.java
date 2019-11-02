package ServerModel;

import java.util.HashMap;

public class PlayerAccounts {

    HashMap<String, Player> players;

    public PlayerAccounts(){
        players = new HashMap<>();
        players.put("dealer", new Player("dealer", "dealer"));
    }

    public Player getPlayer(String username){
        return players.get(username);
    }

    public boolean verifyPlayer(String username, String password){
        if(players.containsKey(username)){
            Player p = players.get(username);
            if(p.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    public void addAccount(String username, String password){
        players.put(username, new Player(username, password));
    }

}

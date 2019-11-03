package ServerModel;

import java.util.HashMap;

public class PlayerAccounts {

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

}

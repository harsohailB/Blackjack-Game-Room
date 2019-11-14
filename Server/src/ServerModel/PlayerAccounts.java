package ServerModel;

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
}

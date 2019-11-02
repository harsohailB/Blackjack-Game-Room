package ServerModel;

public class Player {

    private String username;
    private String password;
    private Hand hand;
    private int balance;

    public Player(String username, String password){
        this.username = username;
        this.password = password;
        if(username.equals("dealer"))
            balance = 100000000;
        else
            balance = 200;
        hand = new Hand();
    }

    public Player(String username, String password, int balance){
        this.username = username;
        this.password = password;
        if(username.equals("dealer"))
            balance = 100000000;
        else
            balance = balance;
        hand = new Hand();
    }

    public String getName() {
        return username;
    }

    public Hand getHand() {
        return hand;
    }

    public void setName(String name) {
        this.username = name;
    }

    public String getPassword() {
        return password;
    }

    public int getBalance() {
        return balance;
    }

    public String toString(){
        String result = "";
        result += username + ": ";

        if(hand.getCards().size() == 0)
            return result;

        Card card;
        for(int i = 0; i < hand.getCards().size(); i++){
            card = hand.getCards().get(i);
            result += card.toString();
        }

        return result;
    }
}

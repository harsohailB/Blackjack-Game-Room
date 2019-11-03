package ServerModel;

public class Player {

    private String username;
    private String password;
    private Hand hand;
    private int balance;
    private int bet;

    public Player(String username, String password, int balance){
        this.username = username;
        this.password = password;
        this.balance = balance;
        hand = new Hand();
        bet = 0;
    }

    public boolean isCardCount(int i){
        if(hand.getCards().size() == i)
            return true;
        return false;
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

        if(hand.getCards().size() > 0) {
            result += " Value: " + hand.getHandValue();
            result += " Bet: " + bet;
        }

        return result;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }
}

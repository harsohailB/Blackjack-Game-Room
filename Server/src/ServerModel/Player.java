package ServerModel;

public class Player {

    private String name;
    private Hand hand;

    public Player(String name){
        this.name = name;
        hand = new Hand();
    }

    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }
}

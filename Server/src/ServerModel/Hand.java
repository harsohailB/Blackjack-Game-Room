package ServerModel;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Hand {

    private ArrayList<Card> cards;

    public Hand(){
        cards = new ArrayList<>();
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}

package ServerModel;

import java.util.ArrayList;

public class Hand implements CARD_INFO{

    private ArrayList<Card> cards;
    private int handValue_one;
    private int handValue_eleven;

    public Hand(){
        cards = new ArrayList<>();
        handValue_one = 0;
        handValue_eleven = 0;
    }

    public int getHandValue(){
        if(handValue_one <= handValue_eleven)
            return handValue_one;
        return handValue_eleven;
    }

    public void addCard(Card card){
        cards.add(card);
        if(card.getFace() == ACE){
            handValue_eleven += 11;
            handValue_one += 1;
            return;
        }else{
            handValue_one += card.getValue();
            handValue_eleven += card.getValue();
        }
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}

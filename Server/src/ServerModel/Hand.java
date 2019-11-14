package ServerModel;

import java.util.ArrayList;

public class Hand implements CardInfo {

    private ArrayList<Card> cards;

    public Hand(){
        cards = new ArrayList<>();
    }

    public void addCard(Card card){
        cards.add(card);
    }

<<<<<<< HEAD
=======
    public void showHand(){
        Card card;
        for(int i = 0; i < cards.size(); i++){
            card = cards.get(i);
            card.setVisibility(true);
        }
    }

    public int getValue(){
        Card card;
        int value_eleven = 0;
        int value_one = 0;
        for(int i = 0; i < cards.size(); i++){
            card = cards.get(i);
            if(card.isVisibility()){
                if(card.getFace() == ACE){
                    value_eleven += 11;
                    value_one += 1;
                }else{
                    value_eleven += card.getValue();
                    value_one += card.getValue();
                }
            }
        }

        if(value_one <= value_eleven){
            return value_one;
        }
        return value_eleven;
    }

    public boolean hasBlackjack(){
        if(getValue() == 21 || getValue() == 21){
            return true;
        }
        return false;
    }

    public boolean hasBust(){
        if(getValue() > 21 && getValue() > 21){
            return true;
        }
        return false;
    }

    public void reset(){
        cards.clear();
    }

    public void clear(){
        cards = new ArrayList<>();
    }

>>>>>>> 2be4464dc1ac2bf761e8ba90cd8fb574fdde0e39
    public ArrayList<Card> getCards() {
        return cards;
    }
}

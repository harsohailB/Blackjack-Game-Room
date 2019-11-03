package ServerModel;

import java.util.ArrayList;
import java.util.Random;

public class Deck implements CARD_INFO{

    private ArrayList<Card> cards;

    public Deck(){
        cards = new ArrayList<>();
        createCards();
    }

    public void createCards(){
        // Clubs
        cards.add(new Card(ACE, CLUBS, 11));
        cards.add(new Card(JACK, CLUBS, 10));
        cards.add(new Card(QUEEN, CLUBS, 10));
        cards.add(new Card(KING, CLUBS, 10));
        for(int i = 2; i <= 10; i++){
            String val = Integer.toString(i);
            cards.add(new Card(val, CLUBS, i));
        }

        // Diamonds
        cards.add(new Card(ACE, DIAMONDS, 11));
        cards.add(new Card(JACK, DIAMONDS, 10));
        cards.add(new Card(QUEEN, DIAMONDS, 10));
        cards.add(new Card(KING, DIAMONDS, 10));
        for(int i = 2; i <= 10; i++){
            String val = Integer.toString(i);
            cards.add(new Card(val, DIAMONDS, i));
        }

        // Hearts
        cards.add(new Card(ACE, HEARTS, 11));
        cards.add(new Card(JACK, HEARTS, 10));
        cards.add(new Card(QUEEN, HEARTS, 10));
        cards.add(new Card(KING, HEARTS, 10));
        for(int i = 2; i <= 10; i++){
            String val = Integer.toString(i);
            cards.add(new Card(val, HEARTS, i));
        }

        // Spades
        cards.add(new Card(ACE, SPADES, 11));
        cards.add(new Card(JACK, SPADES, 10));
        cards.add(new Card(QUEEN, SPADES, 10));
        cards.add(new Card(KING, SPADES, 10));
        for(int i = 2; i <= 10; i++){
            String val = Integer.toString(i);
            cards.add(new Card(val, SPADES, i));
        }
    }
    public Card getRandomCard(){
        Random rand = new Random();
        int n = rand.nextInt(52);
        return getCards().get(n);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}

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
        cards.add(new Card(ACE, CLUBS));
        cards.add(new Card(JACK, CLUBS));
        cards.add(new Card(QUEEN, CLUBS));
        cards.add(new Card(KING, CLUBS));
        for(int i = 2; i <= 10; i++){
            String val = Integer.toString(i);
            cards.add(new Card(val, CLUBS));
        }

        // Diamonds
        cards.add(new Card(ACE, DIAMONDS));
        cards.add(new Card(JACK, DIAMONDS));
        cards.add(new Card(QUEEN, DIAMONDS));
        cards.add(new Card(KING, DIAMONDS));
        for(int i = 2; i <= 10; i++){
            String val = Integer.toString(i);
            cards.add(new Card(val, DIAMONDS));
        }

        // Hearts
        cards.add(new Card(ACE, HEARTS));
        cards.add(new Card(JACK, HEARTS));
        cards.add(new Card(QUEEN, HEARTS));
        cards.add(new Card(KING, HEARTS));
        for(int i = 2; i <= 10; i++){
            String val = Integer.toString(i);
            cards.add(new Card(val, HEARTS));
        }

        // Spades
        cards.add(new Card(ACE, SPADES));
        cards.add(new Card(JACK, SPADES));
        cards.add(new Card(QUEEN, SPADES));
        cards.add(new Card(KING, SPADES));
        for(int i = 2; i <= 10; i++){
            String val = Integer.toString(i);
            cards.add(new Card(val, SPADES));
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

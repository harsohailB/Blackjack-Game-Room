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
        cards.add(new Card(ACE, CLUBS, generatePath(ACE, CLUBS)));
        cards.add(new Card(JACK, CLUBS, generatePath(JACK, CLUBS)));
        cards.add(new Card(QUEEN, CLUBS, generatePath(QUEEN, CLUBS)));
        cards.add(new Card(KING, CLUBS, generatePath(KING, CLUBS)));
        for(int i = 2; i <= 10; i++){
            String val = Integer.toString(i);
            cards.add(new Card(val, CLUBS, generatePath(val, CLUBS)));
        }

        // Diamonds
        cards.add(new Card(ACE, DIAMONDS, generatePath(ACE, DIAMONDS)));
        cards.add(new Card(JACK, DIAMONDS, generatePath(JACK, DIAMONDS)));
        cards.add(new Card(QUEEN, DIAMONDS, generatePath(QUEEN, DIAMONDS)));
        cards.add(new Card(KING, DIAMONDS, generatePath(KING, DIAMONDS)));
        for(int i = 2; i <= 10; i++){
            String val = Integer.toString(i);
            cards.add(new Card(val, DIAMONDS, generatePath(val, DIAMONDS)));
        }

        // Hearts
        cards.add(new Card(ACE, HEARTS, generatePath(ACE, HEARTS)));
        cards.add(new Card(JACK, HEARTS, generatePath(JACK, HEARTS)));
        cards.add(new Card(QUEEN, HEARTS, generatePath(QUEEN, HEARTS)));
        cards.add(new Card(KING, HEARTS, generatePath(KING, HEARTS)));
        for(int i = 2; i <= 10; i++){
            String val = Integer.toString(i);
            cards.add(new Card(val, HEARTS, generatePath(val, HEARTS)));
        }

        // Spades
        cards.add(new Card(ACE, SPADES, generatePath(ACE, SPADES)));
        cards.add(new Card(JACK, SPADES, generatePath(JACK, SPADES)));
        cards.add(new Card(QUEEN, SPADES, generatePath(QUEEN, SPADES)));
        cards.add(new Card(KING, SPADES, generatePath(KING, SPADES)));
        for(int i = 2; i <= 10; i++){
            String val = Integer.toString(i);
            cards.add(new Card(val, SPADES, generatePath(val, SPADES)));
        }
    }

    public String generatePath(String value, String suit){
        return "/utils/CardPics/" + value + "_of_" + suit + ".png";
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

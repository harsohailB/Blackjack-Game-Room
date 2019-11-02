package ServerModel;

import javax.swing.*;
import java.awt.*;

public class Card {

    private String face;
    private char suit;

    public Card(String value, char suit){
        this.face = value;
        this.suit = suit;
    }

    public String toString(){
        return " | " + face + " " + suit + " |";
    }
}

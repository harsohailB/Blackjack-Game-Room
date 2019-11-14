package ServerModel;

public class Card {

    private String face;
    private char suit;
    private int value;
    private boolean visible;

    public Card(String face, char suit, int value){
        this.face = face;
        this.suit = suit;
        this.value = value;
        this.visible = true;
    }

    public String getFace() {
        return face;
    }

    public int getValue() {
        return value;
    }

    public void setVisibility(boolean visibility) {
        this.visible = visibility;
    }

    public boolean isVisibility() {
        return visible;
    }

    public String toString(){
        if(!visible)
            return " |/|";
        return " | " + face + " " + suit + " |";
    }
}

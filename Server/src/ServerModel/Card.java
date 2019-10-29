package ServerModel;

import javax.swing.*;
import java.awt.*;

public class Card {

    private ImageIcon imageIcon;
    private String value;
    private String suit;

    public Card(String value, String suit, String path){
        this.value = value;
        this.suit = suit;

        imageIcon = new ImageIcon(getClass().getResource(path).getFile());
        Image image = imageIcon.getImage();
        Image resizedImage = image.getScaledInstance(50,80, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(resizedImage);
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }
}

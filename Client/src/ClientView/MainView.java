package ClientView;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class MainView{

    private Scanner scanner;

    public MainView(){
        scanner = new Scanner(System.in);
    }

    public String promptMakeDecision(){
        scanner = new Scanner(System.in);
        String input = null;

        do{
            System.out.println("Hit or Stand or Double:");
            input = scanner.nextLine();
            input = input.toLowerCase();
        }while(!input.equals("hit") && !input.equals("stand") && !input.equals("double"));

        return input;
    }
}

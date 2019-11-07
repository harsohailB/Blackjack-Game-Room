package ClientView;

import java.io.IOException;
import java.util.Scanner;

public class MainView{

    private Scanner scanner;

    public MainView(){
        scanner = new Scanner(System.in);
    }

    public String promptHitOrStand(){
        scanner = new Scanner(System.in);
        String input = null;

        do{
            System.out.println("Hit or Stand:");
            input = scanner.nextLine();
            input = input.toLowerCase();
        }while(!input.equals("hit") && !input.equals("stand"));

        return input;
    }

}

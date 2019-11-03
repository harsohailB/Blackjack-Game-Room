package ClientView;

import java.util.Scanner;

public class LoginView{

    private Scanner scanner;

    public LoginView(){
        scanner = new Scanner(System.in);
    }

    public String promptUsername(){
        System.out.println("Enter username:");
        String input = null;

        while (input == null) {
            input = scanner.nextLine();
        }

        return input;
    }

    public String promptPassword(){
        System.out.println("Enter password:");
        String input = null;

        while (input == null) {
            input = scanner.nextLine();
        }

        return input;
    }

    public String promptHitOrStand(){
        String input = null;

        do{
            System.out.println("Hit or Stand:");
            input = scanner.nextLine();
            input = input.toLowerCase();
        }while(!input.equals("hit") && !input.equals("stand"));

        return input;
    }

}
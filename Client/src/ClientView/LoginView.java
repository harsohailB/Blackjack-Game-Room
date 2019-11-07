package ClientView;

import java.util.Scanner;

public class LoginView{

    private Scanner scanner;

    public LoginView(){
        scanner = new Scanner(System.in);
    }

    public String promptUsername(){
        System.out.println("Enter username: ('observer' for observe)");
        String input = null;

        while (input == null) {
            input = scanner.nextLine();
        }

        return input;
    }

    public String promptPassword(){
        System.out.println("Enter password: ('123' to observe)");
        String input = null;

        while (input == null) {
            input = scanner.nextLine();
        }

        return input;
    }

    public static String promptIP(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter IP of Blackjack Game Room:");
        String input = null;

        while(input == null){
            input = scanner.nextLine();
        }

        return input;
    }


    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
}
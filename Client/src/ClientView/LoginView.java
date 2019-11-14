package ClientView;

<<<<<<< HEAD
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {

    //MEMBER VARIABLES

    private JPanel titlePanel, centrePanel, buttonPanel;

    private JTextField usernameField;
    private JPasswordField passwordFeild;

    private JButton loginButton;

    /**
     * Creates a LoginView object
     * @param width width of login window
     * @param height height of login window
     */
    public LoginView(int width, int height){
        titlePanel = new JPanel();
        centrePanel = new JPanel();
        buttonPanel = new JPanel();

        loginButton = new JButton("Login");

        setTitle("Login Window");
        setSize(width, height);
        setLayout(new BorderLayout());

        add("North", titlePanel);
        add("Center", centrePanel);
        add("South", buttonPanel);

        usernameField = new JTextField(10);
        passwordFeild = new JPasswordField(10);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setLabelFor(usernameField);
        centrePanel.add(usernameLabel);
        centrePanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setLabelFor(passwordFeild);
        centrePanel.add(passwordLabel);
        centrePanel.add(passwordFeild);

        buttonPanel.add(loginButton);

        titlePanel.add(new Label("Please enter username and password:"));

        pack();
        setSize(width, height);
        setLocationRelativeTo(null);

        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * adds action listener to the login button
     * @param listenForLoginButton
     */
    public void addLoginListener(ActionListener listenForLoginButton){
        loginButton.addActionListener(listenForLoginButton);
    }


    //GETTERS AND SETTERS
    public JButton getLoginButton() {
        return loginButton;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JTextField getPasswordFeild() {
        return passwordFeild;
    }

=======
import java.util.Scanner;

public class LoginView{

    private Scanner scanner;

    public LoginView(){
        scanner = new Scanner(System.in);
    }

    public String promptUsername(){
        System.out.println("Enter username: ('observer' to observe)");
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
>>>>>>> 2be4464dc1ac2bf761e8ba90cd8fb574fdde0e39
}
package ClientController;

import java.net.InetAddress;
import java.util.*;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class EmailSender {

    private static int num = 0;

    public void sendMail(String recepient) throws MessagingException {
        System.out.println("Preparing to send email");
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myAccountEmail = "blackjackCPSC441@gmail.com";
        String password = "fall2019cpsc441";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        Message message = prepareMessage(session, myAccountEmail, recepient);

        Transport.send(message);
        System.out.println("Message sent successfully!");
    }

    public Message prepareMessage(Session session, String myAccountEmail, String recepient){
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("CPSC 411 Blackjack Invite " + String.valueOf(num));
            message.setText("To join you need the following:\n" +
                    "IP Address: " + InetAddress.getLocalHost() + "\n" +
                    "PORT = 8000");
            num++;
            return message;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
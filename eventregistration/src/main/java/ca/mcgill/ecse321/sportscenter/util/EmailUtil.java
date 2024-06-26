package ca.mcgill.ecse321.sportscenter.util;

import org.springframework.stereotype.Service;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

@Service
public class EmailUtil {
    public static void sendConfirmationEmail(String recipient, String sessionInformation, String password) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "mail.mailservers.ca");
        properties.put("mail.smtp.port", "5025");

        String myAccountEmail = "sports.center@mailservers.ca";
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });
        Message message = prepareMessage(session, myAccountEmail, recipient, sessionInformation);
        Transport.send(message);
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recipient, String sessionInformation) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Sports Center Registration Confirmation");
            String htmlCode = "<h1>Sports Center Registration Created!</h1>\n" +
                    "<p>Your registration for " + sessionInformation + " has been processed successfully! Thank you for joining us.</p>\n" +
                    "<p>See you soon,</p>\n" +
                    "<p>Sports Center Team</p>\n" +
                    "<p>[Do not reply]<p>";
            message.setContent(htmlCode, "text/html");
            return message;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
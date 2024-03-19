package ca.mcgill.ecse321.sportscenter.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;


public class EmailUtil {
    private static String password;

    @Value("${email.password}")
    private String emailPassword;

    @PostConstruct
    private void init() {
        EmailUtil.password = this.emailPassword;
    }
    public static void sendConfirmationEmail(String recipient) throws MessagingException {
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
        Message message = prepareMessage(session, myAccountEmail, recipient);
        Transport.send(message);
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recipient) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Sports Center Registration Confirmation");
            String htmlCode = "<h1>Sports Center Account Created!</h1>\n" +
                    "<p>Your Sports Center account has been created successfully! Thank you for joining us.</p>\n" +
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

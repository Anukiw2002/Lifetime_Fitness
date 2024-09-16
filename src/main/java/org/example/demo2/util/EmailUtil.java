package org.example.demo2.util;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import jakarta.mail.MessagingException;


public class EmailUtil {

    private static final String SMTP_HOST = "smtp.gmail.com"; // Your SMTP server
    private static final String SMTP_PORT = "587"; // Your SMTP port
    private static final String SMTP_USER = "ishanmadurangai661@gmail.com"; // Your SMTP username
    private static final String SMTP_PASSWORD = "your-email-password"; // Your SMTP password

    public static void sendEmail(String to, String subject, String body) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USER, SMTP_PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SMTP_USER));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }
}

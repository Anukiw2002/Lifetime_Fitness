package org.example.demo2.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class InstructorEmailUtil {

    private static final String FROM_EMAIL = "anuki.thiyara@gmail.com";
    private static final String EMAIL_PASSWORD = "efad svtz mman lhvt";

    public static boolean sendCredentialsEmail(String recipientEmail, String tempPassword) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");


        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, EMAIL_PASSWORD);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Lifetime Fitness - Complete Your Profile");

            String emailContent =
                    "<!DOCTYPE html>" +
                            "<html>" +
                            "<head>" +
                            "    <style>" +
                            "        body { font-family: Arial, sans-serif; line-height: 1.6; }" +
                            "        .container { max-width: 600px; margin: 0 auto; padding: 20px; }" +
                            "        .header { background-color: #0052cc; color: white; padding: 10px; text-align: center; }" +
                            "        .content { padding: 20px; border: 1px solid #ddd; border-radius: 5px; }" +
                            "        .credentials { background-color: #f9f9f9; padding: 15px; margin: 20px 0; border-left: 4px solid #0052cc; }" +
                            "    </style>" +
                            "</head>" +
                            "<body>" +
                            "    <div class='container'>" +
                            "        <div class='header'>" +
                            "            <h2>Welcome to Lifetime Fitness!</h2>" +
                            "        </div>" +
                            "        <div class='content'>" +
                            "            <p>Dear Instructor,</p>" +
                            "            <p>Your instructor account has been created. Please use the following credentials to log in:</p>" +
                            "            <div class='credentials'>" +
                            "                <p><strong>Email:</strong> " + recipientEmail + "</p>" +
                            "                <p><strong>Temporary Password:</strong> " + tempPassword + "</p>" +
                            "            </div>" +
                            "            <p>Please change your password after your first login.</p>" +
                            "            <p>If you have any questions, please contact me.</p>" +
                            "            <p>Best regards,<br>Maduranga Perera,<br>Lifetime Fitness</p>" +
                            "        </div>" +
                            "    </div>" +
                            "</body>" +
                            "</html>";

            message.setContent(emailContent, "text/html; charset=utf-8");

            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}

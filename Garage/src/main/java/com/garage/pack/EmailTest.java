package com.pack.garage;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.time.*;
import java.util.*;

public class EmailTest {
    static Clock clock = Clock.systemUTC();

    public static void main(String[] args) {
        // SUBSTITUTE YOUR EMAIL ADDRESSES HERE!
        String to = "sendToMailAddress";
        String from = "sendFromMailAddress";

        // SUBSTITUTE YOUR ISP'S MAIL SERVER HERE!
        String host = "smtp.yourisp.invalid";

        // Create properties, get Session
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.debug", "true");
        Session session = Session.getInstance(props);

        try {
            // Instantiate a message
            Message msg = new MimeMessage(session);

            // Set message attributes
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = { new InternetAddress(to) };
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject("Test E-Mail through Java");
            Date now = Date.from(LocalDateTime.now(clock).toInstant(ZoneOffset.UTC));
            msg.setSentDate(now);

            // Set message content
            msg.setText("This is a test of sending a plain text e-mail through Java.\nHere is line 2.");

            // Send the message
            Transport.send(msg);
        } catch (MessagingException mex) {
            // Prints all nested (chained) exceptions as well
            mex.printStackTrace();
        }
    }
}

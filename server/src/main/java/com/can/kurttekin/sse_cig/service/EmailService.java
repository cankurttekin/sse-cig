package com.can.kurttekin.sse_cig.service;


import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.services.gmail.Gmail;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;

@Service
public class EmailService {
    private static final String IMAP_HOST = "imap.gmail.com";
    private static final String USERNAME = "mail@gmail.com";
    private static final String PASSWORD = "app password for Gmail";
    private static final String SEARCH_TEXT = "Unfortunately, we will not be moving forward with your application";
    private static final String SUBJECT_TEXT = "Your application to ";

    public String checkForNewEmails() {
        try {
            // Connect to Gmail IMAP server
            Properties properties = new Properties();
            properties.put("mail.imap.host", IMAP_HOST);
            properties.put("mail.imap.port", "993");
            properties.put("mail.imap.ssl.enable", "true");
            properties.put("mail.imap.auth", "true");

            Session session = Session.getInstance(properties);
            Store store = session.getStore("imap");
            store.connect(IMAP_HOST, USERNAME, PASSWORD);

            Folder inbox = store.getFolder("Work");
            inbox.open(Folder.READ_WRITE); // Open folder in READ_WRITE mode to mark emails as read

            // Fetch unseen messages from Work folder
            Message[] messages = inbox.search(
                    new FlagTerm(new Flags(Flags.Flag.SEEN), false));

            for (Message message : messages) {
                System.out.println(message.getSubject());
                String subject = message.getSubject();
                String content = extractContent(message);

                // Check if the content matches your keyword
                if (content.contains(SEARCH_TEXT) || subject.contains(SUBJECT_TEXT)) {
                    // Mark the email as read
                    message.setFlag(Flags.Flag.SEEN, true); // This marks the email as read (seen)
                    // Return a message when an email matching the keyword is found
                    return "New email: " + subject + "\nContent: " + content;
                }
            }

            inbox.close(false);
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;  // No email found with the keyword
    }

    // Method to extract the content from the email
    private String extractContent(Message message) throws Exception {
        String content = "";
        if (message.isMimeType("text/plain")) {
            content = (String) message.getContent();  // Plain text email content
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            content = getTextFromMimeMultipart(mimeMultipart);  // Extract text from multipart
        }
        return content;
    }

    // Extract text from MimeMultipart (if the email is multipart)
    private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception {
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < mimeMultipart.getCount(); i++) {
            BodyPart part = mimeMultipart.getBodyPart(i);
            if (part.isMimeType("text/plain")) {
                content.append((String) part.getContent());  // Extract the plain text part
            }
        }
        return content.toString();
    }

}

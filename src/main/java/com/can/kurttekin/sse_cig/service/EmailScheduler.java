package com.can.kurttekin.sse_cig.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
/*
@Service
public class EmailScheduler {
    private final EmailService emailService;

    public EmailScheduler(EmailService emailService) {
        this.emailService = emailService;
    }

    // Periodically check for new emails
    @Scheduled(fixedRate = 1000)  // Every 5 minutes (300,000 milliseconds)
    public void pollForNewEmails() {
        String emailNotification = emailService.checkForNewEmails();

        if (emailNotification != null) {
            // If a new matching email is found, trigger an event (e.g., log it, notify users, etc.)
            System.out.println("New email notification: " + emailNotification);
        }
    }
}
*/
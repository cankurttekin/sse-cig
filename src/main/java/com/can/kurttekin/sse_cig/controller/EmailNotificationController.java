package com.can.kurttekin.sse_cig.controller;

import com.can.kurttekin.sse_cig.service.EmailService;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;

@RestController
public class EmailNotificationController {

    private final EmailService emailService;

    public EmailNotificationController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping(value = "/sse/have-a-cigar", produces = "text/event-stream")
    public Flux<ServerSentEvent<String>> sendEmailNotifications() {
        return Flux.interval(Duration.ofSeconds(60))
                .mapNotNull(sequence -> {
                    String emailNotification = emailService.checkForNewEmails();

                    // Return event only if email is found
                    if (emailNotification != null) {
                        System.out.println(emailNotification);
                        return ServerSentEvent.builder(emailNotification)
                                .event("email")
                                .build();
                    }
                    return null; // Returning null if no new email
                })
                .filter(Objects::nonNull); // Filters out null values
    }
}
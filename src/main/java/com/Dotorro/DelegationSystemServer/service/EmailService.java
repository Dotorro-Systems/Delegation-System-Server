package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.model.Delegation;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendReminderTomorrowDelegation(String addressee, Delegation delegation){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(addressee);
        message.setSubject("Reminder of tomorrows delegation");
        message.setText("We remind you your delegation " + delegation.getTitle()+
                " from: " + delegation.getOrigin() +" to: "+ delegation.getDestination() +
                ". Starts:" + delegation.getStartDate() + "and ends: " + delegation.getEndDate() + ".");
        mailSender.send(message);
    }
}

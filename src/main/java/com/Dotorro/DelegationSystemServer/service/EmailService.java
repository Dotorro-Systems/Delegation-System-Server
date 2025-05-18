package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.model.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendReminderTomorrowDelegation(User user, Delegation delegation){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Reminder of tomorrows delegation");
        message.setText("Dear " + user.getFirstName() + " "+ user.getLastName() + ", \n \n" +
                "This is a friendly reminder about your upcoming delegation titled \""
                        + delegation.getTitle() + "\".\n" +
                        "From: " + delegation.getOrigin() + "\n" +
                        "To: " + delegation.getDestination() + "\n" +
                        "Start date: " + delegation.getStartDate().toLocalDate() + "\n" +
                        "End date: " + delegation.getEndDate().toLocalDate() + "\n\n" +
                        "Here is link to your delegation: http://localhost:4200/delegations/" + delegation.getId() +
                        "Best regards,\n" +
                        "Dotorro.");
        mailSender.send(message);
    }

    public void sendWelcomeMail(User user){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Welcome to Dotorro");
        message.setText("Hey " + user.getFirstName() + " "+ user.getLastName() + ", \n \n" +
                "Thank you for choosing Dotorro.");
        mailSender.send(message);
    }

    public void sendAddToDelegationMail(User user, Delegation delegation){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("You were added to delegation");
        message.setText("Dear " + user.getFirstName() + " "+ user.getLastName() + ", \n \n" +
                "Your manager added you to delegation:  \"" + delegation.getTitle() + "\".\n" +
                "From: " + delegation.getOrigin() + "\n" +
                "To: " + delegation.getDestination() + "\n" +
                "Start date: " + delegation.getStartDate().toLocalDate() + "\n" +
                "End date: " + delegation.getEndDate().toLocalDate() + "\n"+
                "Here is link to your delegation: http://localhost:4200/delegations/" + delegation.getId());
        mailSender.send(message);
    }
}

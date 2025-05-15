package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.repository.DelegationRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class DelegationMailService {
    private final DelegationService delegationService;
    private final EmailService emailService;

    public DelegationMailService(DelegationService delegationService, EmailService emailService){
        this.emailService = emailService;
        this.delegationService = delegationService;
    }

    @Scheduled(cron = "0 00 08 * * *")
    @Transactional
    public void sendEmails(){
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Delegation> delegations = delegationService.getDelegationsByStartDate(tomorrow);
        Set<User> users = new HashSet<>();

        for (Delegation delegation : delegations) {
            users.addAll(delegation.getUsers());
        }

        for (User user : users) {
            sendEmail(user, delegations);
        }
    }

    public void sendEmail(User user, List<Delegation> delegations){
        for(Delegation delegation : delegations){
            emailService.sendReminderTomorrowDelegation(user, delegation);
        }
    }
}

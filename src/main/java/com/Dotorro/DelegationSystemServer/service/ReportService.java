package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.model.*;
import com.Dotorro.DelegationSystemServer.dto.ReportDelegationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final DelegationService delegationService;
    private final ExpenseService expenseService;
    private final WorkLogService workLogService;
    private final NoteService noteService;

    public ReportService(DelegationService delegationService, ExpenseService expenseService,
                         WorkLogService workLogService, NoteService noteService) {
        this.delegationService = delegationService;
        this.expenseService = expenseService;
        this.workLogService = workLogService;
        this.noteService = noteService;
    }

    public ReportDelegationDTO generateReport(Long delegationId){
        Delegation delegation = delegationService.getDelegationById(delegationId);
        List<Expense> allExpenses = expenseService.getExpensesByDelegationId(delegationId);
        double totalExpenses = allExpenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        List<WorkLog> allWorkLogs = workLogService.getWorkLogsByDelegationId(delegationId);
        Long allWorkedHours = allWorkLogs.stream()
                .mapToLong(WorkLog::getWorkedHours)
                .sum();

        Map<User,Long> userAllWorkHours = allWorkLogs.stream()
                .collect(Collectors.groupingBy(
                        WorkLog::getUser,
                        Collectors.summingLong(WorkLog::getWorkedHours)
                ));

        List<Note> allNotes = noteService.getNotesByDelegationId(delegationId);

        List<User> users = new ArrayList<>(userAllWorkHours.keySet());

        return new ReportDelegationDTO(delegation, userAllWorkHours, allWorkedHours,
                totalExpenses, allNotes, users);
    }

}

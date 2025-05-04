package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.dto.ReportMonthlyDTO;
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
    private final DepartmentService departmentService;
    private final ExpenseService expenseService;
    private final WorkLogService workLogService;
    private final NoteService noteService;

    public ReportService(DelegationService delegationService, DepartmentService departmentService, ExpenseService expenseService,
                         WorkLogService workLogService, NoteService noteService) {
        this.delegationService = delegationService;
        this.departmentService = departmentService;
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

    public ReportMonthlyDTO generateMonthlyReport(Long departmentId , Integer targetMonth, Integer targetYear)
    {
        List<Delegation> allDelegations = delegationService.getAllDelegations();
        Department department = departmentService.getDepartmentById(departmentId);

        List<Delegation> monthlyDelegations = allDelegations.stream()
                .filter(delegation -> delegation.getStartDate().getMonthValue() == targetMonth)
                .filter(delegation -> delegation.getStartDate().getYear() == targetYear)
                .filter(delegation -> delegation.getDepartment().equals(department))
                .toList();

        Long allWorkedHours = monthlyDelegations.stream()
                .flatMap(delegation -> delegation.getWorkLogs().stream())
                .mapToLong(WorkLog::getWorkedHours)
                .sum();

        double totalExpenses = monthlyDelegations.stream()
                .flatMap(delegation -> delegation.getExpenses().stream())
                .mapToDouble(Expense::getAmount)
                .sum();

        Map<Delegation, Long> delegationAllWorkHours = monthlyDelegations.stream()
                .collect(Collectors.toMap(
                        delegation -> delegation,
                        delegation -> delegation.getWorkLogs().stream()
                                .mapToLong(WorkLog::getWorkedHours)
                                .sum()
                ));

        Map<Delegation, Double> delegationAllExpenses = monthlyDelegations.stream()
                .collect(Collectors.toMap(
                        delegation -> delegation,
                        delegation -> delegation.getExpenses().stream()
                                .mapToDouble(Expense::getAmount)
                                .sum()
                ));

        Map<Delegation, List<User>> delegationAllUsers = monthlyDelegations.stream()
                .collect(Collectors.toMap(
                        delegation -> delegation,
                        Delegation::getUsers
                ));

        String monthName = monthlyDelegations.get(0).getStartDate().getMonth().toString();

        return new ReportMonthlyDTO(monthName,targetYear, department, delegationAllWorkHours, delegationAllExpenses, delegationAllUsers, allWorkedHours,totalExpenses);
    }

}

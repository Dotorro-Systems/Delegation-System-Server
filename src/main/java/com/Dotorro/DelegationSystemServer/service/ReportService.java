package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.dto.ReportDelegationDTO;
import com.Dotorro.DelegationSystemServer.model.Expense;
import com.Dotorro.DelegationSystemServer.model.WorkLog;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private final DelegationService delegationService;
    private final ExpenseService expenseService;
    private final WorkLogService workLogService;

    public ReportService(DelegationService delegationService, ExpenseService expenseService,
                         WorkLogService workLogService) {
        this.delegationService = delegationService;
        this.expenseService = expenseService;
        this.workLogService = workLogService;
    }

    public ResponseEntity<ReportDelegationDTO> generateReport(Long delegationId){
        Delegation delegation = delegationService.getDelegationById(delegationId);
        List<Expense> allExpenses = expenseService.getExpensesByDelegationId(delegationId);
        double totalExpenses = allExpenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        List<WorkLog> allWorkLogs = workLogService.getWorkLogsByDelegationId(delegationId);
        Long allWorkedHours = allWorkLogs.stream()
                .mapToLong(WorkLog::getWorkedHours)
                .sum();

        //ReportDelegationDTO reportDelegationDTO = new ReportDelegationDTO();

    }

}

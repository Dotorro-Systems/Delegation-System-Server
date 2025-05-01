package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.model.Delegation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final DelegationService delegationService;
    private final ExpenseService expenseService;

    public ReportService(DelegationService delegationService, ExpenseService expenseService) {
        this.delegationService = delegationService;
        this.expenseService = expenseService;
    }

    ///public ResponseEntity<ReportDTO> generateReport(Delegation delegation){
        //zwroca raport

    ///}

}

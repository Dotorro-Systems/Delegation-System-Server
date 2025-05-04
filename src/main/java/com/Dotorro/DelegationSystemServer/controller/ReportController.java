package com.Dotorro.DelegationSystemServer.controller;

import com.Dotorro.DelegationSystemServer.dto.ReportMonthlyDTO;
import com.Dotorro.DelegationSystemServer.service.ReportService;
import com.Dotorro.DelegationSystemServer.dto.ReportDelegationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
@CrossOrigin(origins = "*")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService){
        this.reportService = reportService;
    }

    @GetMapping("/{delegationId}")
    public ResponseEntity<ReportDelegationDTO> getDelegationReport(@PathVariable Long delegationId) {
        ReportDelegationDTO report = reportService.generateReport(delegationId);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/{departmentId}/{year}/{month}")
    public ResponseEntity<ReportMonthlyDTO> getMonthlyReport(@PathVariable Long departmentId, @PathVariable Integer year, @PathVariable Integer month) {
        ReportMonthlyDTO report = reportService.generateMonthlyReport(departmentId, year, month);
        return ResponseEntity.ok(report);
    }


}

package com.Dotorro.DelegationSystemServer.controller;

import com.Dotorro.DelegationSystemServer.service.ReportService;
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

    /* @GetMapping("/{delegationId}")
    public ResponseEntity<ReportDTO> getReport(@PathVariable Long delegationId) {
        ReportDTO report = reportService.generateReport(delegationId);
        return ResponseEntity.ok(report);
    }
     */

}

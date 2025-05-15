package com.Dotorro.DelegationSystemServer.controller;

import com.Dotorro.DelegationSystemServer.dto.ReportMonthlyDTO;
import com.Dotorro.DelegationSystemServer.service.ReportService;
import com.Dotorro.DelegationSystemServer.dto.ReportDelegationDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/reports")
@CrossOrigin(origins = "*")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService){
        this.reportService = reportService;
    }

    @GetMapping("/delegation/{delegationId}")
    public ResponseEntity<ReportDelegationDTO> getDelegationReport(@PathVariable Long delegationId) {
        ReportDelegationDTO report = reportService.generateReport(delegationId);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/{year}/{month}/department/{departmentId}")
    public ResponseEntity<ReportMonthlyDTO> getMonthlyReport(@PathVariable Long departmentId, @PathVariable Integer year, @PathVariable Integer month) {
        ReportMonthlyDTO report = reportService.generateMonthlyReport(departmentId, year, month);
        return ResponseEntity.ok(report);
    }

    @GetMapping(value = "/pdf/{delegationId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getPdfReport(@PathVariable Long delegationId) {
        ReportDelegationDTO report = reportService.generateReport(delegationId);
        byte[] pdf = reportService.getReportToPdf(report);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("report.pdf").build());

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }

    @GetMapping(value = "/pdf/{departmentId}/{year}/{month}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getMonthlyPdfReport(@PathVariable Long departmentId, @PathVariable Integer year, @PathVariable Integer month) {
        ReportMonthlyDTO report = reportService.generateMonthlyReport(departmentId,month,year);
        byte[] pdf = reportService.getMonthlyReportToPdf(report);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("report.pdf").build());

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }
}

package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.dto.ReportMonthlyDTO;
import com.Dotorro.DelegationSystemServer.model.*;
import com.Dotorro.DelegationSystemServer.dto.ReportDelegationDTO;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Document;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final DelegationService delegationService;
    private final DepartmentService departmentService;

    public ReportService(DelegationService delegationService, DepartmentService departmentService) {
        this.delegationService = delegationService;
        this.departmentService = departmentService;
    }

    public ReportDelegationDTO generateReport(Long delegationId){
        Delegation delegation = delegationService.getDelegationById(delegationId);
        List<Expense> allExpenses =delegation.getExpenses();
        double totalExpenses = allExpenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        List<WorkLog> allWorkLogs = delegation.getWorkLogs();
        Long allWorkedHours = allWorkLogs.stream()
                .mapToLong(WorkLog::getWorkedHours)
                .sum();

        Map<String, Long> userAllWorkHours = allWorkLogs.stream()
                .collect(Collectors.groupingBy(
                        workLog -> {
                            User user = workLog.getUser();
                            return user.getFirstName() + " " + user.getLastName();
                        },
                        Collectors.summingLong(WorkLog::getWorkedHours)
                ));

        List<Note> allNotes = delegation.getNotes();
        List<User> users = delegation.getUsers();

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

    public byte[] getReportToPdf(ReportDelegationDTO reportDelegationDTO){
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try{
            PdfWriter.getInstance(document, out);
            document.open();

            document.add(new Paragraph("Report Delegation"));
            document.add(new Paragraph("Delegation number: " + reportDelegationDTO.getDelegationId()));
            document.add(new Paragraph("Title: " + reportDelegationDTO.getTitle()));
            document.add(new Paragraph("Origin: " + reportDelegationDTO.getOrigin()));
            document.add(new Paragraph("Destination: " + reportDelegationDTO.getDestination()));
            document.add(new Paragraph("Start date: " + reportDelegationDTO.getStartDate()));
            document.add(new Paragraph("End date: " + reportDelegationDTO.getEndDate()));
            document.add(new Paragraph("Total expenses: " + reportDelegationDTO.getTotalExpenses()));
            document.add(new Paragraph("All worked hours: " + reportDelegationDTO.getAllWorkHours()));

            reportDelegationDTO.getUserAllWorkHours().forEach((key, value) ->{
                document.add(new Paragraph("Name: " + key + ": worked hours: " + value));
            });

            reportDelegationDTO.getAllNotes().forEach(note ->
                    document.add(new Paragraph("Note: " + note)));

            reportDelegationDTO.getAllUsers().forEach(user ->
                    document.add(new Paragraph("Employee: " + user.getFirstName() + " " + user.getLastName())));

            document.add(new Paragraph("Report generated on: " + LocalDateTime.now()));
            document.close();
        }catch (DocumentException e){
            throw new RuntimeException("Error during report pdf generating", e);
        }
        return  out.toByteArray();
    }

    public byte[] getMonthlyReportToPdf(ReportMonthlyDTO reportMonthlyDTO){
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try{
            PdfWriter.getInstance(document, out);
            document.open();

            document.add(new Paragraph("Monthly Report"));
            document.add(new Paragraph("Report time frame: " + reportMonthlyDTO.getMonthName() + " " + reportMonthlyDTO.getYear()));
            document.add(new Paragraph(""));
            document.add(new Paragraph("Department: " +  reportMonthlyDTO.getDepartmentName()));
            document.add(new Paragraph(""));
            document.add(new Paragraph(""));
            document.add(new Paragraph("Total expenses: " + reportMonthlyDTO.getTotalExpenses()));
            document.add(new Paragraph("All worked hours: " + reportMonthlyDTO.getAllWorkHours()));

            document.add(new Paragraph("Working hours performed during individual delegations:"));
            reportMonthlyDTO.getDelegationAllWorkHours().forEach((key, value) ->{
                document.add(new Paragraph("Delegation: " + key.getTitle() + " - worked hours: " + value));
            });

            document.add(new Paragraph("\n" +
                    "Total expenses incurred during individual delegations:"));
            reportMonthlyDTO.getDelegationAllExpenses().forEach((key, value) ->{
                document.add(new Paragraph("Delegation: " + key.getTitle() + " - incurred expenses: " + value));
            });

            document.add(new Paragraph("\n" +
                    " List of employees taking part in individual delegations:"));
            reportMonthlyDTO.getDelegationAllUsers().forEach((key, value) ->{
                document.add(new Paragraph("Delegation: " + key));
                value.forEach(user ->
                        document.add(new Paragraph(user.getFirstName() + " " + user.getLastName())));
                document.add(new Paragraph("\n"));
            });

            document.add(new Paragraph("Report generated on: " + LocalDateTime.now()));
            document.close();
        }catch (DocumentException e){
            throw new RuntimeException("Error during monthly report pdf generating", e);
        }
        return  out.toByteArray();
    }

}

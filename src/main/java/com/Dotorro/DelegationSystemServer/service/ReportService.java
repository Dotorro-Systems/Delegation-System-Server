package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.dto.CollectiveReportDTO;
import com.Dotorro.DelegationSystemServer.model.*;
import com.Dotorro.DelegationSystemServer.dto.ReportDelegationDTO;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Document;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;

@Service
public class ReportService {

    private final DelegationService delegationService;
    private final DepartmentService departmentService;

    public ReportService(DelegationService delegationService, DepartmentService departmentService) {
        this.delegationService = delegationService;
        this.departmentService = departmentService;
    }

    public ReportDelegationDTO generateReport(Long delegationId) {
        Delegation delegation = delegationService.getDelegationById(delegationId);
        List<Expense> allExpenses = delegation.getExpenses();
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

        Map<String, Double> userTotalExpenses = allExpenses.stream()
                .collect(Collectors.groupingBy(
                        expense -> {
                            User user = expense.getUser();
                            return user.getFirstName() + " " + user.getLastName();
                        },
                        Collectors.summingDouble(Expense::getAmount)
                ));


        List<Note> allNotes = delegation.getNotes();
        List<User> users = delegation.getUsers();

        return new ReportDelegationDTO(delegation, userAllWorkHours, allWorkedHours,
                userTotalExpenses,totalExpenses, allNotes, users);
    }

    public CollectiveReportDTO generateMonthlyReport(Long departmentId, Integer targetMonth, Integer targetYear) {
        List<Delegation> allDelegations = delegationService.getAllDelegations();
        Department department = departmentService.getDepartmentById(departmentId);

        List<Delegation> monthlyDelegations = allDelegations.stream()
                .filter(delegation -> delegation.getStartDate().getMonthValue() == targetMonth)
                .filter(delegation -> delegation.getStartDate().getYear() == targetYear)
                .filter(delegation -> delegation.getDepartment().equals(department))
                .toList();

        YearMonth yearMonth = YearMonth.of(targetYear, targetMonth);

        LocalDate startOfMonth = yearMonth.atDay(1);

        LocalDate endOfMonth = yearMonth.atEndOfMonth();

        return generateCollectiveReport("Monthly Report", monthlyDelegations, startOfMonth, endOfMonth, department);
    }

    public CollectiveReportDTO generateYearlyReport(Long departmentId, Integer targetYear) {
        List<Delegation> allDelegations = delegationService.getAllDelegations();
        Department department = departmentService.getDepartmentById(departmentId);

        List<Delegation> yearlyDelegations = allDelegations.stream()
                .filter(delegation -> delegation.getStartDate().getYear() == targetYear)
                .filter(delegation -> delegation.getDepartment().equals(department))
                .toList();

        YearMonth januaryMonth = YearMonth.of(targetYear, 1);
        YearMonth decemberMonth = YearMonth.of(targetYear, 12);

        LocalDate startOfMonth = januaryMonth.atDay(1);

        LocalDate endOfMonth = decemberMonth.atEndOfMonth();

        return generateCollectiveReport("Yearly Report", yearlyDelegations, startOfMonth, endOfMonth, department);
    }

    public CollectiveReportDTO generateCollectiveReport(String title, List<Delegation> delegations, LocalDate startDate, LocalDate endDate, Department department)
    {
        Long allWorkedHours = delegations.stream()
                .flatMap(delegation -> delegation.getWorkLogs().stream())
                .mapToLong(WorkLog::getWorkedHours)
                .sum();

        double totalExpenses = delegations.stream()
                .flatMap(delegation -> delegation.getExpenses().stream())
                .mapToDouble(Expense::getAmount)
                .sum();

        Map<Delegation, Long> delegationAllWorkHours = delegations.stream()
                .collect(Collectors.toMap(
                        delegation -> delegation,
                        delegation -> delegation.getWorkLogs().stream()
                                .mapToLong(WorkLog::getWorkedHours)
                                .sum()
                ));

        Map<Delegation, Double> delegationAllExpenses = delegations.stream()
                .collect(Collectors.toMap(
                        delegation -> delegation,
                        delegation -> delegation.getExpenses().stream()
                                .mapToDouble(Expense::getAmount)
                                .sum()
                ));

        Map<Delegation, List<User>> delegationAllUsers = delegations.stream()
                .collect(Collectors.toMap(
                        delegation -> delegation,
                        Delegation::getUsers
                ));

        return new CollectiveReportDTO(title, startDate, endDate, department, delegationAllWorkHours, delegationAllExpenses, delegationAllUsers, allWorkedHours, totalExpenses);
    }

    public byte[] getCollectiveReportToPdf(CollectiveReportDTO collectiveReportDTO) {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, out);

            document.open();
            PdfPTable titleTable = new PdfPTable(2);
            titleTable.setWidthPercentage(100);
            titleTable.setWidths(new float[]{50f, 50f});
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");

            PdfPCell leftCell = new PdfPCell(new Phrase(collectiveReportDTO.getTitle(), new Font(Font.HELVETICA, 16, Font.BOLD)));
            leftCell.setBorder(Rectangle.NO_BORDER);
            leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell rightCell = new PdfPCell(new Phrase(LocalDateTime.now().format(formatter)));
            rightCell.setBorder(Rectangle.NO_BORDER);
            rightCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

            titleTable.addCell(leftCell);
            titleTable.addCell(rightCell);

            document.add(titleTable);
            document.add(new Paragraph("Report time frame: " + collectiveReportDTO.getStartPeriod().format(formatter) + " - " + collectiveReportDTO.getEndPeriod().format(formatter)));
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Department: " + collectiveReportDTO.getDepartmentName()));
            document.add(new Paragraph(""));
            document.add(new Paragraph(""));
            document.add(new Paragraph("Total expenses: " + collectiveReportDTO.getTotalExpenses()));

            PdfPTable expensesTable = new PdfPTable(3);
            expensesTable.setWidthPercentage(100);

            Font headerFont = new Font(Font.HELVETICA, 12, Font.BOLD);
            PdfPCell eh1 = new PdfPCell(new Phrase("Delegation Title", headerFont));
            PdfPCell eh2 = new PdfPCell(new Phrase("Date", headerFont));
            PdfPCell eh3 = new PdfPCell(new Phrase("Total Expenses", headerFont));

            expensesTable.addCell(eh1);
            expensesTable.addCell(eh2);
            expensesTable.addCell(eh3);

            expensesTable.setHeaderRows(1);

            collectiveReportDTO.getDelegationAllExpenses().forEach((key, value) -> {
                expensesTable.addCell(key.getTitle());
                expensesTable.addCell(key.getStartDate().toString());
                expensesTable.addCell(value.toString());
            });

            if(collectiveReportDTO.getDelegationAllExpenses().isEmpty())
            {
                document.add(new Paragraph("No expenses recorded"));
            }
            else
            {
                document.add(expensesTable);
            }

            document.add(new Paragraph("All worked hours: " + collectiveReportDTO.getAllWorkHours()));

            document.add(new Paragraph("Working hours performed during individual delegations:"));
            PdfPTable workHoursTable = new PdfPTable(3);
            workHoursTable.setWidthPercentage(100);

            PdfPCell wh1 = new PdfPCell(new Phrase("Delegation Title", headerFont));
            PdfPCell wh2= new PdfPCell(new Phrase("Date", headerFont));
            PdfPCell wh3 = new PdfPCell(new Phrase("Worked Hours", headerFont));

            workHoursTable.addCell(wh1);
            workHoursTable.addCell(wh2);
            workHoursTable.addCell(wh3);

            workHoursTable.setHeaderRows(1);

            collectiveReportDTO.getDelegationAllWorkHours().forEach((key, value) -> {
                workHoursTable.addCell(key.getTitle());
                workHoursTable.addCell(key.getStartDate().format(formatter));
                workHoursTable.addCell(value.toString());
            });

            if(collectiveReportDTO.getDelegationAllWorkHours().isEmpty())
            {
                document.add(new Paragraph("No work hours recorded"));
            }
            else
            {
                document.add(workHoursTable);
            }

            document.add(new Paragraph("\n" +
                    " List of employees taking part in individual delegations:"));
            for (Map.Entry<Delegation, List<User>> entry : collectiveReportDTO.getDelegationAllUsers().entrySet()) {
                Delegation delegation = entry.getKey();
                List<User> users = entry.getValue();

                Font deptFont = new Font(Font.HELVETICA, 14, Font.BOLD);
                Paragraph header = new Paragraph("Delegation: " + delegation.getTitle() + " - " + delegation.getStartDate().toString(), deptFont);
                header.setSpacingBefore(15f);
                header.setSpacingAfter(5f);
                document.add(header);

                if(users.isEmpty())
                {
                    document.add(new Paragraph("No employees assigned to delegation"));
                }
                else
                {
                    PdfPTable table = new PdfPTable(3);
                    table.setWidthPercentage(100);
                    table.setWidths(new float[]{1.5f, 1.5f, 3f});
                    table.setHeaderRows(1);

                    Font headFont = new Font(Font.HELVETICA, 12, Font.BOLD);

                    PdfPCell c1 = new PdfPCell(new Phrase("First Name", headFont));
                    table.addCell(c1);

                    PdfPCell c2 = new PdfPCell(new Phrase("Last Name", headFont));
                    table.addCell(c2);

                    PdfPCell c3 = new PdfPCell(new Phrase("Email", headFont));
                    table.addCell(c3);

                    for (User u : users) {
                        table.addCell(u.getFirstName());
                        table.addCell(u.getLastName());
                        table.addCell(u.getEmail());
                    }
                    
                    document.add(table);
                }
            }


            document.close();
        } catch (DocumentException e) {
            throw new RuntimeException("Error during monthly report pdf generating", e);
        }
        return out.toByteArray();
    }

    public byte[] getReportToPdf(ReportDelegationDTO reportDelegationDTO) {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, out);
            document.open();

            PdfPTable titleTable = new PdfPTable(2);
            titleTable.setWidthPercentage(100);
            titleTable.setWidths(new float[]{50f, 50f});
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");


            PdfPCell leftCell = new PdfPCell(new Phrase("Delegation Report", new Font(Font.HELVETICA, 16, Font.BOLD)));
            leftCell.setBorder(Rectangle.NO_BORDER);
            leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell rightCell = new PdfPCell(new Phrase(LocalDateTime.now().format(formatter)));
            rightCell.setBorder(Rectangle.NO_BORDER);
            rightCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

            titleTable.addCell(leftCell);
            titleTable.addCell(rightCell);

            document.add(titleTable);

            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Delegation number: " + reportDelegationDTO.getDelegationId()));
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Title: " + reportDelegationDTO.getTitle()));
            document.add(new Paragraph("Duration of the delegation: " + reportDelegationDTO.getStartDate().format(formatter) + " - " + reportDelegationDTO.getEndDate().format(formatter)));
            document.add(new Paragraph("Origin: " + reportDelegationDTO.getOrigin()));
            document.add(new Paragraph("Destination: " + reportDelegationDTO.getDestination()));

            document.add(new Paragraph("Total expenses: " + reportDelegationDTO.getTotalExpenses()));
            document.add(Chunk.NEWLINE);
            PdfPTable expensesTable = new PdfPTable(2);
            expensesTable.setWidthPercentage(100);

            Font headerFont = new Font(Font.HELVETICA, 12, Font.BOLD);
            PdfPCell eh1 = new PdfPCell(new Phrase("Name", headerFont));
            PdfPCell eh2 = new PdfPCell(new Phrase("Total expenses", headerFont));

            expensesTable.addCell(eh1);
            expensesTable.addCell(eh2);

            expensesTable.setHeaderRows(1);

            reportDelegationDTO.getUserTotalExpenses().forEach((key, value) -> {
                expensesTable.addCell(key);
                expensesTable.addCell(value.toString());
            });

            if(reportDelegationDTO.getUserTotalExpenses().isEmpty())
            {
                document.add(new Paragraph("No expenses performed during delegation"));
            }
            else
            {
                document.add(expensesTable);
            }

            document.add(new Paragraph("\n" +
                    "All worked hours: " + reportDelegationDTO.getAllWorkHours()));
            document.add(Chunk.NEWLINE);
            PdfPTable workHoursTable = new PdfPTable(2);
            workHoursTable.setWidthPercentage(100);

            PdfPCell wh1 = new PdfPCell(new Phrase("Name", headerFont));
            PdfPCell wh2 = new PdfPCell(new Phrase("Worked Hours", headerFont));

            workHoursTable.addCell(wh1);
            workHoursTable.addCell(wh2);

            workHoursTable.setHeaderRows(1);

            reportDelegationDTO.getUserAllWorkHours().forEach((key, value) -> {
                workHoursTable.addCell(key);
                workHoursTable.addCell(value.toString());
            });

            if(reportDelegationDTO.getUserAllWorkHours().isEmpty())
            {
                document.add(new Paragraph("No work hours performed during delegation"));
            }
            else
            {
                document.add(workHoursTable);
            }

            document.add(new Paragraph("\n" +
                    "Notes taken during delegation"));
            document.add(Chunk.NEWLINE);
            PdfPTable notesTable = new PdfPTable(3);
            notesTable.setWidthPercentage(100);

            PdfPCell nh1 = new PdfPCell(new Phrase("Date", headerFont));
            PdfPCell nh2 = new PdfPCell(new Phrase("Creator", headerFont));
            PdfPCell nh3 = new PdfPCell(new Phrase("Content", headerFont));

            notesTable.addCell(nh1);
            notesTable.addCell(nh2);
            notesTable.addCell(nh3);

            notesTable.setHeaderRows(1);

            reportDelegationDTO.getAllNotes().forEach(note -> {
                notesTable.addCell(note.getCreatedAt().format(formatter));
                notesTable.addCell(note.getUser().getFirstName() + note.getUser().getLastName());
                notesTable.addCell(note.getContent());
            });
            if(reportDelegationDTO.getAllNotes().isEmpty())
            {
                document.add(new Paragraph("No notes performed during delegation"));
            }
            else
            {
                document.add(notesTable);
            }
            document.add(new Paragraph("\n"));

            document.close();
        } catch (DocumentException e) {
            throw new RuntimeException("Error during report pdf generating", e);
        }
        return out.toByteArray();


    }
}

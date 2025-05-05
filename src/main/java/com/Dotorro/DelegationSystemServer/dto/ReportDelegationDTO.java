package com.Dotorro.DelegationSystemServer.dto;

import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.model.Note;
import com.Dotorro.DelegationSystemServer.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ReportDelegationDTO {
    private String title;
    private Long allWorkHours;
    private Map<String, Long> userAllWorkHours;
    private Double totalExpenses;
    private String origin;
    private String destination;
    private List<Note> allNotes;
    private String departmentName;
    private List<User> allUsers;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public ReportDelegationDTO(Delegation delegation, Map<String, Long> userAllWorkHours, Long allWorkHours, Double totalExpenses, List<Note> allNotes, List<User> allUsers)
    {
        this.title = delegation.getTitle();
        this.origin = delegation.getOrigin();
        this.destination = delegation.getDestination();
        this.startDate = delegation.getStartDate();
        this.endDate = delegation.getEndDate();
        this.userAllWorkHours = userAllWorkHours;
        this.allWorkHours = allWorkHours;
        this.totalExpenses = totalExpenses;
        this.allNotes = allNotes;
        this.allUsers = allUsers;
        this.departmentName = delegation.getDepartment().getName();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAllWorkHours() {
        return allWorkHours;
    }

    public void setAllWorkHours(Long allWorkHours) {
        this.allWorkHours = allWorkHours;
    }

    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(Double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public Map<String, Long> getUserAllWorkHours() {
        return userAllWorkHours;
    }

    public void setUserAllWorkHours(Map<String, Long> userAllWorkHours) {
        this.userAllWorkHours = userAllWorkHours;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<Note> getAllNotes() {
        return allNotes;
    }

    public void setAllNotes(List<Note> allNotes) {
        this.allNotes = allNotes;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<User> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(List<User> allUsers) {
        this.allUsers = allUsers;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}



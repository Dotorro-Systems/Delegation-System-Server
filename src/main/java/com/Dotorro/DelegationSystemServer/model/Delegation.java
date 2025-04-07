package com.Dotorro.DelegationSystemServer.model;
import com.Dotorro.DelegationSystemServer.utils.DelegationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Delegations")
public class Delegation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String origin;
    private String destination;
    private DelegationStatus status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "delegation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("delegation")
    private List<Note> notes = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "delegation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("delegation")
    private List<DelegationUser> delegationUsers = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "delegation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("delegation")
    private List<DelegationDepartment> delegationDepartments = new ArrayList<>();

    public List<User> getUsers() {
        return delegationUsers.stream()
                .map(DelegationUser::getUser)
                .collect(Collectors.toList());
    }

    public List<Department> getDepartments() {
        return delegationDepartments.stream()
                .map(DelegationDepartment::getDepartment)
                .collect(Collectors.toList());
    }

    @OneToMany(mappedBy = "delegation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("delegation")
    private List<Expense> expenses = new ArrayList<>();

    @OneToMany(mappedBy = "delegation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("delegation")
    private List<WorkLog> workLogs = new ArrayList<>();


    public Delegation() { }

    public Delegation(String title, String origin, String destination, DelegationStatus status, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.origin = origin;
        this.destination = destination;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Delegation(Long id, String title, String origin, String destination, DelegationStatus status, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.title = title;
        this.origin = origin;
        this.destination = destination;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {return id;}

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {this.origin = origin;}

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getStartDate() {return startDate;}

    public void setStartDate(LocalDateTime startDate) {this.startDate = startDate;}

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public DelegationStatus getStatus() {
        return status;
    }

    public void setStatus(DelegationStatus status) {
        this.status = status;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public List<DelegationUser> getDelegationUsers() {
        return delegationUsers;
    }

    public void setDelegationUsers(List<DelegationUser> delegationUsers) {
        this.delegationUsers = delegationUsers;
    }

    public List<DelegationDepartment> getDelegationDepartments() {
        return delegationDepartments;
    }

    public void setDelegationDepartments(List<DelegationDepartment> delegationDepartments) {
        this.delegationDepartments = delegationDepartments;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public List<WorkLog> getWorkLogs() {
        return workLogs;
    }

    public void setWorkLogs(List<WorkLog> workLogs) {
        this.workLogs = workLogs;
    }
}

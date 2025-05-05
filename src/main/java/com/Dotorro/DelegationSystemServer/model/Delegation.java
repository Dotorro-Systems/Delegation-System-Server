package com.Dotorro.DelegationSystemServer.model;
import com.Dotorro.DelegationSystemServer.enums.DelegationStatus;
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
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @ManyToOne
    @JoinColumn(name = "departmentId")
    private Department department;

    @OneToMany(mappedBy = "delegation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("delegation")
    private List<Note> notes = new ArrayList<>();

    @OneToMany(mappedBy = "delegation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("delegation")
    private List<Stage> stages = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "delegation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("delegation")
    private List<DelegationUser> delegationUsers = new ArrayList<>();

    @OneToMany(mappedBy = "delegation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("delegation")
    private List<Expense> expenses = new ArrayList<>();

    @OneToMany(mappedBy = "delegation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("delegation")
    private List<WorkLog> workLogs = new ArrayList<>();

    public Delegation() { }

    public Delegation(String title, String origin, String destination, LocalDateTime startDate, LocalDateTime endDate, Department department) {
        this.title = title;
        this.origin = origin;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.department = department;
    }

    public Delegation(Long id, String title, String origin, String destination, LocalDateTime startDate, LocalDateTime endDate, Department department) {
        this.id = id;
        this.title = title;
        this.origin = origin;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.department = department;
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

    public DelegationStatus getStatus() {
        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(endDate))
        {
            if (now.isAfter(startDate))
                return DelegationStatus.Active;

            return DelegationStatus.Planned;
        }

        return DelegationStatus.Finished;
    }

    public List<User> getUsers() {
        return delegationUsers.stream()
                .map(DelegationUser::getUser)
                .collect(Collectors.toList());
    }

    public List<Stage> getStages() {
        return stages;
    }

    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}

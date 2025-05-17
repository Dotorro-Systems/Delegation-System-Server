package com.Dotorro.DelegationSystemServer.dto;

import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.model.Department;
import com.Dotorro.DelegationSystemServer.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class CollectiveReportDTO {
    private LocalDate startPeriod;
    private LocalDate endPeriod;
    private String departmentName;
    private Map<Delegation, Long> delegationAllWorkHours;
    private Map<Delegation, Double> delegationAllExpenses;
    private Map<Delegation, List<User>> delegationAllUsers;
    private Double totalExpenses;
    private Long allWorkHours;

    public CollectiveReportDTO(LocalDate startPeriod, LocalDate endPeriod, Department department,
                               Map<Delegation, Long> delegationAllWorkHours, Map<Delegation, Double> delegationAllExpenses,
                               Map<Delegation, List<User>> delegationAllUsers,
                               Long allWorkHours, Double totalExpenses) {

        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.allWorkHours = allWorkHours;
        this.totalExpenses = totalExpenses;
        this.departmentName = department.getName();
        this.delegationAllWorkHours = delegationAllWorkHours;
        this.delegationAllExpenses = delegationAllExpenses;
        this.delegationAllUsers = delegationAllUsers;
    }

    public LocalDate getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(LocalDate startPeriod) {
        this.startPeriod = startPeriod;
    }

    public LocalDate getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(LocalDate endPeriod) {
        this.endPeriod = endPeriod;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Map<Delegation, Long> getDelegationAllWorkHours() {
        return delegationAllWorkHours;
    }

    public void setDelegationAllWorkHours(Map<Delegation, Long> delegationAllWorkHours) {
        this.delegationAllWorkHours = delegationAllWorkHours;
    }

    public Map<Delegation, Double> getDelegationAllExpenses() {
        return delegationAllExpenses;
    }

    public void setDelegationAllExpenses(Map<Delegation, Double> delegationAllExpenses) {
        this.delegationAllExpenses = delegationAllExpenses;
    }

    public Map<Delegation, List<User>> getDelegationAllUsers() {
        return delegationAllUsers;
    }

    public void setDelegationAllUsers(Map<Delegation, List<User>> delegationAllUsers) {
        this.delegationAllUsers = delegationAllUsers;
    }

    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(Double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public Long getAllWorkHours() {
        return allWorkHours;
    }

    public void setAllWorkHours(Long allWorkHours) {
        this.allWorkHours = allWorkHours;
    }
}


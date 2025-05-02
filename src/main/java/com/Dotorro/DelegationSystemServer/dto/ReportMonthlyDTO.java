package com.Dotorro.DelegationSystemServer.dto;

import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.model.Department;
import com.Dotorro.DelegationSystemServer.model.Note;
import com.Dotorro.DelegationSystemServer.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ReportMonthlyDTO {
    private String monthName;
    private Long year;
    private String departmentName;
    private Map<Delegation, Long> delegationAllWorkHours;
    private Map<Delegation, Double> delegationAllExpenses;
    private Map<Delegation, List<User>> delegationAllUsers;
    private Double totalExpenses;
    private Long allWorkHours;

    public ReportMonthlyDTO(String monthName, Long year, Department department, Delegation delegation,
                            Map<Delegation, Long> delegationAllWorkHours, Map<Delegation, Double> delegationAllExpenses,
                            Map<Delegation, List<User>> delegationAllUsers,
                            Long allWorkHours, Double totalExpenses) {

        this.monthName = monthName;
        this.year = year;
        this.allWorkHours = allWorkHours;
        this.totalExpenses = totalExpenses;
        this.departmentName = department.getName();
        this.delegationAllWorkHours = delegationAllWorkHours;
        this.delegationAllExpenses = delegationAllExpenses;
        this.delegationAllUsers = delegationAllUsers;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
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


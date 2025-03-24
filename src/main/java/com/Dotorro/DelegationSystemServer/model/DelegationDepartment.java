package com.Dotorro.DelegationSystemServer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "DelegationDepartments")
public class DelegationDepartment {
    @EmbeddedId
    private DelegationDepartmentKey id;

    @ManyToOne
    @MapsId("delegationId")
    @JoinColumn(name = "delegationId")
    @JsonIgnoreProperties("delegationDepartments")
    private Delegation delegation;

    @ManyToOne
    @MapsId("departmentId")
    @JoinColumn(name = "departmentId")
    @JsonIgnoreProperties("delegationDepartments")
    private Department department;

    public DelegationDepartment() {
    }

    public DelegationDepartment(DelegationDepartmentKey id, Delegation delegation, Department department) {
        this.id = id;
        this.delegation = delegation;
        this.department = department;
    }

    public DelegationDepartmentKey getId() {
        return id;
    }

    public void setId(DelegationDepartmentKey id) {
        this.id = id;
    }

    public Delegation getDelegation() {
        return delegation;
    }

    public void setDelegation(Delegation delegation) {
        this.delegation = delegation;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}

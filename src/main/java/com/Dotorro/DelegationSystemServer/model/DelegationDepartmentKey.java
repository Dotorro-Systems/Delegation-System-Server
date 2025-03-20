package com.Dotorro.DelegationSystemServer.model;
import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class DelegationDepartmentKey implements Serializable {
    @ManyToOne
    @JoinColumn(name = "delegationId")
    private Delegation delegation;

    @ManyToOne
    @JoinColumn(name = "departmentId")
    private Department department;

    public  DelegationDepartmentKey() { }

    public DelegationDepartmentKey(Delegation delegation, Department department) {
        this.delegation = delegation;
        this.department = department;
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

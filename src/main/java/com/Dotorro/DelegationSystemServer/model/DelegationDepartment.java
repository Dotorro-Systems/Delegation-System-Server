package com.Dotorro.DelegationSystemServer.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "DelegationDepartments")
public class DelegationDepartment {
    @EmbeddedId
    private DelegationDepartmentKey id;

    public DelegationDepartment() {
    }

    public DelegationDepartment(DelegationDepartmentKey id) {
        this.id = id;
    }

    public DelegationDepartmentKey getId() {
        return id;
    }

    public void setId(DelegationDepartmentKey id) {
        this.id = id;
    }
}

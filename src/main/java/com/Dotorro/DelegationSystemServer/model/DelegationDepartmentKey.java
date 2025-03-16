package com.Dotorro.DelegationSystemServer.model;
import jakarta.persistence.*;

@Embeddable
public class DelegationDepartmentKey  {

    @ManyToOne
    @JoinColumn(name = "delegationId")
    private Delegation delegation;

    @ManyToOne
    @JoinColumn(name = "departmentId")
    private Department department;

}

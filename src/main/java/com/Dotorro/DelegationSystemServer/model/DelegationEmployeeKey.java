package com.Dotorro.DelegationSystemServer.model;
import jakarta.persistence.*;

@Embeddable
public class DelegationEmployeeKey  {

    @ManyToOne
    @JoinColumn(name = "delegationId")
    private Delegation delegation;

    @ManyToOne
    @JoinColumn(name = "employeeId")
    private User employee;

}

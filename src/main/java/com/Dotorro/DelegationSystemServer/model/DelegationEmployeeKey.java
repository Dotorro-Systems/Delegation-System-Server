package com.Dotorro.DelegationSystemServer.model;


import jakarta.persistence.*;


@Embeddable
public class DelegationEmployeeKey  {

    @ManyToOne
    @JoinColumn(name = "delegationId")
    private Delegation delegation;

    @ManyToOne
    @JoinColumn(name = "EmployeeId")
    private User employee;

}

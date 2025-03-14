package com.Dotorro.DelegationSystemServer.model;
import jakarta.persistence.*;

@Embeddable
public class DelegationUserKey {

    @ManyToOne
    @JoinColumn(name = "delegationId")
    private Delegation delegation;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

}

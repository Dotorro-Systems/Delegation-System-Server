package com.Dotorro.DelegationSystemServer.model;
import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class DelegationUserKey implements Serializable {
    @ManyToOne
    @JoinColumn(name = "delegationId")
    private Delegation delegation;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public DelegationUserKey() { }

    public DelegationUserKey(Delegation delegation, User user) {
        this.delegation = delegation;
        this.user = user;
    }

    public Delegation getDelegation() {
        return delegation;
    }

    public void setDelegation(Delegation delegation) {
        this.delegation = delegation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

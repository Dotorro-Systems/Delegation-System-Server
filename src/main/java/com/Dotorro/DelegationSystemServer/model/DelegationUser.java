package com.Dotorro.DelegationSystemServer.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;


@Entity
@Table(name = "DelegationUsers")
public class DelegationUser {
    @EmbeddedId
    private DelegationUserKey id;

    @ManyToOne
    @MapsId("delegationId")
    @JoinColumn(name = "delegation_id")
    @JsonIgnoreProperties("delegationUsers")
    private Delegation delegation;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("delegationUsers")
    private User user;

    public DelegationUser() { }

    public DelegationUser(DelegationUserKey id, Delegation delegation, User user) {
        this.id = id;
        this.delegation = delegation;
        this.user = user;
    }

    public DelegationUserKey getId() {
        return id;
    }

    public void setId(DelegationUserKey id) {
        this.id = id;
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

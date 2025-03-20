package com.Dotorro.DelegationSystemServer.model;
import jakarta.persistence.*;


@Entity
@Table(name = "DelegationUsers")
public class DelegationUser {
    @EmbeddedId
    private DelegationUserKey id;

    public DelegationUser() { }

    public DelegationUser(DelegationUserKey id) {
        this.id = id;
    }

    public DelegationUserKey getId() {
        return id;
    }

    public void setId(DelegationUserKey id) {
        this.id = id;
    }
}

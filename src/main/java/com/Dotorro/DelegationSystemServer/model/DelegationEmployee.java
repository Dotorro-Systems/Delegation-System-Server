package com.Dotorro.DelegationSystemServer.model;
import jakarta.persistence.*;


@Entity
@Table(name = "DelegationEmployees")
public class DelegationEmployee {
    @EmbeddedId
    private DelegationEmployeeKey id;

    public DelegationEmployee() {
    }

    public DelegationEmployee(DelegationEmployeeKey id) {
        this.id = id;
    }

    public DelegationEmployeeKey getId() {
        return id;
    }

    public void setId(DelegationEmployeeKey id) {
        this.id = id;
    }
}

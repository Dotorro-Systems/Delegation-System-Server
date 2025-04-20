package com.Dotorro.DelegationSystemServer.model;

import com.Dotorro.DelegationSystemServer.enums.StageType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="Stages")
public class Stage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "delegationId")
    private Delegation delegation;
    private StageType type;
    private String place;
    private String description;
    private LocalDateTime when;

    public Stage() { }

    public Stage(Delegation delegation, StageType type, String place, String description, LocalDateTime when) {
        this.delegation = delegation;
        this.type = type;
        this.place = place;
        this.description = description;
        this.when = when;
    }

    public Stage(Long id, Delegation delegation, StageType type, String place, String description, LocalDateTime when) {
        this.id = id;
        this.delegation = delegation;
        this.type = type;
        this.place = place;
        this.description = description;
        this.when = when;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StageType getType() {
        return type;
    }

    public void setType(StageType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Delegation getDelegation() {
        return delegation;
    }

    public void setDelegation(Delegation delegation) {
        this.delegation = delegation;
    }

    public LocalDateTime getWhen() {
        return when;
    }

    public void setWhen(LocalDateTime when) {
        this.when = when;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}

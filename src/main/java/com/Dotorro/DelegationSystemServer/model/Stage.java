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
    private LocalDateTime time;

    public Stage() { }

    public Stage(Delegation delegation, StageType type, String place, String description, LocalDateTime time) {
        this.delegation = delegation;
        this.type = type;
        this.place = place;
        this.description = description;
        this.time = time;
    }

    public Stage(Long id, Delegation delegation, StageType type, String place, String description, LocalDateTime time) {
        this.id = id;
        this.delegation = delegation;
        this.type = type;
        this.place = place;
        this.description = description;
        this.time = time;
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

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}

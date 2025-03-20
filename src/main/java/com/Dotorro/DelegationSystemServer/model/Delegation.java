package com.Dotorro.DelegationSystemServer.model;
import com.Dotorro.DelegationSystemServer.utils.DelegationStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Delegations")
public class Delegation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String origin;
    private String destination;
    private DelegationStatus delegationStatus;
    private LocalDateTime startDate;
    private LocalDateTime endDate;


    public Delegation() { }

    public Delegation(String title, String origin, String destination, DelegationStatus delegationStatus, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.origin = origin;
        this.destination = destination;
        this.delegationStatus = delegationStatus;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Delegation(Long id, String title, String origin, String destination, DelegationStatus delegationStatus, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.title = title;
        this.origin = origin;
        this.destination = destination;
        this.delegationStatus = delegationStatus;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {return id;}

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {this.origin = origin;}

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getStartDate() {return startDate;}

    public void setStartDate(LocalDateTime startDate) {this.startDate = startDate;}

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public DelegationStatus getDelegationStatus() {
        return delegationStatus;
    }

    public void setDelegationStatus(DelegationStatus delegationStatus) {
        this.delegationStatus = delegationStatus;
    }
}

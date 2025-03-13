package com.Dotorro.DelegationSystemServer.model;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "delegations")
public class Delegation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String origin;
    private String destination;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Delegation() {}

    public Delegation(Long id, String title, String origin, String destination, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.title = title;
        this.origin = origin;
        this.destination = destination;
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

    public LocalDateTime getStart_date() {return startDate;}

    public void setStart_date(LocalDateTime startDate) {this.startDate = startDate;}

    public LocalDateTime getEnd_date() {
        return endDate;
    }

    public void setEnd_date(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}

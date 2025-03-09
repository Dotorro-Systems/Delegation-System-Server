package com.Dotorro.DelegationSystemServer.model;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "delegations")
public class Delegation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String origin;
    private String destination;
    private LocalDate start_date;
    private LocalDate end_date;

    public Delegation() {}

    public Delegation(Long id, String title, String origin, String destination, LocalDate start_date, LocalDate end_date) {
        this.id = id;
        this.title = title;
        this.origin = origin;
        this.destination = destination;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Long getId() {
        return id;
    }

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

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }
}

package com.Dotorro.DelegationSystemServer.model;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "WorkLogs")
public class WorkLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "delegationId")
    private Delegation delegation;
    @ManyToOne
    @JoinColumn(name = "userId")
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public WorkLog(Long id, Delegation delegation, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.delegation = delegation;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Delegation getDelegation() {
        return delegation;
    }

    public void setDelegation(Delegation delegation) {
        this.delegation = delegation;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}

package com.Dotorro.DelegationSystemServer.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private User user;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @OneToMany(mappedBy = "workLog", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("workLog")
    private List<WorkLogBreak> workLogBreaks = new ArrayList<>();

    public WorkLog() { }

    public WorkLog(Delegation delegation, User user, LocalDateTime startTime, LocalDateTime endTime) {
        this.delegation = delegation;
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public WorkLog(Long id, Delegation delegation, User user, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.delegation = delegation;
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public List<WorkLogBreak> getWorkLogBreaks() {
        return workLogBreaks;
    }

    public void setWorkLogBreaks(List<WorkLogBreak> workLogBreaks) {
        this.workLogBreaks = workLogBreaks;
    }
}

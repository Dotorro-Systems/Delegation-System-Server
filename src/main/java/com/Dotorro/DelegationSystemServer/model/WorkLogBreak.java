package com.Dotorro.DelegationSystemServer.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "WorkLogBreaks")
public class WorkLogBreak {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "workLogId")
    private WorkLog workLog;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public WorkLogBreak() {
    }

    public WorkLogBreak(Long id, WorkLog workLog, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.workLog = workLog;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkLog getWorkLog() {
        return workLog;
    }

    public void setWorkLog(WorkLog workLog) {
        this.workLog = workLog;
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

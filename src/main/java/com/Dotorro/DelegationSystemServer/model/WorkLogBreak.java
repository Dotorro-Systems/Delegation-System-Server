package com.Dotorro.DelegationSystemServer.model;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "workLogBreaks")
public class WorkLogBreak {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long workLogId;
    private LocalDate startTime;
    private LocalDate endTime;

    public WorkLogBreak() {
    }

    public WorkLogBreak(Long id, Long workLogId, LocalDate startTime, LocalDate endTime) {
        this.id = id;
        this.workLogId = workLogId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkLogId() {
        return workLogId;
    }

    public void setWorkLogId(Long workLogId) {
        this.workLogId = workLogId;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }
}

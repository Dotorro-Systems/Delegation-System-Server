package com.Dotorro.DelegationSystemServer.model;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "workLogBreaks")
public class WorkLogBreak {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long workLogId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public WorkLogBreak() {
    }

    public WorkLogBreak(Long id, Long workLogId, LocalDateTime startTime, LocalDateTime endTime) {
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

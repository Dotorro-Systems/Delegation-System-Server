package com.Dotorro.DelegationSystemServer.dto;

import com.Dotorro.DelegationSystemServer.model.WorkLog;

import java.time.LocalDateTime;

public class WorkLogBreakDTO {
    private Long workLogId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public WorkLogBreakDTO(Long workLogId, LocalDateTime startTime, LocalDateTime endTime) {
        this.workLogId = workLogId;
        this.startTime = startTime;
        this.endTime = endTime;
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

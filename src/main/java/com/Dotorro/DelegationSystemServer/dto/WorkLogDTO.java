package com.Dotorro.DelegationSystemServer.dto;

import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

public class WorkLogDTO {
    private Long delegationId;
    private Long userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public WorkLogDTO(Long delegationId, Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        this.delegationId = delegationId;
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getDelegationId() {
        return delegationId;
    }

    public void setDelegationId(Long delegationId) {
        this.delegationId = delegationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

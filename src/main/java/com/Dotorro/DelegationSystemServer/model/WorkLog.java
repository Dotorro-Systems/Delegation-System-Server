package com.Dotorro.DelegationSystemServer.model;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "workLogs")
public class WorkLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long delegationId;
    private Long userId;
    private String password;
    private String email;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public WorkLog() {
    }

    public WorkLog(Long id, Long delegationId, Long userId, String password, String email, LocalDateTime startTime,
                   LocalDateTime endTime) {
        this.id = id;
        this.delegationId = delegationId;
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

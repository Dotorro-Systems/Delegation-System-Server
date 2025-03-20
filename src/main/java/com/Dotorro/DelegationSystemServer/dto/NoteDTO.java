package com.Dotorro.DelegationSystemServer.dto;

import java.time.LocalDateTime;

public class NoteDTO {
    private Long delegationId;
    private Long userId;
    private String content;
    private LocalDateTime createdAt;

    public NoteDTO(Long delegationId, Long userId, String content, LocalDateTime createdAt) {
        this.delegationId = delegationId;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

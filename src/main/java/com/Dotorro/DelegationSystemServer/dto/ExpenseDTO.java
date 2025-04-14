package com.Dotorro.DelegationSystemServer.dto;



import java.time.LocalDateTime;

public class ExpenseDTO {
    private Long delegationId;
    private Long userId;
    private String description;
    private Double amount;
    private LocalDateTime createdAt;

    public ExpenseDTO(Long delegationId, Long userId, String description, Double amount, LocalDateTime createdAt) {
        this.delegationId = delegationId;
        this.userId = userId;
        this.description = description;
        this.amount = amount;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

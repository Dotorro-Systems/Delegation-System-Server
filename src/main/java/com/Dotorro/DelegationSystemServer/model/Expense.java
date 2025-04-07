package com.Dotorro.DelegationSystemServer.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "delegationId")
    private Delegation delegation;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    private String description;
    private Double amount;
    private LocalDateTime createAt;

    public Expense() { }

    public Expense(Delegation delegation, User user, String description, Double amount, LocalDateTime createAt) {
        this.delegation = delegation;
        this.user = user;
        this.description = description;
        this.amount = amount;
        this.createAt = createAt;
    }

    public Expense(Long id, Delegation delegation, User user, String description, Double amount, LocalDateTime createAt) {
        this.id = id;
        this.delegation = delegation;
        this.user = user;
        this.description = description;
        this.amount = amount;
        this.createAt = createAt;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
}

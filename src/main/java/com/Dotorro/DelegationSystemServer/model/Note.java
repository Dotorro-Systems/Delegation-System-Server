package com.Dotorro.DelegationSystemServer.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long delegationId;
    private Long userId;
    private String content;
    private LocalDateTime createdAt;

    public Note() {}

    public Note(Long id, Long delegationId, Long userId, String content, LocalDateTime createdAt) {
        this.id = id;
        this.delegationId = delegationId;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public Long getDelegationId() {return delegationId;}

    public void setDelegationId(Long delegationId) {this.delegationId = delegationId;}

    public Long getUserId() {return userId;}

    public void setUserId(Long userId) {this.userId = userId;}

    public String getContent() {return content;}

    public void setContent(String content) {this.content = content;}

    public LocalDateTime getCreatedAt() {return createdAt;}

    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}}

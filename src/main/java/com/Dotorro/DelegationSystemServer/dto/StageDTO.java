package com.Dotorro.DelegationSystemServer.dto;

import java.time.LocalDateTime;

public class StageDTO {
    private Long delegationId;
    private String type;
    private String place;
    private String description;
    private LocalDateTime when;

    public StageDTO() { }

    public StageDTO(Long delegationId, String type, String place, String description, LocalDateTime when) {
        this.delegationId = delegationId;
        this.type = type;
        this.place = place;
        this.description = description;
        this.when = when;
    }

    public Long getDelegationId() {
        return delegationId;
    }

    public void setDelegationId(Long delegationId) {
        this.delegationId = delegationId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getWhen() {
        return when;
    }

    public void setWhen(LocalDateTime when) {
        this.when = when;
    }
}

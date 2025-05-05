package com.Dotorro.DelegationSystemServer.dto;

import java.time.LocalDateTime;

public class StageDTO {
    private Long delegationId;
    private String type;
    private String place;
    private String description;
    private LocalDateTime time;

    public StageDTO(Long delegationId, String type, String place, String description, LocalDateTime time) {
        this.delegationId = delegationId;
        this.type = type;
        this.place = place;
        this.description = description;
        this.time = time;
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

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}

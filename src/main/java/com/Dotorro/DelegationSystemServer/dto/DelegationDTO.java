package com.Dotorro.DelegationSystemServer.dto;

import java.time.LocalDateTime;

public class DelegationDTO {
    private String title;
    private String origin;
    private String destination;
    private String status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public DelegationDTO(String title, String origin, String destination, String status, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.origin = origin;
        this.destination = destination;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}

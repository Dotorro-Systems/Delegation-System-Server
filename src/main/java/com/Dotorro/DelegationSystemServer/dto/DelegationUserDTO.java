package com.Dotorro.DelegationSystemServer.dto;

public class DelegationUserDTO {
    private Long delegationId;
    private Long userId;

    public DelegationUserDTO() { }

    public DelegationUserDTO(Long delegationId, Long userId) {
        this.delegationId = delegationId;
        this.userId = userId;
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
}

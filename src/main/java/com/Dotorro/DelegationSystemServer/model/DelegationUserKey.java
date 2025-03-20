package com.Dotorro.DelegationSystemServer.model;
import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class DelegationUserKey implements Serializable {
    @Column(name = "delegationId")
    private Long delegationId;

    @Column(name = "userId")
    private Long userId;

    public DelegationUserKey() { }

    public DelegationUserKey(Long delegationId, Long userId) {
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

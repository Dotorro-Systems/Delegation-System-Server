package com.Dotorro.DelegationSystemServer.model;
import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class DelegationDepartmentKey implements Serializable {
    @Column(name = "delegationId")
    private Long delegationId;

    @Column(name = "departmentId")
    private Long departmentId;

    public  DelegationDepartmentKey() { }

    public DelegationDepartmentKey(Long delegationId, Long departmentId) {
        this.delegationId = delegationId;
        this.departmentId = departmentId;
    }

    public Long getDelegationId() {
        return delegationId;
    }

    public void setDelegationId(Long delegationId) {
        this.delegationId = delegationId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}

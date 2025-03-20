package com.Dotorro.DelegationSystemServer.dto;

public class DelegationDepartmentDTO {
    private Long delegationId;
    private Long departmentId;

    public DelegationDepartmentDTO(Long delegationId, Long departmentId) {
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

package com.Dotorro.DelegationSystemServer.repository;

import com.Dotorro.DelegationSystemServer.model.DelegationDepartment;
import com.Dotorro.DelegationSystemServer.model.DelegationDepartmentKey;
import com.Dotorro.DelegationSystemServer.model.DelegationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DelegationDepartmentRepository extends JpaRepository<DelegationDepartment, DelegationDepartmentKey> {
    public List<DelegationDepartment> findByDepartmentId(Long departmentId);
}

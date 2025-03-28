package com.Dotorro.DelegationSystemServer.repository;

import com.Dotorro.DelegationSystemServer.model.DelegationDepartment;
import com.Dotorro.DelegationSystemServer.model.DelegationDepartmentKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DelegationDepartmentRepository extends JpaRepository<DelegationDepartment, DelegationDepartmentKey> {
}

package com.Dotorro.DelegationSystemServer.repository;

import com.Dotorro.DelegationSystemServer.model.Expense;
import com.Dotorro.DelegationSystemServer.model.WorkLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkLogRepository extends JpaRepository<WorkLog,Long> {
    public List<WorkLog> findByDelegationId(Long delegationId);
}

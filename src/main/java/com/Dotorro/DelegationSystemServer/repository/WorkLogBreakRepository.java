package com.Dotorro.DelegationSystemServer.repository;

import com.Dotorro.DelegationSystemServer.model.WorkLogBreak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkLogBreakRepository extends JpaRepository<WorkLogBreak,Long> {
}

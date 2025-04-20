package com.Dotorro.DelegationSystemServer.repository;

import com.Dotorro.DelegationSystemServer.model.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {
    public List<Stage> findByDelegationId(Long delegationId);
}

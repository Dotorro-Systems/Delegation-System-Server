package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.model.WorkLogBreak;
import com.Dotorro.DelegationSystemServer.repository.WorkLogBreakRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkLogBreakService {
    private final WorkLogBreakRepository workLogBreakRepository;

    public WorkLogBreakService(WorkLogBreakRepository workLogBreakRepository) {
        this.workLogBreakRepository = workLogBreakRepository;
    }

    public List<WorkLogBreak> getAllWorkLogBreaks() {
        return workLogBreakRepository.findAll();
    }

    public WorkLogBreak createWorkLogBreak(WorkLogBreak workLogBreak) {
        return workLogBreakRepository.save(workLogBreak);
    }
}

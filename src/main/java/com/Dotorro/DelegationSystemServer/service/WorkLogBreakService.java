package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.dto.WorkLogBreakDTO;
import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.model.Department;
import com.Dotorro.DelegationSystemServer.model.WorkLog;
import com.Dotorro.DelegationSystemServer.model.WorkLogBreak;
import com.Dotorro.DelegationSystemServer.repository.WorkLogBreakRepository;
import org.springframework.stereotype.Service;

import java.lang.module.FindException;
import java.util.List;

@Service
public class WorkLogBreakService {
    private final WorkLogBreakRepository workLogBreakRepository;
    private final WorkLogService workLogService;

    public WorkLogBreakService(WorkLogBreakRepository workLogBreakRepository, WorkLogService workLogService) {
        this.workLogBreakRepository = workLogBreakRepository;
        this.workLogService = workLogService;
    }

    public List<WorkLogBreak> getAllWorkLogBreaks() {
        return workLogBreakRepository.findAll();
    }

    public WorkLogBreak getWorkLogBreakById(Long workLogId)
    {
        return workLogBreakRepository.findById(workLogId).orElse(null);
    }

    public WorkLogBreak createWorkLogBreak(WorkLogBreakDTO workLogBreakDTO) {
        return workLogBreakRepository.save(convertToEntity(workLogBreakDTO));
    }

    private WorkLogBreak convertToEntity(WorkLogBreakDTO workLogBreakDTO) {
        WorkLog workLog = workLogService.getWorkLogById(workLogBreakDTO.getWorkLogId());

        return new WorkLogBreak(
                workLog,
                workLogBreakDTO.getStartTime(),
                workLogBreakDTO.getEndTime()
        );
    }

    private WorkLogBreakDTO convertToDTO(WorkLogBreak workLogBreak)
    {
        return new WorkLogBreakDTO(
                workLogBreak.getWorkLog().getId(),
                workLogBreak.getStartTime(),
                workLogBreak.getStartTime()
        );
    }
}

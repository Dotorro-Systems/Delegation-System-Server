package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.model.WorkLog;
import com.Dotorro.DelegationSystemServer.repository.WorkLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkLogService {
    private final WorkLogRepository workLogRepository;

    public WorkLogService(WorkLogRepository workLogRepository) {
        this.workLogRepository = workLogRepository;
    }

    public List<WorkLog> getAllWorkLogs() {
        return workLogRepository.findAll();
    }

    public WorkLog createWorkLog(WorkLog workLog) {
        return workLogRepository.save(workLog);
    }
}

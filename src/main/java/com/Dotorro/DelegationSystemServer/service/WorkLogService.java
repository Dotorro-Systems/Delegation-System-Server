package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.dto.UserDTO;
import com.Dotorro.DelegationSystemServer.dto.WorkLogDTO;
import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.model.Department;
import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.model.WorkLog;
import com.Dotorro.DelegationSystemServer.repository.UserRepository;
import com.Dotorro.DelegationSystemServer.repository.WorkLogRepository;
import org.springframework.stereotype.Service;

import java.lang.module.FindException;
import java.util.List;

@Service
public class WorkLogService {
    private final WorkLogRepository workLogRepository;
    private final UserService userService;
    private final DelegationService delegationService;

    public WorkLogService(WorkLogRepository workLogRepository, UserService userService, DelegationService delegationService) {
        this.workLogRepository = workLogRepository;
        this.userService = userService;
        this.delegationService = delegationService;
    }

    public List<WorkLog> getAllWorkLogs() {
        return workLogRepository.findAll();
    }

    public WorkLog getWorkLogById(Long workLogId)
    {
        return workLogRepository.findById(workLogId).orElse(null);
    }

    public WorkLog createWorkLog(WorkLogDTO workLogDTO) {
        return workLogRepository.save(convertToEntity(workLogDTO));
    }

    private WorkLog convertToEntity(WorkLogDTO workLogDTO) {
        User user = userService.getUserById(workLogDTO.getUserId());

        Delegation delegation = delegationService.getDelegationById(workLogDTO.getDelegationId());

        return new WorkLog(
                delegation,
                user,
                workLogDTO.getStartTime(),
                workLogDTO.getEndTime()
        );
    }

    private WorkLogDTO convertToDTO(WorkLog workLog)
    {
        return new WorkLogDTO(
                workLog.getDelegation().getId(),
                workLog.getUser().getId(),
                workLog.getStartTime(),
                workLog.getEndTime()
        );
    }
}

package com.Dotorro.DelegationSystemServer.service;


import com.Dotorro.DelegationSystemServer.dto.WorkLogDTO;
import com.Dotorro.DelegationSystemServer.model.Delegation;

import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.model.WorkLog;
import com.Dotorro.DelegationSystemServer.repository.WorkLogRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public void validateWorkLog(WorkLog workLog) {
        if (workLog.getStartTime().isBefore(workLog.getDelegation().getStartDate())) {
            throw new IllegalArgumentException("Start time cannot be earlier than the delegation start date");
        }

        if (workLog.getEndTime().isBefore(workLog.getStartTime())) {
            throw new IllegalArgumentException("End time cannot be earlier than the start time");
        }

        if (workLog.getUser() == null) {
            throw new RuntimeException("User not found");
        }

        if (workLog.getDelegation() == null) {
            throw new RuntimeException("Delegation not found");
        }
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

    public WorkLog updateWorkLog(Long id, WorkLogDTO workLogDTO)
    {
        Optional<WorkLog> optionalWorkLog = workLogRepository.findById(id);

        WorkLog updatedWorkLog = convertToEntity(workLogDTO);

        if (optionalWorkLog.isPresent()) {
            WorkLog workLog = optionalWorkLog.get();

            workLog.setDelegation(updatedWorkLog.getDelegation());
            workLog.setUser(updatedWorkLog.getUser());
            workLog.setStartTime(updatedWorkLog.getStartTime());
            workLog.setEndTime(updatedWorkLog.getEndTime());

            return workLogRepository.save(workLog);
        } else {
            throw new RuntimeException("WorkLog not found with id: " + id);
        }
    }

    public void deleteWorkLog(Long id)
    {
        workLogRepository.deleteById(id);
    }

    private WorkLog convertToEntity(WorkLogDTO workLogDTO) {
        User user = userService.getUserById(workLogDTO.getUserId());

        Delegation delegation = delegationService.getDelegationById(workLogDTO.getDelegationId());

        WorkLog workLog = new WorkLog(
                delegation,
                user,
                workLogDTO.getStartTime(),
                workLogDTO.getEndTime()
        );
        validateWorkLog(workLog);
        return workLog;
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

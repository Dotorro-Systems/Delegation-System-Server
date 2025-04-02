package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.dto.UserDTO;
import com.Dotorro.DelegationSystemServer.dto.WorkLogDTO;
import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.model.Department;
import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.model.WorkLog;
import com.Dotorro.DelegationSystemServer.repository.UserRepository;
import com.Dotorro.DelegationSystemServer.repository.WorkLogRepository;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Service;

import java.lang.module.FindException;
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

    public void workLogValidation(WorkLogDTO workLogDTO){
        if(workLogDTO.getStartTime().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("time can't be from the past!");
        }
        if(workLogDTO.getEndTime().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("time can't be from the past!");
        }
        if(workLogDTO.getEndTime().isBefore(workLogDTO.getStartTime())){
            throw new IllegalArgumentException("The endTime cannot be earlier than the startTime");
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
        workLogValidation(workLogDTO);
        return workLogRepository.save(convertToEntity(workLogDTO));
    }

    public WorkLog updateWorkLog(Long id, WorkLogDTO workLogDTO)
    {
        Optional<WorkLog> optionalWorkLog = workLogRepository.findById(id);
        workLogValidation(workLogDTO);
        WorkLog updatedWorkLog = convertToEntity(workLogDTO);

        if (optionalWorkLog.isPresent()) {
            WorkLog workLog = optionalWorkLog.get();
            workLog.setDelegation(updatedWorkLog.getDelegation());
            workLog.setUser(updatedWorkLog.getUser());
            workLog.setStartTime(updatedWorkLog.getStartTime());
            workLog.setEndTime(updatedWorkLog.getEndTime());

            return workLogRepository.save(updatedWorkLog);
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

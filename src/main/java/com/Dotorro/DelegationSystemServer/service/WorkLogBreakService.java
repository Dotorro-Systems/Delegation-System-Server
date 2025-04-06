package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.dto.WorkLogBreakDTO;
import com.Dotorro.DelegationSystemServer.model.*;
import com.Dotorro.DelegationSystemServer.repository.WorkLogBreakRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class WorkLogBreakService {
    private final WorkLogBreakRepository workLogBreakRepository;
    private final WorkLogService workLogService;

    public WorkLogBreakService(WorkLogBreakRepository workLogBreakRepository, WorkLogService workLogService) {
        this.workLogBreakRepository = workLogBreakRepository;
        this.workLogService = workLogService;
    }

    public void validateWorkLogBreak(WorkLogBreak workLogBreak){

        if (workLogBreak.getWorkLog() == null){
            throw new RuntimeException("WorkLog not found with id: "+workLogBreak.getWorkLog());
        }
        if(workLogBreak.getEndTime().isAfter(workLogBreak.getWorkLog().getEndTime())) {
            workLogBreak.setEndTime(workLogBreak.getWorkLog().getEndTime());
        }
        if(workLogBreak.getStartTime().isBefore(workLogBreak.getWorkLog().getStartTime())) {
            workLogBreak.setStartTime(workLogBreak.getWorkLog().getStartTime());
        }
        if(workLogBreak.getEndTime().isBefore(workLogBreak.getStartTime())){
            throw new IllegalArgumentException("The end time cannot be earlier than the start time");
        }
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

    public WorkLogBreak updateWorkLogBreak(Long id, WorkLogBreakDTO workLogBreakDTO)
    {
        Optional<WorkLogBreak> optionalWorkLogBreak = workLogBreakRepository.findById(id);

        WorkLogBreak updatedWorkLogBreak = convertToEntity(workLogBreakDTO);

        if (optionalWorkLogBreak.isPresent()) {
            WorkLogBreak workLogBreak = optionalWorkLogBreak.get();
            workLogBreak.setWorkLog(updatedWorkLogBreak.getWorkLog());
            workLogBreak.setStartTime(updatedWorkLogBreak.getStartTime());
            workLogBreak.setEndTime(updatedWorkLogBreak.getEndTime());

            return workLogBreakRepository.save(workLogBreak);
        } else {
            throw new RuntimeException("WorkLogBreak not found with id: " + id);
        }
    }

    public void deleteWorkLogBreak(Long id)
    {
        workLogBreakRepository.deleteById(id);
    }


    private WorkLogBreak convertToEntity(WorkLogBreakDTO workLogBreakDTO) {
        WorkLog workLog = workLogService.getWorkLogById(workLogBreakDTO.getWorkLogId());

         WorkLogBreak workLogBreak = new WorkLogBreak(
                workLog,
                workLogBreakDTO.getStartTime(),
                workLogBreakDTO.getEndTime()
        );
        validateWorkLogBreak(workLogBreak);
        return workLogBreak;
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

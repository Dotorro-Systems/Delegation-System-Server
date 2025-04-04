package com.Dotorro.DelegationSystemServer.controller;

import com.Dotorro.DelegationSystemServer.dto.UserDTO;
import com.Dotorro.DelegationSystemServer.dto.WorkLogDTO;
import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.model.WorkLog;
import com.Dotorro.DelegationSystemServer.service.WorkLogService;
import org.hibernate.jdbc.Work;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/workLogs")
@CrossOrigin(origins = "*")
public class WorkLogController {
    private final WorkLogService workLogService;

    public WorkLogController(WorkLogService workLogService) {
        this.workLogService = workLogService;
    }

    @GetMapping(value = "/")
    public List<WorkLog> getWorkLogs() {
        return workLogService.getAllWorkLogs();
    }

    @GetMapping(value = "/{id}")
    public WorkLog getWorkLogById(@PathVariable Long id)
    {
        return workLogService.getWorkLogById(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateWorkLog(@PathVariable Long id, @RequestBody WorkLogDTO workLogDTO)
    {
        WorkLog savedWorkLog = workLogService.updateWorkLog(id, workLogDTO);
        return ResponseEntity.ok(savedWorkLog);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteWorkLogById(@PathVariable Long id)
    {
        workLogService.deleteWorkLog(id);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createWorkLog(@RequestBody WorkLogDTO workLogDTO) {
        try {
            WorkLog savedWorkLog = workLogService.createWorkLog(workLogDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedWorkLog);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

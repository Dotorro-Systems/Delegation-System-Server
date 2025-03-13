package com.Dotorro.DelegationSystemServer.controller;

import com.Dotorro.DelegationSystemServer.model.WorkLog;
import com.Dotorro.DelegationSystemServer.service.WorkLogService;
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

    @GetMapping
    public List<WorkLog> getWorkLogs() {
        return workLogService.getAllWorkLogs();
    }

    @PostMapping
    public WorkLog createWorkLog(@RequestBody WorkLog workLog) {
        return workLogService.createWorkLog(workLog);
    }
}

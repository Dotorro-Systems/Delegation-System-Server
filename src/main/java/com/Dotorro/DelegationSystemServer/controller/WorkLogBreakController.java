package com.Dotorro.DelegationSystemServer.controller;

import com.Dotorro.DelegationSystemServer.dto.WorkLogBreakDTO;
import com.Dotorro.DelegationSystemServer.model.WorkLogBreak;
import com.Dotorro.DelegationSystemServer.service.WorkLogBreakService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/workLogBreaks")
@CrossOrigin(origins = "*") // Allow frontend requests
public class WorkLogBreakController {
    private final WorkLogBreakService workLogBreakService;

    public WorkLogBreakController(WorkLogBreakService workLogBreakService) {
        this.workLogBreakService = workLogBreakService;
    }

    @GetMapping
    public List<WorkLogBreak> getWorkLogBreaks() {
        return workLogBreakService.getAllWorkLogBreaks();
    }

    @PostMapping
    public WorkLogBreak createWorkLogBreak(@RequestBody WorkLogBreakDTO workLogBreakDTO) {
        return workLogBreakService.createWorkLogBreak(workLogBreakDTO);
    }
}

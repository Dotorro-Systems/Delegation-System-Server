package com.Dotorro.DelegationSystemServer.controller;

import com.Dotorro.DelegationSystemServer.dto.WorkLogBreakDTO;
import com.Dotorro.DelegationSystemServer.model.WorkLog;
import com.Dotorro.DelegationSystemServer.model.WorkLogBreak;
import com.Dotorro.DelegationSystemServer.service.WorkLogBreakService;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(value = "/")
    public List<WorkLogBreak> getWorkLogBreaks() {
        return workLogBreakService.getAllWorkLogBreaks();
    }

    @GetMapping(value = "/{id}")
    public WorkLogBreak getWorkLogBreakById(@PathVariable Long id)
    {
        return workLogBreakService.getWorkLogBreakById(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateWorkLogBreak(@PathVariable Long id, @RequestBody WorkLogBreakDTO workLogBreakDTO)
    {
        try {
            WorkLogBreak savedWorkLogBreak = workLogBreakService.updateWorkLogBreak(id,workLogBreakDTO);
            return ResponseEntity.ok(savedWorkLogBreak);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    public void deleteWorkLogBreakById(@PathVariable Long id)
    {
        workLogBreakService.deleteWorkLogBreak(id);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createWorkLogBreak(@RequestBody WorkLogBreakDTO workLogBreakDTO) {
        try {
            WorkLogBreak savedWorkLogBreak = workLogBreakService.createWorkLogBreak(workLogBreakDTO);
            return ResponseEntity.ok(savedWorkLogBreak);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

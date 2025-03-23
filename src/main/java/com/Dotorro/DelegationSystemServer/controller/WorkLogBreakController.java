package com.Dotorro.DelegationSystemServer.controller;

import com.Dotorro.DelegationSystemServer.dto.UserDTO;
import com.Dotorro.DelegationSystemServer.dto.WorkLogBreakDTO;
import com.Dotorro.DelegationSystemServer.model.User;
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

    @GetMapping(value = "/all")
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
        WorkLogBreak savedWorkLogBreak = workLogBreakService.updateWorkLogBreak(id,workLogBreakDTO);
        return ResponseEntity.ok(savedWorkLogBreak);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteWorkLogBreakById(@PathVariable Long id)
    {
        workLogBreakService.deleteWorkLogBrak(id);
    }

    @PostMapping(value = "/create")
    public WorkLogBreak createWorkLogBreak(@RequestBody WorkLogBreakDTO workLogBreakDTO) {
        return workLogBreakService.createWorkLogBreak(workLogBreakDTO);
    }
}

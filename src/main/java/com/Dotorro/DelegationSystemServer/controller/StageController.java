package com.Dotorro.DelegationSystemServer.controller;

import com.Dotorro.DelegationSystemServer.dto.StageDTO;
import com.Dotorro.DelegationSystemServer.model.Stage;
import com.Dotorro.DelegationSystemServer.service.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stages")
@CrossOrigin(origins = "*")
public class StageController {

    @Autowired
    private StageService stageService;

    @GetMapping(value = "/")
    public List<Stage> getAllStages() {
        return stageService.getAllStages();
    }

    @GetMapping(value = "/{id}")
    public Stage getStageById(@PathVariable Long id) {
        return stageService.getStageById(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateStage(@PathVariable Long id, @RequestBody StageDTO stageDTO)
    {
        try {
            Stage savedStage = stageService.updateStage(id, stageDTO);
            return ResponseEntity.ok(savedStage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    public void deleteNoteById(@PathVariable Long id) {
        stageService.deleteStage(id);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createNote(@RequestBody StageDTO stageDTO) {
        try {
            Stage savedStage = stageService.createStage(stageDTO);
            return ResponseEntity.ok(savedStage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

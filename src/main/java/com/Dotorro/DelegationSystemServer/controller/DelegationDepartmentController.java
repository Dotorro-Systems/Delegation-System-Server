package com.Dotorro.DelegationSystemServer.controller;

import com.Dotorro.DelegationSystemServer.dto.DelegationDepartmentDTO;
import com.Dotorro.DelegationSystemServer.dto.UserDTO;
import com.Dotorro.DelegationSystemServer.model.DelegationDepartment;
import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.service.DelegationDepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/delegationDepartments")
@CrossOrigin(origins = "*") // Allow frontend requests
public class DelegationDepartmentController {
    private final DelegationDepartmentService delegationDepartmentService;

    public DelegationDepartmentController(DelegationDepartmentService delegationDepartmentService) {
        this.delegationDepartmentService = delegationDepartmentService;
    }

    @GetMapping(value = "/")
    public List<DelegationDepartment> getDelegationDepartments() {
        return delegationDepartmentService.getAllDelegationDepartments();
    }

    @GetMapping(value = "/{delegationId}/{departmentId}")
    public DelegationDepartment getDelegationDepartmentByDelegationIdDepartmentId(@PathVariable Long delegationId, @PathVariable Long departmentId)
    {
        return delegationDepartmentService.getDelegationDepartmentByDelegationIdDepartmentId(delegationId,departmentId);
    }

    @PutMapping(value = "/{delegationId}/{departmentId}")
    public ResponseEntity<?> updateDelegationDepartment(@PathVariable Long delegationId, @PathVariable Long departmentId, @RequestBody DelegationDepartmentDTO delegationDepartmentDTO)
    {
        DelegationDepartment savedDelegationDepartment = delegationDepartmentService.updateDelegationDepartment(delegationId, departmentId, delegationDepartmentDTO);
        return ResponseEntity.ok(savedDelegationDepartment);
    }

    @DeleteMapping(value = "/{delegationId}/{departmentId}")
    public void deleteDelegationDepartmentByDelegationIdDepartmentId(@PathVariable Long delegationId, @PathVariable Long departmentId)
    {
        delegationDepartmentService.deleteDelegationDepartment(delegationId, departmentId);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createDelegationDepartment(@RequestBody DelegationDepartmentDTO delegationDepartmentDTO) {
        DelegationDepartment delegationDepartment = delegationDepartmentService.createDelegationDepartment(delegationDepartmentDTO);

        return ResponseEntity.ok(delegationDepartment);
    }
}

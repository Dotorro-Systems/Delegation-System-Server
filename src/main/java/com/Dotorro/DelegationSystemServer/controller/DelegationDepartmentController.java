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

    @GetMapping(value = "/all")
    public List<DelegationDepartment> getDelegationDepartments() {
        return delegationDepartmentService.getAllDelegationDepartments();
    }

    @GetMapping(value = "/{id}")
    public DelegationDepartment getDelegationDepartmentById(@PathVariable Long id)
    {
        return delegationDepartmentService.getDelegationDepartmentById(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateDelegationDepartment(@PathVariable Long id, @RequestBody DelegationDepartmentDTO delegationDepartmentDTO)
    {
        DelegationDepartment savedDelegationDepartment = delegationDepartmentService.updateDelegationDepartment(id, delegationDepartmentDTO);
        return ResponseEntity.ok(savedDelegationDepartment);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteDelegationDepartmentById(@PathVariable Long id)
    {
        delegationDepartmentService.deleteDelegationDepartment(id);
    }

    @PostMapping(value = "/create")
    public DelegationDepartment createDelegationDepartment(@RequestBody DelegationDepartmentDTO delegationDepartmentDTO) {
        return delegationDepartmentService.createDelegationDepartment(delegationDepartmentDTO);
    }
}

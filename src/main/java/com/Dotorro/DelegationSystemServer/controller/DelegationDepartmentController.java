package com.Dotorro.DelegationSystemServer.controller;

import com.Dotorro.DelegationSystemServer.dto.DelegationDepartmentDTO;
import com.Dotorro.DelegationSystemServer.model.DelegationDepartment;
import com.Dotorro.DelegationSystemServer.service.DelegationDepartmentService;
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

    @GetMapping
    public List<DelegationDepartment> getDelegationDepartments() {
        return delegationDepartmentService.getAllDelegationDepartments();
    }

    @PostMapping
    public DelegationDepartment createDelegationDepartment(@RequestBody DelegationDepartmentDTO delegationDepartmentDTO) {
        return delegationDepartmentService.createDelegationDepartment(delegationDepartmentDTO);
    }
}

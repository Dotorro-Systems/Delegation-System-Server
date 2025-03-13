package com.Dotorro.DelegationSystemServer.controller;

import com.Dotorro.DelegationSystemServer.model.DelegationEmployee;
import com.Dotorro.DelegationSystemServer.service.DelegationEmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/delegationEmployees")
@CrossOrigin(origins = "*") // Allow frontend requests
public class DelegationEmployeeController {
    private final DelegationEmployeeService delegationEmployeeService;

    public DelegationEmployeeController(DelegationEmployeeService delegationEmployeeService) {
        this.delegationEmployeeService = delegationEmployeeService;
    }

    @GetMapping
    public List<DelegationEmployee> getDelegationEmployees() {
        return delegationEmployeeService.getAllDelegationEmployees();
    }

    @PostMapping
    public DelegationEmployee createDelegationEmployee(@RequestBody DelegationEmployee delegationEmployee) {
        return delegationEmployeeService.createDelegationEmployee(delegationEmployee);
    }
}

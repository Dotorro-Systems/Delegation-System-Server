package com.Dotorro.DelegationSystemServer.controller;

import com.Dotorro.DelegationSystemServer.dto.DepartmentDTO;
import com.Dotorro.DelegationSystemServer.dto.UserDTO;
import com.Dotorro.DelegationSystemServer.model.Department;
import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/departments")
@CrossOrigin(origins = "*") // Allow frontend requests
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping(value = "/")
    public List<Department> getDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping(value = "/{id}")
    public Department getDepartmentById(@PathVariable Long id)
    {
        return departmentService.getDepartmentById(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable Long id, @RequestBody DepartmentDTO departmentDTO)
    {
        Department savedDepartment = departmentService.updateDepartment(id, departmentDTO);
        return ResponseEntity.ok(savedDepartment);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteDepartmentById(@PathVariable Long id)
    {
        departmentService.deleteDepartment(id);
    }

    @PostMapping(value = "/create")
    public Department createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        return departmentService.createDepartment(departmentDTO);
    }
}
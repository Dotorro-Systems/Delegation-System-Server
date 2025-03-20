package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.dto.DelegationDTO;
import com.Dotorro.DelegationSystemServer.dto.DepartmentDTO;
import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.model.Department;
import com.Dotorro.DelegationSystemServer.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAllDepartments() {

        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Long departmentId)
    {
        return departmentRepository.findById(departmentId).orElse(null);
    }

    public Department createDepartment(DepartmentDTO departmentDTO) {
        return departmentRepository.save(convertToEntity(departmentDTO));
    }

    private Department convertToEntity(DepartmentDTO departmentDTO) {
        return new Department(
            departmentDTO.getName()
        );
    }

    private DepartmentDTO convertToDTO(Department department)
    {
        return new DepartmentDTO(
            department.getName()
        );
    }
}

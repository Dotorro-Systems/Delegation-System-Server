package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.dto.DepartmentDTO;
import com.Dotorro.DelegationSystemServer.model.Department;
import com.Dotorro.DelegationSystemServer.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public void validateDepartment(Department department){
        if (!department.getName().matches("^[\\p{L}-& ]+$")) {
            throw new IllegalArgumentException("Department must only contain letters");
        }
    }

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

    public Department updateDepartment(Long id, DepartmentDTO departmentDTO)
    {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);

        Department updatedDepartment = convertToEntity(departmentDTO);

        if (optionalDepartment.isPresent()) {
            Department department = optionalDepartment.get();
            department.setName(updatedDepartment.getName());

            return departmentRepository.save(department);
        } else {
            throw new RuntimeException("Department not found with id: " + id);
        }
    }

    public void deleteDepartment(Long id)
    {
        departmentRepository.deleteById(id);
    }

    private Department convertToEntity(DepartmentDTO departmentDTO) {
        Department department = new Department(
            departmentDTO.getName()
        );
        validateDepartment(department);
        return department;
    }

    private DepartmentDTO convertToDTO(Department department)
    {
        return new DepartmentDTO(
            department.getName()
        );
    }
}

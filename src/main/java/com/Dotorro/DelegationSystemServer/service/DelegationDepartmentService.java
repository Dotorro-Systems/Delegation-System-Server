package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.dto.DelegationDepartmentDTO;
import com.Dotorro.DelegationSystemServer.dto.UserDTO;
import com.Dotorro.DelegationSystemServer.model.*;
import com.Dotorro.DelegationSystemServer.repository.DelegationDepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DelegationDepartmentService {
    private final DelegationDepartmentRepository delegationDepartmentRepository;
    private final DelegationService delegationService;
    private final DepartmentService departmentService;

    public DelegationDepartmentService(DelegationDepartmentRepository delegationDepartmentRepository,
                                       DelegationService delegationService,
                                       DepartmentService departmentService) {
        this.delegationDepartmentRepository = delegationDepartmentRepository;
        this.delegationService = delegationService;
        this.departmentService = departmentService;
    }

    public List<DelegationDepartment> getAllDelegationDepartments() {
        return delegationDepartmentRepository.findAll();
    }

    public DelegationDepartment getDelegationDepartmentById(Long delegationDepartmentId) {
        return delegationDepartmentRepository.findById(delegationDepartmentId).orElse(null);
    }

    public DelegationDepartment createDelegationDepartment(DelegationDepartmentDTO delegationDepartmentDTO) {
        return delegationDepartmentRepository.save(convertToEntity(delegationDepartmentDTO));
    }

    public DelegationDepartment updateDelegationDepartment(Long id, DelegationDepartmentDTO delegationDepartmentDTO)
    {
        Optional<DelegationDepartment> optionalDelegationDepartment = delegationDepartmentRepository.findById(id);

        DelegationDepartment updatedDelegationDepartment = convertToEntity(delegationDepartmentDTO);

        if (optionalDelegationDepartment.isPresent()) {
            DelegationDepartment delegationDepartment = optionalDelegationDepartment.get();

            return delegationDepartmentRepository.save(delegationDepartment);
        } else {
            throw new RuntimeException("DelegationDepartment not found with id: " + id);
        }
    }

    public void deleteDelegationDepartment(Long id)
    {
        delegationDepartmentRepository.deleteById(id);
    }

    private DelegationDepartment convertToEntity(DelegationDepartmentDTO delegationUserDTO) {
        Delegation delegation = delegationService.getDelegationById(delegationUserDTO.getDelegationId());

        Department department = departmentService.getDepartmentById(delegationUserDTO.getDepartmentId());

        return new DelegationDepartment(
                new DelegationDepartmentKey(
                        delegation,
                        department
                )
        );
    }

    private DelegationDepartmentDTO convertToDTO(DelegationDepartment delegationDepartment)
    {
        return new DelegationDepartmentDTO(
                delegationDepartment.getId().getDelegation().getId(),
                delegationDepartment.getId().getDepartment().getId()
        );
    }
}

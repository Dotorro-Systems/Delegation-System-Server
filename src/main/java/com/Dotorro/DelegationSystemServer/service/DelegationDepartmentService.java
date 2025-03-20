package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.dto.DelegationDepartmentDTO;
import com.Dotorro.DelegationSystemServer.model.*;
import com.Dotorro.DelegationSystemServer.repository.DelegationDepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public DelegationDepartment createDelegationDepartment(DelegationDepartmentDTO delegationDepartmentDTO) {
        return delegationDepartmentRepository.save(convertToEntity(delegationDepartmentDTO));
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

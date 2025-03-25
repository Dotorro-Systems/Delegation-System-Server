package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.dto.DelegationDepartmentDTO;
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

    public DelegationDepartment getDelegationDepartmentByDelegationIdDepartmentId(Long delegationId, Long departmentId)
    {
        DelegationDepartmentKey id = new DelegationDepartmentKey(delegationId, departmentId);

        return delegationDepartmentRepository.findById(id).orElse(null);
    }

    public DelegationDepartment createDelegationDepartment(DelegationDepartmentDTO delegationDepartmentDTO) {
        return delegationDepartmentRepository.save(convertToEntity(delegationDepartmentDTO));
    }

    public DelegationDepartment updateDelegationDepartment(Long delegationId, Long departmentId, DelegationDepartmentDTO delegationDepartmentDTO)
    {
        DelegationDepartmentKey id = new DelegationDepartmentKey(delegationId, departmentId);

        Optional<DelegationDepartment> optionalDelegationDepartment = delegationDepartmentRepository.findById(id);

        DelegationDepartment updatedDelegationDepartment = convertToEntity(delegationDepartmentDTO);

        if (optionalDelegationDepartment.isPresent()) {
            DelegationDepartment delegationDepartment = optionalDelegationDepartment.get();
            delegationDepartment.setId(updatedDelegationDepartment.getId());

            return delegationDepartmentRepository.save(delegationDepartment);
        } else {
            throw new RuntimeException("DelegationDepartment not found with delegationId: " + delegationId + " and departmentId: " + departmentId);
        }
    }

    public void deleteDelegationDepartment(Long delegationId, Long departmentId)
    {
        DelegationDepartmentKey id = new DelegationDepartmentKey(delegationId, departmentId);

        delegationDepartmentRepository.deleteById(id);
    }

    private DelegationDepartment convertToEntity(DelegationDepartmentDTO delegationDepartmentDTO) {
        Delegation delegation = delegationService.getDelegationById(delegationDepartmentDTO.getDelegationId());
        Department department = departmentService.getDepartmentById(delegationDepartmentDTO.getDepartmentId());

        return new DelegationDepartment(
                new DelegationDepartmentKey(
                        delegationDepartmentDTO.getDelegationId(),
                        delegationDepartmentDTO.getDepartmentId()
                ),
                delegation,
                department
        );
    }

    private DelegationDepartmentDTO convertToDTO(DelegationDepartment delegationDepartment)
    {
        return new DelegationDepartmentDTO(
                delegationDepartment.getId().getDelegationId(),
                delegationDepartment.getId().getDepartmentId()
        );
    }
}

package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.model.DelegationDepartment;
import com.Dotorro.DelegationSystemServer.repository.DelegationDepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DelegationDepartmentService {
    private final DelegationDepartmentRepository delegationDepartmentRepository;

    public DelegationDepartmentService(DelegationDepartmentRepository delegationDepartmentRepository) {
        this.delegationDepartmentRepository = delegationDepartmentRepository;
    }

    public List<DelegationDepartment> getAllDelegationDepartments() {
        return delegationDepartmentRepository.findAll();
    }

    public DelegationDepartment createDelegationDepartment(DelegationDepartment delegationDepartment) {
        return delegationDepartmentRepository.save(delegationDepartment);
    }
}

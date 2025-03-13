package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.model.DelegationEmployee;
import com.Dotorro.DelegationSystemServer.repository.DelegationEmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DelegationEmployeeService {
    private final DelegationEmployeeRepository delegationEmployeeRepository;

    public DelegationEmployeeService(DelegationEmployeeRepository delegationEmployeeRepository) {
        this.delegationEmployeeRepository = delegationEmployeeRepository;
    }

    public List<DelegationEmployee> getAllDelegationEmployees() {
        return delegationEmployeeRepository.findAll();
    }

    public DelegationEmployee createDelegationEmployee(DelegationEmployee delegationEmployee) {
        return delegationEmployeeRepository.save(delegationEmployee);
    }
}

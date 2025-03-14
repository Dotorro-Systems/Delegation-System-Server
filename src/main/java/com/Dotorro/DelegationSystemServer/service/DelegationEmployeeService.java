package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.model.DelegationUser;
import com.Dotorro.DelegationSystemServer.repository.DelegationEmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DelegationEmployeeService {
    private final DelegationEmployeeRepository delegationEmployeeRepository;

    public DelegationEmployeeService(DelegationEmployeeRepository delegationEmployeeRepository) {
        this.delegationEmployeeRepository = delegationEmployeeRepository;
    }

    public List<DelegationUser> getAllDelegationEmployees() {
        return delegationEmployeeRepository.findAll();
    }

    public DelegationUser createDelegationEmployee(DelegationUser delegationUser) {
        return delegationEmployeeRepository.save(delegationUser);
    }
}

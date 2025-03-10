package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.repository.DelegationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DelegationService {
    private final DelegationRepository delegationsRepository;

    public DelegationService(DelegationRepository delegationsRepository) {
        this.delegationsRepository = delegationsRepository;}

    public List<Delegation> getAllDelegations(){ return delegationsRepository.findAll();}

    public Delegation createDelegation(Delegation delegation){return delegationsRepository.save(delegation);}
}

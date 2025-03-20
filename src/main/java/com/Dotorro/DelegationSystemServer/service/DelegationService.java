package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.dto.DelegationDTO;
import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.repository.DelegationRepository;
import com.Dotorro.DelegationSystemServer.utils.DelegationStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DelegationService {
    private final DelegationRepository delegationsRepository;

    public DelegationService(DelegationRepository delegationsRepository) {
        this.delegationsRepository = delegationsRepository;}

    public List<Delegation> getAllDelegations(){ return delegationsRepository.findAll();}

    public Delegation getDelegationById(Long delegationId) {
        return delegationsRepository.findById(delegationId).orElse(null);
    }

    public Delegation createDelegation(DelegationDTO delegationDTO){return delegationsRepository.save(convertToEntity(delegationDTO));}

    private Delegation convertToEntity(DelegationDTO delegationDTO) {
        return new Delegation(
                delegationDTO.getTitle(),
                delegationDTO.getOrigin(),
                delegationDTO.getDestination(),
                DelegationStatus.valueOf(delegationDTO.getStatus()),
                delegationDTO.getStartDate(),
                delegationDTO.getEndDate()
        );
    }

    private DelegationDTO convertToDTO(Delegation delegation)
    {
        return new DelegationDTO(
                delegation.getTitle(),
                delegation.getOrigin(),
                delegation.getDestination(),
                delegation.getStatus().toString(),
                delegation.getStartDate(),
                delegation.getEndDate()
        );
    }
}

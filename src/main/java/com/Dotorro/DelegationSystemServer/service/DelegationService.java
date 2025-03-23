package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.dto.DelegationDTO;
import com.Dotorro.DelegationSystemServer.dto.UserDTO;
import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.repository.DelegationRepository;
import com.Dotorro.DelegationSystemServer.utils.DelegationStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Delegation updateDelegation(Long id, DelegationDTO delegationDTO)
    {
        Optional<Delegation> optionalDelegation = delegationsRepository.findById(id);

        Delegation updatedDelegation = convertToEntity(delegationDTO);

        if (optionalDelegation.isPresent()) {
            Delegation delegation = optionalDelegation.get();
            delegation.setTitle(optionalDelegation.get().getTitle());
            delegation.setOrigin(optionalDelegation.get().getOrigin());
            delegation.setDestination(optionalDelegation.get().getDestination());
            delegation.setStatus(optionalDelegation.get().getStatus());
            delegation.setStartDate(optionalDelegation.get().getStartDate());
            delegation.setEndDate(optionalDelegation.get().getEndDate());

            return delegationsRepository.save(delegation);
        } else {
            throw new RuntimeException("Delegation not found with id: " + id);
        }
    }

    public void deleteDelegation(Long id)
    {
        delegationsRepository.deleteById(id);
    }

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

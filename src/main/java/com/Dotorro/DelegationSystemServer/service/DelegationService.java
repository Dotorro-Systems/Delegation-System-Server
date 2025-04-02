package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.dto.DelegationDTO;
import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.repository.DelegationRepository;
import com.Dotorro.DelegationSystemServer.utils.DelegationStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DelegationService {
    private final DelegationRepository delegationRepository;

    public DelegationService(DelegationRepository delegationRepository) {
        this.delegationRepository = delegationRepository;}

    public void delegationValidate(DelegationDTO delegationDTO){
        if(delegationDTO.getStartDate().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("date can't be from the past!");
        }
        if(delegationDTO.getEndDate().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("date can't be from the past!");
        }
        if(delegationDTO.getEndDate().isBefore(delegationDTO.getStartDate())){
            throw new IllegalArgumentException("The endDate cannot be earlier than the startDate");
        }
        if(!delegationDTO.getOrigin().matches("[A-Z][a-zA-Z]*")) {
            throw new IllegalArgumentException("The origin is not valid.");
        }
        if(!delegationDTO.getDestination().matches("[A-Z][a-zA-Z]*")) {
            throw new IllegalArgumentException("The destination is not valid.");
        }
    }

    public List<Delegation> getAllDelegations(){ return delegationRepository.findAll();}

    public Delegation getDelegationById(Long delegationId) {
        return delegationRepository.findById(delegationId).orElse(null);
    }

    public Delegation createDelegation(DelegationDTO delegationDTO){
        delegationValidate(delegationDTO);
        return delegationRepository.save(convertToEntity(delegationDTO));}

    public Delegation updateDelegation(Long id, DelegationDTO delegationDTO)
    {
        Optional<Delegation> optionalDelegation = delegationRepository.findById(id);
        delegationValidate(delegationDTO);
        Delegation updatedDelegation = convertToEntity(delegationDTO);

        if (optionalDelegation.isPresent()) {
            Delegation delegation = optionalDelegation.get();
            delegation.setTitle(optionalDelegation.get().getTitle());
            delegation.setOrigin(optionalDelegation.get().getOrigin());
            delegation.setDestination(optionalDelegation.get().getDestination());
            delegation.setStatus(optionalDelegation.get().getStatus());
            delegation.setStartDate(optionalDelegation.get().getStartDate());
            delegation.setEndDate(optionalDelegation.get().getEndDate());

            return delegationRepository.save(delegation);
        } else {
            throw new RuntimeException("Delegation not found with id: " + id);
        }
    }

    public void deleteDelegation(Long id)
    {
        delegationRepository.deleteById(id);
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

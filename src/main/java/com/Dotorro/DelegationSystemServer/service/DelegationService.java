package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.dto.DelegationDTO;
import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.repository.DelegationRepository;
import com.Dotorro.DelegationSystemServer.utils.DelegationStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DelegationService {
    private final DelegationRepository delegationRepository;

    public DelegationService(DelegationRepository delegationRepository) {
        this.delegationRepository = delegationRepository;}

    public void validateDelegation(Delegation delegation){

        if (delegation.getEndDate().isBefore(delegation.getStartDate())) {
            throw new IllegalArgumentException("The end date cannot be earlier than the start date.");
        }

        if (!delegation.getOrigin().matches("^[\\p{L}- ]+$")) {
            throw new IllegalArgumentException("The origin must only contain letters.");
        }

        if(!Character.isUpperCase(delegation.getOrigin().charAt(0))){
            throw new IllegalArgumentException("The origin must start with Capital letter.");
        }
      
        if (!delegation.getDestination().matches("^[\\p{L}- ]+$")) {
            throw new IllegalArgumentException("The destination must only contain letters.");
        }

        if(!Character.isUpperCase(delegation.getDestination().charAt(0))){
            throw new IllegalArgumentException("The destination must start with Capital letter.");
        }
      
        if (!delegation.getTitle().matches("^[\\p{L}- ]+$")) {
            throw new IllegalArgumentException("The title must only contain letters.");
        }

        if(!Character.isUpperCase(delegation.getTitle().charAt(0))){
            throw new IllegalArgumentException("The title must start with Capital letter.");
        }
    }

    public List<Delegation> getAllDelegations(){ return delegationRepository.findAll();}

    public Delegation getDelegationById(Long delegationId) {
        return delegationRepository.findById(delegationId).orElse(null);
    }

    public List<Delegation> getDelegationsByDepartmentId(Long departmentId) {
        return getAllDelegations()
                .stream()
                .filter(delegation -> delegation.getDepartments().stream()
                        .anyMatch(department -> department.getId().equals(departmentId)))
                .collect(Collectors.toList());
    }

    public Delegation createDelegation(DelegationDTO delegationDTO){
        return delegationRepository.save(convertToEntity(delegationDTO));}

    public Delegation updateDelegation(Long id, DelegationDTO delegationDTO)
    {
        Optional<Delegation> optionalDelegation = delegationRepository.findById(id);

        Delegation updatedDelegation = convertToEntity(delegationDTO);

        if (optionalDelegation.isPresent()) {
            Delegation delegation = optionalDelegation.get();
            delegation.setTitle(updatedDelegation.getTitle());
            delegation.setOrigin(updatedDelegation.getOrigin());
            delegation.setDestination(updatedDelegation.getDestination());
            delegation.setStatus(updatedDelegation.getStatus());
            delegation.setStartDate(updatedDelegation.getStartDate());
            delegation.setEndDate(updatedDelegation.getEndDate());

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
        Delegation delegation = new Delegation(
                delegationDTO.getTitle(),
                delegationDTO.getOrigin(),
                delegationDTO.getDestination(),
                DelegationStatus.valueOf(delegationDTO.getStatus()),
                delegationDTO.getStartDate(),
                delegationDTO.getEndDate()
        );

        validateDelegation(delegation);
        return delegation;
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

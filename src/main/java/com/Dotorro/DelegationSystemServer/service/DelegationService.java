package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.dto.DelegationDTO;
import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.model.Department;
import com.Dotorro.DelegationSystemServer.repository.DelegationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DelegationService {
    private final DelegationRepository delegationRepository;

    @Autowired
    private DepartmentService departmentService;

    public DelegationService(DelegationRepository delegationRepository) {
        this.delegationRepository = delegationRepository;}

    public void validateDelegation(Delegation delegation){

        if (delegation.getEndDate().isBefore(delegation.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be earlier than the start date");
        }

        if (!delegation.getOrigin().matches("^[\\p{L}- ]+$")) {
            throw new IllegalArgumentException("Origin must only contain letters");
        }

        if (!Character.isUpperCase(delegation.getOrigin().charAt(0))) {
            throw new IllegalArgumentException("The origin must start with capital letter.");
        }
      
        if (!delegation.getDestination().matches("^[\\p{L}- ]+$")) {
            throw new IllegalArgumentException("Destination must only contain letters");
        }

        if (!Character.isUpperCase(delegation.getDestination().charAt(0))) {
            throw new IllegalArgumentException("The destination must start with capital letter.");
        }
      
        if (!delegation.getTitle().matches("^[\\p{L}- ]+$")) {
            throw new IllegalArgumentException("Title must only contain letters");
        }

        if (!Character.isUpperCase(delegation.getTitle().charAt(0))) {
            throw new IllegalArgumentException("The title must start with capital letter.");
        }
    }

    public List<Delegation> getAllDelegations(){ return delegationRepository.findAll();}

    public Delegation getDelegationById(Long delegationId) {
        return delegationRepository.findById(delegationId).orElse(null);
    }

    public List<Delegation> getDelegationsByDepartmentId(Long departmentId) {
        return getAllDelegations()
                .stream()
                .filter(delegation -> delegation.getDepartment().getId().equals(departmentId))
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
            delegation.setStartDate(updatedDelegation.getStartDate());
            delegation.setEndDate(updatedDelegation.getEndDate());
            delegation.setDepartment(updatedDelegation.getDepartment());

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
        Department department = departmentService.getDepartmentById(delegationDTO.getDepartmentId());

        Delegation delegation = new Delegation(
                delegationDTO.getTitle(),
                delegationDTO.getOrigin(),
                delegationDTO.getDestination(),
                delegationDTO.getStartDate(),
                delegationDTO.getEndDate(),
                department
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
                delegation.getStartDate(),
                delegation.getEndDate(),
                delegation.getDepartment().getId()
        );
    }

    public List<Delegation> getDelegationsByStartDate(LocalDate startDate) {
        return getAllDelegations()
                .stream()
                .filter(delegation -> delegation.getStartDate().toLocalDate().isEqual(startDate))
                .collect(Collectors.toList());
    }
}

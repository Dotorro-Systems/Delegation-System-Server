package com.Dotorro.DelegationSystemServer.controller;

import com.Dotorro.DelegationSystemServer.dto.DelegationDTO;
import com.Dotorro.DelegationSystemServer.exceptions.ApiException;
import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.service.DelegationService;
import com.Dotorro.DelegationSystemServer.service.UserService;
import com.Dotorro.DelegationSystemServer.utils.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/delegations")
@CrossOrigin(origins = "*")
public class DelegationController {
    private final DelegationService delegationService;
    private final UserService userService;

    public DelegationController(DelegationService delegationService, UserService userService) {
        this.delegationService = delegationService;
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public List<Delegation> getDelegations() {
        return delegationService.getAllDelegations();
    }

    @GetMapping(value = "/{id}")
    public Delegation getDelegationById(@PathVariable Long id, HttpServletRequest request) throws ApiException {
        User user = userService.getUserByRequest(request);

        Delegation delegation = delegationService.getDelegationById(id);

        boolean hasElevatedRights = user.getRole() != UserRole.EMPLOYEE;
        boolean participatesInDelegation = delegation.getUsers().stream().anyMatch(u -> u.getId().equals(user.getId()));
        boolean isInMyDepartment = delegation.getDepartments().stream().anyMatch(d -> d.getId().equals(user.getDepartment().getId()));

        if ((hasElevatedRights && !isInMyDepartment) || (!hasElevatedRights && !participatesInDelegation))
            throw new RuntimeException("You don't have permission to view this delegation");

        return delegation;
    }

    @GetMapping(value = "/in-my-department")
    public List<Delegation> getDelegationsInMyDepartment(HttpServletRequest request) throws ApiException {
        User user = userService.getUserByRequest(request);

        List<Delegation> allDelegationsInDepartment = delegationService.getDelegationsByDepartmentId(user.getDepartment().getId());

        if (user.getRole() == UserRole.EMPLOYEE)
            return allDelegationsInDepartment
                    .stream()
                    .filter(delegation -> delegation.getUsers().stream()
                            .anyMatch(u -> u.getId().equals(user.getId())))
                    .collect(Collectors.toList());

        return allDelegationsInDepartment;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateDelegation(@PathVariable Long id, @RequestBody DelegationDTO delegationDTO)
    {
        try {
            Delegation savedDelegation = delegationService.updateDelegation(id, delegationDTO);
            return ResponseEntity.ok(savedDelegation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    public void deleteDelegationById(@PathVariable Long id)
    {
        delegationService.deleteDelegation(id);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createDelegation(@RequestBody DelegationDTO delegationDTO){
        try {
            Delegation savedDelegation = delegationService.createDelegation(delegationDTO);
            return ResponseEntity.ok(savedDelegation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

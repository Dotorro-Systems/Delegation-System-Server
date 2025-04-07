package com.Dotorro.DelegationSystemServer.controller;

import com.Dotorro.DelegationSystemServer.dto.DelegationDTO;
import com.Dotorro.DelegationSystemServer.exceptions.ApiException;
import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.service.DelegationService;
import com.Dotorro.DelegationSystemServer.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/")
    public List<Delegation> getDelegations(){return delegationService.getAllDelegations();}

    @GetMapping(value = "/{id}")
    public Delegation getDelegationById(@PathVariable Long id, HttpServletRequest request) throws ApiException {
        User user = userService.getUserByRequest(request);

        Delegation delegation = delegationService.getDelegationById(id);

        if (!delegation.getUsers().contains(user))
            throw new RuntimeException("You don't have permission to view this delegation");

        return delegation;
    }

    @GetMapping(value = "/department/{id}")
    public List<Delegation> getDelegationByDepartmentId(@PathVariable Long id) {
        return delegationService.getDelegationsByDepartmentId(id);
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

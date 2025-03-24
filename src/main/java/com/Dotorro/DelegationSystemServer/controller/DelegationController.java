package com.Dotorro.DelegationSystemServer.controller;

import com.Dotorro.DelegationSystemServer.dto.DelegationDTO;
import com.Dotorro.DelegationSystemServer.dto.UserDTO;
import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.service.DelegationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/delegations")
@CrossOrigin(origins = "*") // Allow frontend requests
public class DelegationController {
    private final DelegationService delegationService;

    public DelegationController(DelegationService delegationService){this.delegationService = delegationService;}

    @GetMapping(value = "/")
    public List<Delegation> getDelegations(){return delegationService.getAllDelegations();}

    @GetMapping(value = "/{id}")
    public Delegation getDelegationById(@PathVariable Long id)
    {
        return delegationService.getDelegationById(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateDelegation(@PathVariable Long id, @RequestBody DelegationDTO delegationDTO)
    {
        Delegation savedDelegation = delegationService.updateDelegation(id, delegationDTO);
        return ResponseEntity.ok(savedDelegation);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteDelegationById(@PathVariable Long id)
    {
        delegationService.deleteDelegation(id);
    }

    @PostMapping(value = "/create")
    public Delegation createDelegation(@RequestBody DelegationDTO delegationDTO){
        return delegationService.createDelegation(delegationDTO);}
}

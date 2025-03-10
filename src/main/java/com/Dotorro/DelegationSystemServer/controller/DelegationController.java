package com.Dotorro.DelegationSystemServer.controller;

import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.service.DelegationService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/delegations")
@CrossOrigin(origins = "*") // Allow frontend requests
public class DelegationController {
    private final DelegationService delegationService;

    public DelegationController(DelegationService delegationService){this.delegationService = delegationService;}

    @GetMapping
    public List<Delegation> getDelegations(){return delegationService.getAllDelegations();}

    @PostMapping
    public Delegation createDelegation(@RequestBody Delegation delegation){
        return delegationService.createDelegation(delegation);}
}

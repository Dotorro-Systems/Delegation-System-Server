package com.Dotorro.DelegationSystemServer.controller;

import com.Dotorro.DelegationSystemServer.dto.DelegationUserDTO;
import com.Dotorro.DelegationSystemServer.model.DelegationUser;
import com.Dotorro.DelegationSystemServer.service.DelegationUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/delegationUsers")
@CrossOrigin(origins = "*") // Allow frontend requests
public class DelegationUserController {
    private final DelegationUserService delegationUserService;

    public DelegationUserController(DelegationUserService delegationUserService) {
        this.delegationUserService = delegationUserService;
    }

    @GetMapping
    public List<DelegationUser> getDelegationUsers() {
        return delegationUserService.getAllDelegationUsers();
    }

    @PostMapping
    public DelegationUser createDelegationEmployee(@RequestBody DelegationUserDTO delegationUserDTO) {
        return delegationUserService.createDelegationUser(delegationUserDTO);
    }
}

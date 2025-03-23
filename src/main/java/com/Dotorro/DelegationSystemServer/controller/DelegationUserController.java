package com.Dotorro.DelegationSystemServer.controller;

import com.Dotorro.DelegationSystemServer.dto.DelegationUserDTO;
import com.Dotorro.DelegationSystemServer.dto.UserDTO;
import com.Dotorro.DelegationSystemServer.model.DelegationUser;
import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.service.DelegationUserService;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(value = "/all")
    public List<DelegationUser> getDelegationUsers() {
        return delegationUserService.getAllDelegationUsers();
    }

    @GetMapping(value = "/{id}")
    public DelegationUser getDelegationUserById(@PathVariable Long id)
    {
        return delegationUserService.getDelegationUserById(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateDelegationUser(@PathVariable Long id, @RequestBody DelegationUserDTO delegationUserDTO)
    {
        DelegationUser savedDelegationUser = delegationUserService.updateDelegationUser(id, delegationUserDTO);
        return ResponseEntity.ok(savedDelegationUser);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteDelegationUserById(@PathVariable Long id)
    {
        delegationUserService.deleteDelegationUser(id);
    }

    @PostMapping(value = "/create")
    public DelegationUser createDelegationEmployee(@RequestBody DelegationUserDTO delegationUserDTO) {
        return delegationUserService.createDelegationUser(delegationUserDTO);
    }
}

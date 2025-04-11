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
@CrossOrigin(origins = "*")
public class DelegationUserController {
    private final DelegationUserService delegationUserService;

    public DelegationUserController(DelegationUserService delegationUserService) {
        this.delegationUserService = delegationUserService;
    }

    @GetMapping(value = "/")
    public List<DelegationUser> getDelegationUsers() {
        return delegationUserService.getAllDelegationUsers();
    }

    @GetMapping(value = "/{delegationId}/{userId}")
    public DelegationUser getDelegationUserByDelegationIdUserId(@PathVariable Long delegationId, @PathVariable Long userId)
    {
        return delegationUserService.getDelegationUserByDelegationIdUserId(delegationId, userId);
    }

    @PutMapping(value = "/{delegationId}/{userId}")
    public ResponseEntity<?> updateDelegationUser(@PathVariable Long delegationId, @PathVariable Long userId, @RequestBody DelegationUserDTO delegationUserDTO)
    {   try {
            DelegationUser savedDelegationUser = delegationUserService.updateDelegationUser(delegationId, userId, delegationUserDTO);
            return ResponseEntity.ok(savedDelegationUser);
        } catch (Exception e) {
             return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{delegationId}/{userId}")
    public void deleteDelegationUserById(@PathVariable Long delegationId, @PathVariable Long userId )
    {
        delegationUserService.deleteDelegationUser(delegationId, userId);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createDelegationUser(@RequestBody DelegationUserDTO delegationUserDTO) {
        try {
            DelegationUser savedDelegationUser = delegationUserService.createDelegationUser(delegationUserDTO);
            return ResponseEntity.ok(savedDelegationUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

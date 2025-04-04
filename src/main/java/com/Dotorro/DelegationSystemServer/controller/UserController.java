package com.Dotorro.DelegationSystemServer.controller;

import com.Dotorro.DelegationSystemServer.dto.UserDTO;
import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/{id}")
    public User getUserById(@PathVariable Long id)
    {
        return userService.getUserById(id);
    }

    @PostMapping(value = "/{id}/authenticate")
    public boolean authenticateUser(@PathVariable Long id, @RequestBody String password)
    {
        return userService.authenticateUser(id, password);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO)
    {
        User savedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(savedUser);
    }

    @PutMapping(value = "/{id}/password")
    public ResponseEntity<?> updateUserPassword(@PathVariable Long id, @RequestBody String newPassword)
    {
        User savedUser = userService.updatePassword(id, newPassword);
        return ResponseEntity.ok(savedUser);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteUserById(@PathVariable Long id)
    {
        userService.deleteUser(id);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        try {
            User savedUser = userService.createUser(userDTO);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

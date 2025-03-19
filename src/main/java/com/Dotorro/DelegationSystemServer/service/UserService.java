package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.dto.UserDTO;
import com.Dotorro.DelegationSystemServer.model.Department;
import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.repository.DepartmentRepository;
import com.Dotorro.DelegationSystemServer.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.lang.module.FindException;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final DepartmentRepository departmentRepository;

    public UserService(UserRepository userRepository, DepartmentRepository departmentRepository) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User createUser(UserDTO userDto) {
        return userRepository.save(convertToEntity(userDto));
    }

    private User convertToEntity(UserDTO userDto) {
        Department department = departmentRepository
                .findById(userDto.getDepartmentId())
                .orElseThrow(() -> new FindException("Department not found"));

        return new User(
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getHashedPassword(),
                userDto.getEmail(),
                userDto.getRole(),
                department
        );
    }

    private UserDTO convertToDTO(User user)
    {
        return new UserDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getHashedPassword(),
                user.getEmail(),
                user.getRole(),
                user.getDepartment().getId()
        );
    }
}

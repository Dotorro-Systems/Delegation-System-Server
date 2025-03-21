package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.dto.UserDTO;
import com.Dotorro.DelegationSystemServer.model.Department;
import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.repository.DepartmentRepository;
import com.Dotorro.DelegationSystemServer.repository.UserRepository;
import com.Dotorro.DelegationSystemServer.utils.UserRole;
import org.springframework.stereotype.Service;

import java.lang.module.FindException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final DepartmentService departmentService;

    public UserService(UserRepository userRepository, DepartmentService departmentService) {
        this.userRepository = userRepository;
        this.departmentService = departmentService;
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

    public User updateUser(Long id, UserDTO userDTO)
    {
        Optional<User> optionalUser = userRepository.findById(id);

        User updatedUser = convertToEntity(userDTO);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setHashedPassword(updatedUser.getHashedPassword());
            user.setEmail(updatedUser.getEmail());
            user.setRole(updatedUser.getRole());
            user.setDepartment(updatedUser.getDepartment());

            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    public void deleteUser(Long id)
    {
        userRepository.deleteById(id);
    }

    private User convertToEntity(UserDTO userDTO) {
        Department department = departmentService.getDepartmentById(userDTO.getDepartmentId());

        return new User(
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getHashedPassword(),
                userDTO.getPhone(),
                userDTO.getEmail(),
                UserRole.valueOf(userDTO.getRole()),
                department
        );
    }

    private UserDTO convertToDTO(User user)
    {
        return new UserDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getHashedPassword(),
                user.getPhone(),
                user.getEmail(),
                user.getRole().toString(),
                user.getDepartment().getId()
        );
    }
}

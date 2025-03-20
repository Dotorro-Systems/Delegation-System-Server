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
import java.util.Optional;

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

    public User updateUser(Long id, UserDTO userDto)
    {
        Optional<User> optionalUser = userRepository.findById(id);

        User updatedUser = convertToEntity(userDto);

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

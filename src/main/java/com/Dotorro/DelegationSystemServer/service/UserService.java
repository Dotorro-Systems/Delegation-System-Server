package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.dto.UserDTO;
import com.Dotorro.DelegationSystemServer.model.Department;
import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.repository.UserRepository;
import com.Dotorro.DelegationSystemServer.utils.UserRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final DepartmentService departmentService;
    private final AuthenticationService authenticationService;

    public UserService(UserRepository userRepository,
                       DepartmentService departmentService,
                       AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.departmentService = departmentService;
        this.authenticationService = authenticationService;
    }

    public void validateUser(UserDTO userDTO){
        if(!userDTO.getFirstName().matches("[A-Z][a-zA-Z]*")) {
            throw new IllegalArgumentException("First name must start with a capital letter and only contains letters");
        }
        if(!userDTO.getLastName().matches("[A-Z][a-zA-Z]*")) {
            throw new IllegalArgumentException("Last name must start with a capital letter and only contains letters");
        }
        if(!userDTO.getPhone().matches("\\d{9}")){
            throw new IllegalArgumentException("Phone number must only contain numbers.");
        }
        if (departmentService.getDepartmentById(userDTO.getDepartmentId()) == null){
            throw new RuntimeException("Department not found with id: "+userDTO.getDepartmentId());
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null)
            throw new NoSuchElementException("No user with provided email found");

        return user;
    }

    public User createUser(UserDTO userDto) {
        User user = convertToEntity(userDto);

        authenticationService.validateEmail(user.getEmail());
        authenticationService.validatePassword(user.getPassword());

        user.setPassword(authenticationService.hashPassword(user.getPassword()));

        return userRepository.save(user);
    }

    public User updateUser(Long id, UserDTO userDTO)
    {
        Optional<User> optionalUser = userRepository.findById(id);

        User updatedUser = convertToEntity(userDTO);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setPassword(updatedUser.getPassword());
            user.setEmail(updatedUser.getEmail());
            user.setPhone(updatedUser.getPhone());
            user.setRole(updatedUser.getRole());
            user.setDepartment(updatedUser.getDepartment());

            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    public User updatePassword(Long id, String newPassword)
    {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            authenticationService.validatePassword(newPassword);

            User user = optionalUser.get();
            user.setPassword(authenticationService.hashPassword(newPassword));

            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    public void deleteUser(Long id)
    {
        userRepository.deleteById(id);
    }

    public boolean authenticateUser(Long id, String password)
    {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            return authenticationService.matchPassword(password, user.getPassword());
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    public User convertToEntity(UserDTO userDTO) {
        validateUser(userDTO);
        Department department = departmentService.getDepartmentById(userDTO.getDepartmentId());

        return new User(
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getPassword(),
                userDTO.getPhone(),
                userDTO.getEmail(),
                UserRole.valueOf(userDTO.getRole()),
                department
        );
    }

    public UserDTO convertToDTO(User user)
    {
        return new UserDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getPassword(),
                user.getPhone(),
                user.getEmail(),
                user.getRole().toString(),
                user.getDepartment().getId()
        );
    }
}

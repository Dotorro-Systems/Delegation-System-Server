package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.dto.LoginRequestDTO;
import com.Dotorro.DelegationSystemServer.dto.UserDTO;
import com.Dotorro.DelegationSystemServer.exceptions.ApiException;
import com.Dotorro.DelegationSystemServer.model.Department;
import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.repository.UserRepository;
import com.Dotorro.DelegationSystemServer.utils.UserRole;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

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

    public void validateUser(User user){
        if(!user.getFirstName().matches("[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]*")) {
            throw new IllegalArgumentException("First name must start with a capital letter and only contains letters");
        }

        if(!user.getLastName().matches("[A-ZąćęłĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]*")) {
            throw new IllegalArgumentException("Last name must start with a capital letter and only contains letters");
        }

        if(!user.getPhone().matches("\\d{9}")) {
            throw new IllegalArgumentException("Phone number must only contain numbers.");
        }

        if (user.getDepartment() == null) {
            throw new RuntimeException("Department not found");
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

    public List<User> getUsersByDepartment(Long departmentId) {
        return userRepository.findByDepartmentId(departmentId);
    }

    public User registerUser(UserDTO userDto) throws NoSuchElementException {
        boolean foundUser = false;
        try {
            getUserByEmail(userDto.getEmail());
            foundUser = true;
        } catch (NoSuchElementException e) {

        }

        if (foundUser) {
            throw new NoSuchElementException("This email is already in use.");
        }

        User user = convertToEntity(userDto);

        authenticationService.validateEmail(user.getEmail());
        authenticationService.validatePassword(user.getPassword());

        user.setPassword(hashPassword(user.getPassword()));

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
            user.setPassword(hashPassword(newPassword));

            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    private String hashPassword(String password)
    {
        return encoder.encode(password);
    }

    public void deleteUser(Long id)
    {
        userRepository.deleteById(id);
    }

    public User convertToEntity(UserDTO userDTO) {
        Department department = departmentService.getDepartmentById(userDTO.getDepartmentId());

        User user = new User(
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getPassword(),
                userDTO.getPhone(),
                userDTO.getEmail(),
                UserRole.valueOf(userDTO.getRole()),
                department
        );

        validateUser(user);

        return user;
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

    public String verify(LoginRequestDTO loginRequestDTO) throws ApiException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(convertToDTO(getUserByEmail(loginRequestDTO.getEmail())));
        }

        throw new ApiException(HttpStatus.UNAUTHORIZED, "Cannot verify user");
    }

    public User getUserByRequest(HttpServletRequest request) throws ApiException {
        return getUserByEmail(jwtService.extractEmail(jwtService.getTokenFromRequest(request)));
    }
}

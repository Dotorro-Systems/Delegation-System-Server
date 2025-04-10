package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.dto.UserDTO;
import com.Dotorro.DelegationSystemServer.model.Department;
import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.repository.DepartmentRepository;
import com.Dotorro.DelegationSystemServer.repository.UserRepository;
import com.Dotorro.DelegationSystemServer.utils.UserRole;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private DepartmentService departmentService;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private JWTService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDTO userDTO;
    private Department department;

    @BeforeEach
    void setup() {
        department = new Department("Human & Resources");
        department.setId(1L);

        user = new User("John", "Doe", "password", "123456789", "john@example.com", UserRole.EMPLOYEE, department);
        user.setId(1L);

        userDTO = new UserDTO("John", "Doe", "password", "123456789", "john@example.com", UserRole.EMPLOYEE.toString(), 1L);
    }

    @Test
    void testRegisterUser() {
        when(departmentService.getDepartmentById(1L)).thenReturn(department);
        doNothing().when(authenticationService).validateEmail(userDTO.getEmail());
        doNothing().when(authenticationService).validatePassword(userDTO.getPassword());
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.registerUser(userDTO);

        assertNotNull(savedUser);
        assertEquals("John", savedUser.getFirstName());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testGetUserById_Found() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));

        User result = userService.getUserById(1L);
        assertEquals("John", result.getFirstName());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(2L)).thenReturn(null);

        User result = userService.getUserById(2L);
        assertNull(result);
    }

    @Test
    void testUpdateUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(departmentService.getDepartmentById(1L)).thenReturn(department);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updated = userService.updateUser(1L, userDTO);

        assertEquals("John", updated.getFirstName());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testUpdatePassword_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(authenticationService).validatePassword("newpass");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.updatePassword(1L, "newpass");

        assertNotNull(result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testGetUserByEmail_Found() {
        when(userRepository.findByEmail("john@example.com")).thenReturn(user);

        User result = userService.getUserByEmail("john@example.com");

        assertEquals("John", result.getFirstName());
    }

    @Test
    void testGetUserByEmail_NotFound() {
        when(userRepository.findByEmail("notfound@example.com")).thenReturn(null);

        assertThrows(NoSuchElementException.class, () -> {
            userService.getUserByEmail("notfound@example.com");
        });
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);
        userService.deleteUser(1L);
        verify(userRepository).deleteById(1L);
    }
}
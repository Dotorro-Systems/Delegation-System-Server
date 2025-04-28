package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.dto.UserDTO;
import com.Dotorro.DelegationSystemServer.model.Department;
import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.repository.UserRepository;
import com.Dotorro.DelegationSystemServer.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

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
    void shouldRegisterUser() {
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
    void shouldValidateUserWithValidPhone() {
        User user = new User("Jan", "Kowalski", "password", "111222569",
                "jan@example.com", UserRole.EMPLOYEE, new Department());

        assertDoesNotThrow(() -> userService.validateUser(user));
    }

    @Test
    void shouldThrowExceptionForNotEnoughNumbersPhone() {
        User user = new User("Jan", "Kowalski", "password", "123",
                "jan@example.com", UserRole.EMPLOYEE, new Department());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.validateUser(user);
        });
        assertEquals("Phone number must only contain 9 numbers", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForLettersInPhone() {
        User user = new User("Jan", "Kowalski", "password", "123pop555",
                "jan@example.com", UserRole.EMPLOYEE, new Department());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.validateUser(user);
        });

        assertEquals("Phone number must only contain 9 numbers", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForNullInPhone() {
        User user = new User("Jan", "Kowalski", "password", "",
                "jan@example.com", UserRole.EMPLOYEE, new Department());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.validateUser(user);
        });

        assertEquals("Phone number must only contain 9 numbers", exception.getMessage());
    }

    @Test
    void shouldValidateUserWithValidFirstName(){
        User user = new User("Jan", "Kwiatowska", "password", "123333444",
                "jan@example.com", UserRole.EMPLOYEE, new Department());

        assertDoesNotThrow(() -> userService.validateUser(user));
    }

    @Test
    void shouldValidateUserWithPolishLetterInFirstName(){
        User user = new User("Małgorzata", "Kwiatowska", "password", "123333444",
                "jan@example.com", UserRole.EMPLOYEE, new Department());

        assertDoesNotThrow(() -> userService.validateUser(user));
    }

    @Test
    void shouldValidateUserWithCapitalPolishLetterInFirstName(){
        User user = new User("Łucja", "Kwiatowska", "password", "123333444",
                "jan@example.com", UserRole.EMPLOYEE, new Department());

        assertDoesNotThrow(() -> userService.validateUser(user));
    }

    @Test
    void shouldThrowExceptionForNonCapitalLetterInFirstName() {
        User user = new User("adam", "Kowalski", "password", "123",
                "jan@example.com", UserRole.EMPLOYEE, new Department());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.validateUser(user);
        });

        assertEquals("First name must start with capital letter",
                exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForNumbersInFirstName() {
        User user = new User("Ad4m", "Kowalski", "password", "123",
                "jan@example.com", UserRole.EMPLOYEE, new Department());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.validateUser(user);
        });

        assertEquals("First name must only contains letters",
                exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForSpecialCharactersInFirstName() {
        User user = new User("Ad@m", "Kowalski", "password", "123",
                "jan@example.com", UserRole.EMPLOYEE, new Department());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.validateUser(user);
        });

        assertEquals("First name must only contains letters",
                exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForNullFirstName() {
        User user = new User("", "Kowalski", "password", "123",
                "jan@example.com", UserRole.EMPLOYEE, new Department());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.validateUser(user);
        });

        assertEquals("First name must only contains letters",
                exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForSpaceInFirstName() {
        User user = new User("A dam", "Kowalski", "password", "123",
                "jan@example.com", UserRole.EMPLOYEE, new Department());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.validateUser(user);
        });

        assertEquals("First name must only contains letters",
                exception.getMessage());
    }

    @Test
    void shouldValidateUserWithValidLastName(){
        User user = new User("Jan", "Kwiatowska", "password", "123333444",
                "jan@example.com", UserRole.EMPLOYEE, new Department());

        assertDoesNotThrow(() -> userService.validateUser(user));
    }

    @Test
    void shouldValidateUserWithPolishLetterInLastName(){
        User user = new User("Jan", "Bąk", "password", "123333444",
                "jan@example.com", UserRole.EMPLOYEE, new Department());

        assertDoesNotThrow(() -> userService.validateUser(user));
    }

    @Test
    void shouldValidateUserWithDashInLastName(){
        User user = new User("Janina", "Kwiatek-Korowicz", "password", "123333444",
                "jan@example.com", UserRole.EMPLOYEE, new Department());

        assertDoesNotThrow(() -> userService.validateUser(user));
    }

    @Test
    void shouldValidateUserWithCapitalPolishLetterInLastName(){
        User user = new User("Jan", "Łąkowa", "password", "123333444",
                "jan@example.com", UserRole.EMPLOYEE, new Department());

        assertDoesNotThrow(() -> userService.validateUser(user));
    }

    @Test
    void shouldThrowExceptionForNonCapitalLetterInLastName() {
        User user = new User("Kan", "kowalski", "password", "123",
                "jan@example.com", UserRole.EMPLOYEE, new Department());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.validateUser(user);
        });

        assertEquals("Last name must start with capital letter",
                exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForNumbersInLastName() {
        User user = new User("Jan", "Kowalskj888i", "password", "123",
                "jan@example.com", UserRole.EMPLOYEE, new Department());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.validateUser(user);
        });

        assertEquals("Last name must only contains letters",
                exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForSpecialCharactersInLastName() {
        User user = new User("Jan", "Kowalskj&i", "password", "123",
                "jan@example.com", UserRole.EMPLOYEE, new Department());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.validateUser(user);
        });

        assertEquals("Last name must only contains letters",
                exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForSpaceInLastName() {
        User user = new User("Jan", "Kow alski", "password", "123",
                "jan@example.com", UserRole.EMPLOYEE, new Department());
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.validateUser(user);
        });

        assertEquals("Last name must only contains letters",
                exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForDashInFirstName() {
        User user = new User("Jan-Maria", "Kowalski", "password", "123",
                "jan@example.com", UserRole.EMPLOYEE, new Department());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.validateUser(user);
        });

        assertEquals("First name must only contains letters",
                exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForNullDepartment() {
        User user = new User("Jan", "Kowalski", "password", "123456789",
                "jan@example.com", UserRole.EMPLOYEE, null);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.validateUser(user);
        });

        assertEquals("Department not found",
                exception.getMessage());
    }

    @Test
    void shouldReturnUserWhenUserFoundById() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));

        User result = userService.getUserById(1L);
        assertEquals("John", result.getFirstName());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundById() {
        when(userRepository.findById(2L)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> {
            userService.getUserById(2L);
        });
    }

    @Test
    void shouldUpdateUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(departmentService.getDepartmentById(1L)).thenReturn(department);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updated = userService.updateUser(1L, userDTO);

        assertEquals("John", updated.getFirstName());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldUpdateUserPassword() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(authenticationService).validatePassword("newpass");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.updatePassword(1L, "newpass");

        assertNotNull(result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldReturnUserWhenUserFoundByEmail() {
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.ofNullable(user));

        User result = userService.getUserByEmail("john@example.com");

        assertEquals("John", result.getFirstName());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundByEmail() {
        when(userRepository.findByEmail("notfound@example.com")).thenReturn(null);

        assertThrows(RuntimeException.class, () -> {
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
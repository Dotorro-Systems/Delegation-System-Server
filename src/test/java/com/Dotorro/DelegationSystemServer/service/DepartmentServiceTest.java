package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.dto.DepartmentDTO;
import com.Dotorro.DelegationSystemServer.model.Department;
import com.Dotorro.DelegationSystemServer.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;
    private Department department;
    private DepartmentDTO departmentDTO;

    @BeforeEach
    void setUp(){

    }

    @Test
    void shouldValidateDepartment(){
        Department department = new Department("IT");
        assertDoesNotThrow(() -> departmentService.validateDepartment(department));
    }

    @Test
    void shouldValidateWithPolishLettersInDepartmentName(){
        Department department = new Department("Księgowość");
        assertDoesNotThrow(() -> departmentService.validateDepartment(department));
    }

    @Test
    void shouldValidateWithAmpersandInDepartmentName(){
        Department department = new Department("Human & Resources");
        assertDoesNotThrow(() -> departmentService.validateDepartment(department));
    }
    @Test
    void shouldValidateWithSpaceInDepartmentName(){
        Department department = new Department("Super Księgowość");
        assertDoesNotThrow(() -> departmentService.validateDepartment(department));
    }

    @Test
    void shouldThrowExceptionForNumbersInDepartmentName(){
        Department department = new Department("Księgowość1");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            departmentService.validateDepartment(department);
        });

        assertEquals("Department must only contain letters", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForSpecialCharactersInDepartmentName(){
        Department department = new Department("Księgowość!#");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            departmentService.validateDepartment(department);
        });

        assertEquals("Department must only contain letters", exception.getMessage());
    }


}

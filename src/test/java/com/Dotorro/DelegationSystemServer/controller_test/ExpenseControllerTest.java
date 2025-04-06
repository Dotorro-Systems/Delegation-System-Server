package com.Dotorro.DelegationSystemServer.controller_test;

import com.Dotorro.DelegationSystemServer.controller.ExpenseController;
import com.Dotorro.DelegationSystemServer.controller.UserController;
import com.Dotorro.DelegationSystemServer.dto.ExpenseDTO;
import com.Dotorro.DelegationSystemServer.dto.UserDTO;
import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.model.Department;
import com.Dotorro.DelegationSystemServer.model.Expense;
import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.service.ExpenseService;
import com.Dotorro.DelegationSystemServer.service.JWTService;
import com.Dotorro.DelegationSystemServer.service.UserService;
import com.Dotorro.DelegationSystemServer.utils.DelegationStatus;
import com.Dotorro.DelegationSystemServer.utils.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExpenseController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ExpenseControllerTest
{
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private ExpenseService expenseService;

    @MockitoBean
    private JWTService jwtService;

    @Test
    public void getExpensesTest() throws Exception
    {
        Department testDepartment = new Department(1L,"testDepartment");
        UserRole testRole = UserRole.EMPLOYEE;
        User testUser = new User(1L,"testFirstName","testLastName","testPassword","testPhone","testEmail",testRole,testDepartment);
        DelegationStatus status = DelegationStatus.Active;
        LocalDateTime testDate = LocalDateTime.of(2025,1,1,1,1,1);
        Delegation testDelegation = new Delegation(1L,"testTitle","testOrigin","testDestination",status,testDate,testDate);

        Expense testExpense1 = new Expense(1L,testDelegation,testUser,"testDescription",1D,testDate);
        Expense testExpense2 = new Expense(2L,testDelegation,testUser,"testDescription",1D,testDate);
        List<Expense> testExpenses = Arrays.asList(testExpense1, testExpense2);

        when(expenseService.getAllExpenses()).thenReturn(testExpenses);

        mvc.perform(get("/expense/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("[0].id").value(1L))
                .andExpect(jsonPath("[1].id").value(2L));
    }

    @Test
    public void getExpenseByIdTest() throws Exception
    {
        Department testDepartment = new Department(1L,"testDepartment");
        UserRole testRole = UserRole.EMPLOYEE;
        User testUser = new User(1L,"testFirstName","testLastName","testPassword","testPhone","testEmail",testRole,testDepartment);
        DelegationStatus status = DelegationStatus.Active;
        LocalDateTime testDate = LocalDateTime.of(2025,1,1,1,1,1);
        Delegation testDelegation = new Delegation(1L,"testTitle","testOrigin","testDestination",status,testDate,testDate);

        Expense testExpense = new Expense(1L,testDelegation,testUser,"testDescription",1D,testDate);

        when(expenseService.getExpenseById(anyLong())).thenReturn(testExpense);

        mvc.perform(get("/expense/{id}",1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.delegation.id").value(1L))
                .andExpect(jsonPath("$.user.id").value(1L))
                .andExpect(jsonPath("$.description").value("testDescription"))
                .andExpect(jsonPath("$.amount").value(1D))
                .andExpect(jsonPath("$.createAt").value(testDate.toString()));
    }

    @Test
    public void updateExpenseTest() throws Exception
    {
        Department testDepartment = new Department(1L,"testDepartment");
        UserRole testRole = UserRole.EMPLOYEE;
        User testUser = new User(1L,"testFirstName","testLastName","testPassword","testPhone","testEmail",testRole,testDepartment);
        DelegationStatus status = DelegationStatus.Active;
        LocalDateTime testDate = LocalDateTime.of(2025,1,1,1,1,1);
        Delegation testDelegation = new Delegation(1L,"testTitle","testOrigin","testDestination",status,testDate,testDate);

        ExpenseDTO testExpenseDTO = new ExpenseDTO(1L,1L,"testDescription",1D,testDate);
        Expense testExpense = new Expense(1L,testDelegation,testUser,"testDescription",1D,testDate);

        when(expenseService.updateExpense(anyLong(),any(ExpenseDTO.class))).thenReturn(testExpense);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String testExpenseDTOJson = objectMapper.writeValueAsString(testExpenseDTO);

        mvc.perform(put("/expense/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testExpenseDTOJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void deleteExpenseByIdTest() throws Exception
    {
        Long id = 1L;

        doNothing().when(expenseService).deleteExpense(id);

        mvc.perform(delete("/expense/{id}",id))
                .andExpect(status().isOk());
    }

    @Test
    public void createExpenseTest() throws Exception
    {
        Department testDepartment = new Department(1L,"testDepartment");
        UserRole testRole = UserRole.EMPLOYEE;
        User testUser = new User(1L,"testFirstName","testLastName","testPassword","testPhone","testEmail",testRole,testDepartment);
        DelegationStatus status = DelegationStatus.Active;
        LocalDateTime testDate = LocalDateTime.of(2025,1,1,1,1,1);
        Delegation testDelegation = new Delegation(1L,"testTitle","testOrigin","testDestination",status,testDate,testDate);

        ExpenseDTO testExpenseDTO = new ExpenseDTO(1L,1L,"testDescription",1D,testDate);
        Expense testExpense = new Expense(1L,testDelegation,testUser,"testDescription",1D,testDate);

        when(expenseService.createExpense(any(ExpenseDTO.class))).thenReturn(testExpense);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String testExpenseDTOJson = objectMapper.writeValueAsString(testExpenseDTO);

        mvc.perform(post("/expense/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testExpenseDTOJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.delegation.id").value(1L))
                .andExpect(jsonPath("$.user.id").value(1L))
                .andExpect(jsonPath("$.description").value("testDescription"))
                .andExpect(jsonPath("$.amount").value(1D))
                .andExpect(jsonPath("$.createAt").value(testDate.toString()));
    }
}

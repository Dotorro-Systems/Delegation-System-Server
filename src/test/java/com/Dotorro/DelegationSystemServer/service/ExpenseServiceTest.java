package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.model.Expense;
import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private  UserService userService;

    @Mock
    DelegationService delegationService;

    @InjectMocks
    private Expense expense;
    private User user;
    private Delegation delegation;

    private ExpenseService expenseService;

    @BeforeEach
    void setUp(){
        expenseService = new ExpenseService(expenseRepository, delegationService, userService);
    }

    @Test
    void shouldValidateExpense(){
        Expense expense = new Expense(new Delegation(),new User(),"obiad",12.33,
                LocalDateTime.of(2012,2, 20,2,22,2));

        assertDoesNotThrow(() -> expenseService.validateExpense(expense));
    }

    @Test
    void shouldValidateExpenseWithZeroAmount(){
        Expense expense = new Expense(new Delegation(),new User(),"obiad",0.0,
                LocalDateTime.of(2012,2, 20,2,22,2));

        assertDoesNotThrow(() -> expenseService.validateExpense(expense));
    }

    @Test
    void shouldThrowExceptionForNegativeAmount(){
        Expense expense = new Expense(new Delegation(),new User(),"obiad",-12.33,
                LocalDateTime.of(2012,2, 20,2,22,2));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            expenseService.validateExpense(expense);
        });

        assertEquals("Amount can not be less than zero", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForNullDelegation(){
        Expense expense = new Expense(null,new User(),"obiad",12.33,
                LocalDateTime.of(2012,2, 20,2,22,2));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            expenseService.validateExpense(expense);
        });

        assertEquals("Delegation not found", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForNullUser(){
        Expense expense = new Expense(new Delegation(),null,"obiad",12.33,
                LocalDateTime.of(2012,2, 20,2,22,2));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            expenseService.validateExpense(expense);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForFutureDate(){
        Expense expense = new Expense(new Delegation(),new User(),"obiad",12.33,
                LocalDateTime.of(2032,2, 20,2,22,2));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            expenseService.validateExpense(expense);
        });

        assertEquals("Date can't be from the future", exception.getMessage());
    }
}

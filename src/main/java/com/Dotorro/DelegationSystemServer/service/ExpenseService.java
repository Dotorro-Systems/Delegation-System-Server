package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.dto.ExpenseDTO;
import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.model.Expense;
import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.lang.module.FindException;
import java.util.List;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final DelegationService delegationService;
    private final UserService userService;

    public ExpenseService(ExpenseRepository expenseRepository, DelegationService delegationService, UserService userService) {
        this.expenseRepository = expenseRepository;
        this.delegationService = delegationService;
        this.userService = userService;
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(Long expenseId) {
        return expenseRepository.findById(expenseId).orElse(null);
    }

    public Expense createExpense(ExpenseDTO expenseDTO) {
        return expenseRepository.save(convertToEntity(expenseDTO));
    }

    private Expense convertToEntity(ExpenseDTO expenseDTO) {
        Delegation delegation = delegationService.getDelegationById(expenseDTO.getDelegationId());

        User user = userService.getUserById(expenseDTO.getUserId());

        return new Expense(
                delegation,
                user,
                expenseDTO.getDescription(),
                expenseDTO.getAmount(),
                expenseDTO.getCreateAt()
        );
    }

    private ExpenseDTO convertToDTO(Expense expense) {
        return new ExpenseDTO(
                expense.getDelegation().getId(),
                expense.getUser().getId(),
                expense.getDescription(),
                expense.getAmount(),
                expense.getCreateAt()
        );
    }
}

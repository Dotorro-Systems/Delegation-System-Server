package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.dto.ExpenseDTO;
import com.Dotorro.DelegationSystemServer.dto.UserDTO;
import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.model.Expense;
import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.lang.module.FindException;
import java.util.List;
import java.util.Optional;

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
    public void validateExpense(Expense expense){
        if (expense.getDelegation() == null){
            throw new RuntimeException("Delegation not found");
        }
        if (expense.getUser() == null){
            throw new RuntimeException("User not found");
        }
        if(expense.getAmount() < 0){
            throw new IllegalArgumentException("The amount can not be less than zero.");
        }
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

    public Expense updateExpense(Long id, ExpenseDTO expenseDTO)
    {
        Optional<Expense> optionalExpense = expenseRepository.findById(id);

        Expense updatedExpense = convertToEntity(expenseDTO);

        if (optionalExpense.isPresent()) {
            Expense expense = optionalExpense.get();
            expense.setDelegation(updatedExpense.getDelegation());
            expense.setUser(updatedExpense.getUser());
            expense.setDescription(updatedExpense.getDescription());
            expense.setAmount(updatedExpense.getAmount());
            expense.setCreateAt(updatedExpense.getCreateAt());

            return expenseRepository.save(expense);
        } else {
            throw new RuntimeException("Expense not found with id: " + id);
        }
    }

    public void deleteExpense(Long id)
    {
        expenseRepository.deleteById(id);
    }

    private Expense convertToEntity(ExpenseDTO expenseDTO) {
        Delegation delegation = delegationService.getDelegationById(expenseDTO.getDelegationId());

        User user = userService.getUserById(expenseDTO.getUserId());

        Expense expense = new Expense(
                delegation,
                user,
                expenseDTO.getDescription(),
                expenseDTO.getAmount(),
                expenseDTO.getCreateAt()
        );
        validateExpense(expense);
        return expense;
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

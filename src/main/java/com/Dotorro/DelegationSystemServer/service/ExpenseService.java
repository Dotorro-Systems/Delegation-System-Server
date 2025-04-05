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
    public void expenseValidation(ExpenseDTO expenseDTO){
        Delegation delegation = delegationService.getDelegationById(expenseDTO.getDelegationId());
        if (delegation == null){
            throw new RuntimeException("Delegation not found with id: "+expenseDTO.getDelegationId());
        }
        User user = userService.getUserById(expenseDTO.getUserId());
        if (user == null){
            throw new RuntimeException("User not found with id: "+expenseDTO.getUserId());
        }
        if(expenseDTO.getAmount() < 0){
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
        expenseValidation(expenseDTO);
        return expenseRepository.save(convertToEntity(expenseDTO));
    }

    public Expense updateExpense(Long id, ExpenseDTO expenseDTO)
    {
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        expenseValidation(expenseDTO);
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

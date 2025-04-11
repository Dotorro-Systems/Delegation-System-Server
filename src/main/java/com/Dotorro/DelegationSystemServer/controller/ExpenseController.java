package com.Dotorro.DelegationSystemServer.controller;

import com.Dotorro.DelegationSystemServer.dto.ExpenseDTO;
import com.Dotorro.DelegationSystemServer.dto.UserDTO;
import com.Dotorro.DelegationSystemServer.model.Expense;
import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/expense")
@CrossOrigin(origins = "*")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService userService) {
        this.expenseService = userService;
    }

    @GetMapping(value = "/")
    public List<Expense> getExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping(value = "/{id}")
    public Expense getExpenseById(@PathVariable Long id)
    {
        return expenseService.getExpenseById(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateExpense(@PathVariable Long id, @RequestBody ExpenseDTO expenseDTO)
    {
        try {
            Expense savedExpense = expenseService.updateExpense(id, expenseDTO);
            return ResponseEntity.ok(savedExpense);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    public void deleteExpenseById(@PathVariable Long id)
    {
        expenseService.deleteExpense(id);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createExpense(@RequestBody ExpenseDTO expenseDTO) {
        try {
            Expense savedExpense = expenseService.createExpense(expenseDTO);
            return ResponseEntity.ok(savedExpense);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

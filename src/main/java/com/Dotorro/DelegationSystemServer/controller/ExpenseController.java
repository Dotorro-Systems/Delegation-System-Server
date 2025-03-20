package com.Dotorro.DelegationSystemServer.controller;

import com.Dotorro.DelegationSystemServer.dto.ExpenseDTO;
import com.Dotorro.DelegationSystemServer.model.Expense;
import com.Dotorro.DelegationSystemServer.service.ExpenseService;
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

    @GetMapping
    public List<Expense> getExpenses() {
        return expenseService.getAllExpenses();
    }

    @PostMapping
    public Expense createExpense(@RequestBody ExpenseDTO expenseDTO) {
        return expenseService.createExpense(expenseDTO);
    }
}

package com.Dotorro.DelegationSystemServer.controller;

import com.Dotorro.DelegationSystemServer.model.Expense;
import com.Dotorro.DelegationSystemServer.service.ExpenseService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/expense")
@CrossOrigin(origins = "*")
public class ExpenseController {
    private final ExpenseService userService;

    public ExpenseController(ExpenseService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<Expense> getExpenses() {
        return userService.getAllExpenses();
    }

    @PostMapping
    public Expense createExpense(@RequestBody Expense user) {
        return userService.createExpense(user);
    }
}

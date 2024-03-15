package com.Expenses.Controller;

import com.Expenses.Expense;
import com.Expenses.Service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
@CrossOrigin(origins = "http://127.0.0.1:5500/")
public class ExpenseController {

    @Autowired
    ExpenseService service;

    @GetMapping("/Expenses")
    public List<Expense> getAllExpenses(){
       List<Expense> list = service.getAllExpenses();
       return list;
 }


    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable int id) {
        Expense expense = service.getExpenseById(id);

        if (expense != null) {
            return ResponseEntity.ok(expense); // Return the expense if found
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // If expense not found
        }
    }
    @PostMapping("/Expense")
    public ResponseEntity<String> addExpense(@RequestBody Expense expenseRequest) {
        // Validate and process the incoming request
        Expense expense = new Expense();
        expense.setName(expenseRequest.getName());
        expense.setDate(expenseRequest.getDate());
        expense.setAmount(expenseRequest.getAmount());

        service.addExpense(expense);

        return ResponseEntity.ok("Expense added successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable int id) {
        // Call the service method to delete the expense
        boolean deleted = service.deleteExpenseById(id);

        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // If deleted successfully
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // If expense not found
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable int id, @RequestBody Expense updatedExpense) {
        Expense existingExpense = service.getExpenseById(id);

        if (existingExpense != null) {
            updatedExpense.setId(id); // Ensure the ID of the updated expense matches the path variable
            Expense updated = service.updateExpense(id, updatedExpense);
            return ResponseEntity.ok(updated); // Return the updated expense
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // If expense not found
        }
    }
}

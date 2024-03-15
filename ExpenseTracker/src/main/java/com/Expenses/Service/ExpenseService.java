package com.Expenses.Service;

import com.Expenses.Expense;
import com.Expenses.Repo.ExpensesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService implements Iexpense {
    @Autowired
    ExpensesRepo repo;
    @Override
    public List<Expense> getAllExpenses() {
        List<Expense> list1  = repo.findAll();
        return list1;
    }
    @Override
    public void addExpense(Expense expense){
         repo.save(expense);
    }

    @Override
    public boolean deleteExpenseById(int id) {
        Optional<Expense> optionalExpense = repo.findById(id);

        if (((Optional<?>) optionalExpense).isPresent()) {
            repo.deleteById(id);
            return true;
        }

        return false;
    }


    @Override
    public Expense updateExpense(int id, Expense updatedExpense) {
        Optional<Expense> optionalExpense = repo.findById(id);

        if (optionalExpense.isPresent()) {
            Expense existingExpense = optionalExpense.get();
            // Update fields of the existing expense with the new values
            existingExpense.setName(updatedExpense.getName());
            existingExpense.setDate(updatedExpense.getDate());
            existingExpense.setAmount(updatedExpense.getAmount());
            // Save the updated expense
            return repo.save(existingExpense);
        }

        return null;
    }


    @Override
    public Expense getExpenseById(int id) {
        Optional<Expense> optionalExpense = repo.findById(id);
        return optionalExpense.orElse(null); // Return the expense if found, otherwise null
    }
}

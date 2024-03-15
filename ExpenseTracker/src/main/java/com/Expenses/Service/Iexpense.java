package com.Expenses.Service;

import com.Expenses.Expense;

import java.util.List;

public interface Iexpense {
    public List<Expense> getAllExpenses();

    public void addExpense(Expense expense);



    boolean deleteExpenseById(int id);

    Expense updateExpense(int id, Expense updatedExpense);



    Expense getExpenseById(int id);
}

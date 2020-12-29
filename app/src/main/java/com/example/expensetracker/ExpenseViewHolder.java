package com.example.expensetracker;


import androidx.lifecycle.ViewModel;

import com.example.expensetracker.database.ExpenseDao;

import java.util.List;

class ExpenseViewHolder extends ViewModel {
    public final List<Expense> expenseList;
    public ExpenseViewHolder(ExpenseDao expenseDao) {
        expenseList = expenseDao.getAll();
    }
}


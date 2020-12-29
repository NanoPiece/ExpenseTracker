package com.example.expensetracker.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.expensetracker.Expense;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Dao
public interface ExpenseDao {
    @Query("SELECT * FROM expense")
    List<Expense> getAll();

    @Query("SELECT * FROM expense WHERE uid IN (:userIds)")
    List<Expense> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM expense WHERE category LIKE :category AND " +
            "description LIKE :description")
    Expense findByName(String category, String description);

    @Query("SELECT * FROM expense WHERE date BETWEEN :from AND :to")
    List<Expense> findExpensesBetweenDates(Date from, Date to);

    @Query("SELECT COUNT(*) FROM expense")
    int findDatabaseSize();

    @Insert
    void insert(Expense expense);

    @Delete
    void delete(Expense expense);
}

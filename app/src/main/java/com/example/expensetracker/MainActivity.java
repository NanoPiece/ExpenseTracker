package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Expense> expenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lookup the recyclerview in activity layout
        RecyclerView rvExpenses = (RecyclerView) findViewById(R.id.rvExpenses);

        // Initialise expenses
        expenses = new ArrayList<Expense>();
        for (int i=0; i<20; i++){
            // Test data
            expenses.add(new Expense("Travel", "TFL daily commute",
                    10.50 ));
        }
        ExpenseViewAdapter adapter = new ExpenseViewAdapter(expenses);
        rvExpenses.setAdapter(adapter);
        rvExpenses.setLayoutManager(new LinearLayoutManager(this));
    }
}
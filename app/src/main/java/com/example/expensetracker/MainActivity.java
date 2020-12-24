package com.example.expensetracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ExpenseViewAdapter.OnExpenseListener {
    private List<Expense> expenses;
    RecyclerView rvExpenses;
    ExpenseViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lookup the recyclerview in activity layout
        rvExpenses = (RecyclerView) findViewById(R.id.rvExpenses);

        // Initialise expenses
        expenses = new ArrayList<Expense>();
        for (int i=0; i<20; i++){
            // Test data
            expenses.add(new Expense("Travel", "TFL daily commute",
                    10.50));
        }
        adapter = new ExpenseViewAdapter(expenses, this);
        rvExpenses.setAdapter(adapter);
        rvExpenses.setLayoutManager(new LinearLayoutManager(this));

        // Floating action button - add new expense
        FloatingActionButton fab = findViewById(R.id.add_expense_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddExpense.class);
                startActivityForResult(i, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String result = data.getStringExtra("New_Expense");
                String[] resultValues = result.split(",");
                // Log.i("Testing", resultValues[2]);
                Expense expense = new Expense(resultValues[0], resultValues[1],
                        Double.parseDouble(resultValues[2]));
                expenses.add(0, expense);
                adapter.notifyItemInserted(0);

                //ensure the recyclerview stays at the top
                rvExpenses.scrollToPosition(0);
            }
        }
        if (requestCode == 2) {
            if(resultCode == RESULT_OK) {
                String result = data.getStringExtra("Updated_Expense");
                String[] resultValues = result.split(",");
                Integer position = Integer.parseInt(resultValues[0]);
                Expense item = expenses.get(position);
                item.setCategory(resultValues[1]);
                item.setDescription(resultValues[2]);
                item.setAmount(Double.parseDouble(resultValues[3]));
                adapter.notifyItemChanged(position);

                //ensure the recyclerview stays at the top
                rvExpenses.scrollToPosition(0);
            }
        }
    }

    // Action to be taken when clicking on individual expense item
    @Override
    public void onExpenseCLick(int position) {
        Expense expense = expenses.get(position);
        Intent intent = new Intent(this, EditExpense.class);
        String[] expenseDetail = new String[4];
        expenseDetail[0] = Integer.toString(position);
        expenseDetail[1] = expense.getCategory();
        expenseDetail[2] = expense.getDescription();
        expenseDetail[3] = Double.toString(expense.getAmount());
        intent.putExtra("expense", expenseDetail);
        startActivityForResult(intent, 2);
    }
}
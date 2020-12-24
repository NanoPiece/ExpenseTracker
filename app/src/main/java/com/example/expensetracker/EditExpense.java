package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

// Resources used to help build this:
// https://guides.codepath.com/android/Defining-The-ActionBar

public class EditExpense extends AppCompatActivity {
    MenuItem composeButton;
    String[] expense;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);

        final Button confirmButton = findViewById(R.id.button_confirm);
        final Button cancelButton = findViewById(R.id.button_cancel);
        final EditText category = findViewById(R.id.editTextCategory);
        final EditText description = findViewById(R.id.editTextDescription);
        final EditText amount = findViewById(R.id.editTextAmount);
        final TextView header = findViewById(R.id.edit_expense_header);

        final InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        Intent intent = getIntent();
        expense = intent.getStringArrayExtra("expense");

        final String originalCategory = expense[1];
        final String originalDescription = expense[2];
        final String originalAmount = expense[3];

        // Page configuration
        confirmButton.setVisibility(View.GONE);
        cancelButton.setVisibility(View.GONE);

        category.setText(expense[1]);
        description.setText(expense[2]);
        amount.setText(expense[3]);
        category.setEnabled(false);
        description.setEnabled(false);
        amount.setEnabled(false);

        // Action Bar - back button configuration
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Button actions
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Hide keyboard
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                category.setEnabled(false);
                description.setEnabled(false);
                amount.setEnabled(false);
                composeButton.setVisible(true);
                cancelButton.setVisibility(View.GONE);
                confirmButton.setVisibility(View.GONE);

                Intent resultIntent = new Intent();
                String result = expense[0] + "," +
                                category.getText().toString() + "," +
                                description.getText().toString() + "," +
                                amount.getText().toString();
                resultIntent.putExtra("Updated_Expense", result);
                setResult(MainActivity.RESULT_OK, resultIntent);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Hide keyboard
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                category.setText(originalCategory);
                description.setText(originalDescription);
                amount.setText(originalAmount);
                category.setEnabled(false);
                description.setEnabled(false);
                amount.setEnabled(false);
                composeButton.setVisible(true);
                cancelButton.setVisibility(View.GONE);
                confirmButton.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_edit_expense_menu, menu);
        return true;
    }

    public void onComposeAction(MenuItem mi) {
        // handle click here
        composeButton = mi;
        final Button confirmButton = findViewById(R.id.button_confirm);
        Button cancelButton = findViewById(R.id.button_cancel);
        final EditText category = findViewById(R.id.editTextCategory);
        final EditText description = findViewById(R.id.editTextDescription);
        final EditText amount = findViewById(R.id.editTextAmount);

        confirmButton.setVisibility(View.VISIBLE);
        cancelButton.setVisibility(View.VISIBLE);
        category.setEnabled(true);
        description.setEnabled(true);
        amount.setEnabled(true);
        mi.setVisible(false);
    }

}